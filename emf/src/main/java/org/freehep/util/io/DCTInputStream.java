// Copyright 2003, FreeHEP.
package org.freehep.util.io;

import com.google.code.appengine.awt.Image;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.code.appengine.imageio.ImageIO;

/**
 * Reads images from a JPEG Stream, but only images.
 *
 * @author Mark Donszelmann
 * @version $Id: DCTInputStream.java 8584 2006-08-10 23:06:37Z duns $
 */
/**
 * @author duns
 * 
 */
public class DCTInputStream extends FilterInputStream {

    /**
     * Creates a DCT input stream from the given input stream
     * 
     * @param input stream to read from
     */
    public DCTInputStream(InputStream input) {
        super(input);
    }

    /**
     * Read is not supported, only readImage.
     * 
     * @see java.io.FilterInputStream#read()
     */
    public int read() throws IOException {
        throw new IOException(getClass()
                + ": read() not implemented, use readImage().");
    }

    /**
     * @return image read
     * @throws IOException if read fails
     */
    public Image readImage() throws IOException {
        return ImageIO.read(new NoCloseInputStream(this));
    }
}
