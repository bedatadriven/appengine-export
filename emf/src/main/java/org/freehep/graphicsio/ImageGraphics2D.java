// Copyright 2003-2007, FreeHEP.
package org.freehep.graphicsio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.freehep.graphicsio.raw.RawImageWriteParam;
import org.freehep.util.UserProperties;
import org.freehep.util.images.ImageUtilities;
import org.freehep.util.io.ASCII85OutputStream;
import org.freehep.util.io.FlateOutputStream;

import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Image;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.RenderedImage;
import com.google.code.appengine.imageio.IIOImage;
import com.google.code.appengine.imageio.ImageIO;
import com.google.code.appengine.imageio.ImageReader;
import com.google.code.appengine.imageio.ImageWriteParam;
import com.google.code.appengine.imageio.ImageWriter;
import com.google.code.appengine.imageio.stream.ImageInputStream;
import com.google.code.appengine.imageio.stream.ImageOutputStream;

/**
 * Generic class for generating bitmap outputs from an image.
 *
 * @author Mark Donszelmann
 * @version $Id: ImageGraphics2D.java 10273 2007-01-09 19:01:32Z duns $
 */
public class ImageGraphics2D  {

    private final static String alwaysCompressedFormats[] = {
        ImageConstants.JPG.toLowerCase(),
        ImageConstants.JPEG.toLowerCase(),
        ImageConstants.GIF.toLowerCase()};

    private final static String nonTransparentFormats[] = {
        ImageConstants.JPG.toLowerCase(),
        ImageConstants.JPEG.toLowerCase(),
        ImageConstants.PPM.toLowerCase()};

    public static final String rootKey = "org.freehep.graphicsio";

    // our general properties
    public static final String TRANSPARENT = "." + PageConstants.TRANSPARENT;

    public static final String BACKGROUND = "." + PageConstants.BACKGROUND;

    public static final String BACKGROUND_COLOR = "."
            + PageConstants.BACKGROUND_COLOR;

    // our image properties
    public static final String ANTIALIAS = ".Antialias";

    public static final String ANTIALIAS_TEXT = ".AntialiasText";

    // standard image properties
    public static final String PROGRESSIVE = ".Progressive";

    public static final String COMPRESS = ".Compress";

    public static final String COMPRESS_MODE = ".CompressMode";

    public static final String COMPRESS_DESCRIPTION = ".CompressDescription";

    public static final String COMPRESS_QUALITY = ".CompressQuality";

    private static final Map /* UserProperties */defaultProperties = new HashMap();

