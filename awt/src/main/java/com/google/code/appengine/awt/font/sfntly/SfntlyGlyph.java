package com.google.code.appengine.awt.font.sfntly;

import com.google.code.appengine.awt.Rectangle;
import com.google.code.appengine.awt.Shape;
import com.google.code.appengine.awt.font.GlyphMetrics;
import com.google.code.appengine.awt.geom.Rectangle2D;

public class SfntlyGlyph extends org.apache.harmony.awt.gl.font.Glyph {

	private double width;
	private double height;

	public SfntlyGlyph(double width, double height) {
		this.width = width; 
		this.height = height;
		this.glPointMetrics = new GlyphMetrics((int)width, new Rectangle2D.Double(0,0,width,height), GlyphMetrics.STANDARD);
	}

	@Override
	public byte[] getBitmap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Shape initOutline(char c) {
		return new Rectangle((int)width, (int)height);
	}

}
