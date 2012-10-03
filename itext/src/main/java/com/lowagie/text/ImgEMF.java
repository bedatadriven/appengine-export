package com.lowagie.text;

import java.io.IOException;
import java.net.URL;

public class ImgEMF extends Image {

	public ImgEMF(Image img) {
		super(img);
		if(img instanceof ImgEMF) {
			ImgEMF toClone = (ImgEMF)img;
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
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
