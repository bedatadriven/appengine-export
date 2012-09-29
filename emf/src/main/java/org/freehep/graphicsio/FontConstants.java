// Copyright 2003, FreeHEP.
package org.freehep.graphicsio;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: FontConstants.java 10271 2007-01-09 18:20:50Z duns $
 */
public class FontConstants {

    private FontConstants() {
    }

    // Font Embedding
    public static final String EMBED_FONTS = "EmbedFonts";

    public static final String EMBED_FONTS_AS = "EmbedFontsAs";

    public static final String EMBED_FONTS_TYPE1 = "Type1";

    public static final String EMBED_FONTS_TYPE3 = "Type3";

    public static final String TEXT_AS_SHAPES = "TEXT_AS_SHAPES";

    public static final String[] getEmbedFontsAsList() {
        return new String[] { EMBED_FONTS_TYPE1, EMBED_FONTS_TYPE3 };
    }
}
