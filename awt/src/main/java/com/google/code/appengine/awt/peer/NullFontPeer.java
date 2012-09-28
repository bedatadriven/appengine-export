package com.google.code.appengine.awt.peer;

import org.apache.harmony.awt.gl.font.FontExtraMetrics;
import org.apache.harmony.awt.gl.font.FontPeerImpl;
import org.apache.harmony.awt.gl.font.Glyph;
import org.apache.harmony.awt.gl.font.NullLineMetricsImpl;

import com.google.code.appengine.awt.Shape;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.font.LineMetrics;
import com.google.code.appengine.awt.geom.AffineTransform;
import com.google.code.appengine.awt.geom.Rectangle2D;

public class NullFontPeer extends FontPeerImpl {

	@Override
	public FontExtraMetrics getExtraMetrics() {
		return new FontExtraMetrics();
	}

	@Override
	public LineMetrics getLineMetrics(String str, FontRenderContext frc,
			AffineTransform at) {
		return new NullLineMetricsImpl(str.length());
	}

	@Override
	public String getPSName() {
		return "undefined";
	}

	@Override
	public int getMissingGlyphCode() {
		return (int)'?';
	}

	@Override
	public Glyph getGlyph(char ch) {
		return new Glyph() {
			
			@Override
			public Shape initOutline(char c) {
				return new Rectangle2D.Float();
			}
			
			@Override
			public byte[] getBitmap() {
				return new byte[0];
			}
		};
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public Glyph getDefaultGlyph() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canDisplay(char c) {
		return false;
	}

}