    public static Properties getDefaultProperties(String format) {
        UserProperties properties = (UserProperties) defaultProperties
                .get(format);
        if (properties == null) {
            properties = new UserProperties();
            defaultProperties.put(format, properties);

            String formatKey = rootKey + "." + format;

            // set our parameters
            if (canWriteTransparent(format)) {
                properties.setProperty(formatKey + TRANSPARENT, true);
                properties.setProperty(formatKey + BACKGROUND, false);
                properties
                        .setProperty(formatKey + BACKGROUND_COLOR, Color.GRAY);
            } else {
                properties.setProperty(formatKey + BACKGROUND, false);
                properties
                        .setProperty(formatKey + BACKGROUND_COLOR, Color.GRAY);
            }

            // set our parameters
            properties.setProperty(formatKey + ANTIALIAS, true);
            properties.setProperty(formatKey + ANTIALIAS_TEXT, true);

            // copy parameters from specific format
            ImageWriter writer = getPreferredImageWriter(format);
            if (writer != null) {
                ImageWriteParam param = writer.getDefaultWriteParam();

                // compression
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    properties.setProperty(formatKey + COMPRESS, true);
                    String[] compressionTypes = param.getCompressionTypes();
                    String compressionType = param.getCompressionType();
                    properties.setProperty(formatKey + COMPRESS_MODE, compressionType != null ? compressionType : compressionTypes[0]);
                    properties.setProperty(formatKey + COMPRESS_DESCRIPTION,
                            "Custom");
                    float compressionQuality = 0.0f;
                    try {
                    	compressionQuality = param.getCompressionQuality();
                    } catch (IllegalStateException e) {
                    	// ignored
                    }
                    properties.setProperty(formatKey + COMPRESS_QUALITY, compressionQuality);
                } else {
                    properties.setProperty(formatKey + COMPRESS, false);
                    properties.setProperty(formatKey + COMPRESS_MODE, "");
                    properties.setProperty(formatKey + COMPRESS_DESCRIPTION,
                            "Custom");
                    properties.setProperty(formatKey + COMPRESS_QUALITY, 0.0f);
                }

                // progressive
                if (param.canWriteProgressive()) {
                    properties
                            .setProperty(
                                    formatKey + PROGRESSIVE,
                                    param.getProgressiveMode() != ImageWriteParam.MODE_DISABLED);
                } else {
                    properties.setProperty(formatKey + PROGRESSIVE, false);
                }
            } else {
                System.err.println(ImageGraphics2D.class
                        + ": No writer for format '" + format + "'.");
            }
        }
        return properties;
    }


    /**
     * Handles an exception which has been caught. Dispatches exception to
     * writeWarning for UnsupportedOperationExceptions and writeError for others
     *
     * @param exception to be handled
     */
    protected void handleException(Exception exception) {
        System.err.println(exception);
    }

    /**
     * creates an empty image
     *
     * @param format e.g. {@link ImageConstants#BMP} or {ImageConstants#PNG}
     * @param width image width
     * @param height image height
     * @return offscreen buffered image
     */
    public static BufferedImage createBufferedImage(
        String format,
        int width,
        int height) {

        // NOTE: special case for WBMP which only
        // supports on color band with sample size 1
        // (which means black / white with no gray scale)
        if (ImageConstants.WBMP.equalsIgnoreCase(format)) {
            return new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        }

        // NOTE: special case for JPEG which has no Alpha
        if (ImageConstants.JPG.equalsIgnoreCase(format)) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        // NOTE: special case for BMP which has no Alpha
        if (ImageConstants.BMP.equalsIgnoreCase(format)) {
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }

        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }


    public static void writeImage(Image image, String format,
            Properties properties, OutputStream os) throws IOException {
        // FIXME hardcoded background
        writeImage(
                ImageUtilities.createRenderedImage(image, null, Color.black),
                format, properties, os);
    }

    public static void writeImage(RenderedImage image, String format,
            Properties properties, OutputStream os) throws IOException {

        ImageWriter writer = getPreferredImageWriter(format);
        if (writer == null)
            throw new IOException(ImageGraphics2D.class
                    + ": No writer for format '" + format + "'.");

        // get the parameters for this format
        UserProperties user = new UserProperties(properties);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param instanceof ImageParamConverter) {
            param = ((ImageParamConverter) param).getWriteParam(user);
        }

        // now set the standard write parameters
        String formatKey = rootKey + "." + format;
        if (param.canWriteCompressed()) {
            if (user.isProperty(formatKey + COMPRESS)) {
                if (user.getProperty(formatKey + COMPRESS_MODE).equals("")) {
                    param.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
                } else {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionType(user.getProperty(formatKey
                            + COMPRESS_MODE));
                    param.setCompressionQuality(user.getPropertyFloat(formatKey
                            + COMPRESS_QUALITY));
                }
            } else {
                if (canWriteUncompressed(format)) {
                    param.setCompressionMode(ImageWriteParam.MODE_DISABLED);
                }
            }
        }
        if (param.canWriteProgressive()) {
            if (user.isProperty(formatKey + PROGRESSIVE)) {
                param.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
            } else {
                param.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            }
        }

        // write the image
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
        ios.close();
    }

    public static ImageWriter getPreferredImageWriter(String format) {
        return (ImageWriter)getImageWriters(ImageIO
                .getImageWritersByFormatName(format)).first();
    }

    public static ImageWriter getPreferredImageWriterForMIMEType(String mimeType) {
        return (ImageWriter)getImageWriters(ImageIO
                .getImageWritersByMIMEType(mimeType)).first();
    }

    public static SortedSet/*<ImageWriter>*/ getImageWriters(Iterator iterator) {
        // look for a writer that supports the given format,
        // BUT prefer our own "org.freehep."
        // over "com.sun.imageio." over "com.sun.media." over others
        SortedSet imageWriters = new TreeSet(new Comparator() {
        	private int order(Object o) {
        		String className = o.getClass().getName();
        		if (className.startsWith("org.freehep.")) {
                    return 0;
                } else if (className.startsWith("com.sun.imageio.")) {
                    return 1;
                } else if (className.startsWith("com.sun.media.")) {
                    return 2;
                }
        		return 3;
        	}

        	public int compare(Object arg0, Object arg1) {
        		int order0 = order(arg0);
        		int order1 = order(arg1);
        		return order0 < order1 ? -1 : order0 > order1 ? 1 : 0;
        	}
        });
        while (iterator.hasNext()) {
            imageWriters.add((ImageWriter) iterator.next());
        }
        return imageWriters;
    }

    public static BufferedImage readImage(String format, InputStream is)
            throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName(format.toLowerCase());
        if (!iterator.hasNext()) {
            throw new IOException(ImageGraphics2D.class
                    + ": No reader for format '" + format + "'.");
        }
        ImageReader reader = (ImageReader) iterator.next();

        ImageInputStream iis = ImageIO.createImageInputStream(is);
        reader.setInput(iis, true);
        BufferedImage image = reader.read(0);
        reader.dispose();
        iis.close();
        return image;
    }

    public static boolean canWriteUncompressed(String format) {
        // Method forgotten by Sun, BUG# 4856395.
        // If param.canWriteCompressed() is true, then it may be that
        // the format always needs to be compressed... GIF and JPG are among of
        // them.
        return !Arrays.asList(alwaysCompressedFormats).contains(
                format.toLowerCase());
    }

    public static boolean canWriteTransparent(String format) {
        return !Arrays.asList(nonTransparentFormats).contains(
                format.toLowerCase());
    }

    /**
     * @param bkg Background color for the image
     * @return Properties used to create a RAW image
     * @param code Color encoding, e.g. {@link ImageConstants#COLOR_MODEL_RGB}
     */
    public static UserProperties getRAWProperties(Color bkg, String code) {
        UserProperties result = new UserProperties();
        result.setProperty(RawImageWriteParam.BACKGROUND, bkg);
        result.setProperty(RawImageWriteParam.CODE, code);
        result.setProperty(RawImageWriteParam.PAD, 1);
        return result;
    }

    /**
     * Converts a given image to byte[]
     *
     * @throws IOException thrown by {@link #writeImage(com.google.code.appengine.awt.image.RenderedImage, String, java.util.Properties, java.io.OutputStream)}
     * @param image Image to convert
     * @param format e.g. {@link ImageConstants#JPG}, {@link ImageConstants#PNG, {@link ImageConstants#RAW}
     * @param props Properties for writing, e.g. {@link org.freehep.graphicsio.raw.RawImageWriteParam#BACKGROUND}
     * @param encoding {@link ImageConstants#ENCODING_ASCII85}, {@link ImageConstants#ENCODING_FLATE} or null
     * @return bytes representing the image
     */
    public static byte[] toByteArray(
        RenderedImage image,
        String format,
        String encoding,
        Properties props) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os = bos;

        if (ImageConstants.ENCODING_ASCII85.equals(encoding)
            || ImageConstants.ENCODING_FLATE_ASCII85.equals(encoding)) {
            os = new ASCII85OutputStream(os);
        }

        if (ImageConstants.ENCODING_FLATE.equals(encoding)
            || ImageConstants.ENCODING_FLATE_ASCII85.equals(encoding)) {
            os = new FlateOutputStream(os);
        }

        // avoid NPE
        if (props == null) {
            props = new Properties();
        }

        // write image into the stream
        ImageGraphics2D.writeImage(image, format.toLowerCase(), props, os);
        os.close();

        // return reulting bytes from stream
        return bos.toByteArray();
    }
}
