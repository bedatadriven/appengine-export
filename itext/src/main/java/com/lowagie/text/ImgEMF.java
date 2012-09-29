package com.lowagie.text;

import java.io.IOException;
import java.net.URL;

public class ImgEMF extends Image {

    public ImgEMF(byte[] img, float width, float height) throws BadElementException, IOException {
        super((URL)null);
        rawData = img;
        type = IMGTEMPLATE; 
        originalData = img;
        originalType = ORIGINAL_EMF;
        setBottom(height);
        setRight(width);
        this.plainWidth = width;
        this.plainHeight = height;
    }
}
