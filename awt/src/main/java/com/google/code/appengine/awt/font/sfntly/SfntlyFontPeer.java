package com.google.code.appengine.awt.font.sfntly;

import java.io.IOException;
import java.io.InputStream;

import org.apache.harmony.awt.gl.font.FontExtraMetrics;
import org.apache.harmony.awt.gl.font.FontPeerImpl;
import org.apache.harmony.awt.gl.font.Glyph;
import org.apache.harmony.awt.gl.font.NullLineMetricsImpl;

import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.font.LineMetrics;
import com.google.code.appengine.awt.geom.AffineTransform;
import com.google.typography.font.sfntly.Font;
import com.google.typography.font.sfntly.FontFactory;
import com.google.typography.font.sfntly.Tag;
import com.google.typography.font.sfntly.table.core.CMap;
import com.google.typography.font.sfntly.table.core.CMapTable;
import com.google.typography.font.sfntly.table.core.CMapTable.CMapId;
import com.google.typography.font.sfntly.table.core.FontHeaderTable;
import com.google.typography.font.sfntly.table.truetype.GlyphTable;
import com.google.typography.font.sfntly.table.truetype.LocaTable;

public class SfntlyFontPeer extends FontPeerImpl {

	private Font font;
	private double unitsPerEm;

	public SfntlyFontPeer(String name, int style, int size) {
		this.name = name;
		this.style = style;
		this.size = size;
		
		try {
			InputStream ttfInput = getClass().getResourceAsStream("OpenSans-Regular.ttf");
			if(ttfInput == null) {
				throw new RuntimeException("Couldn't open the font file");
			}
			font = FontFactory.getInstance().loadFonts(ttfInput)[0];
		} catch (IOException e) {
			throw new RuntimeException("Could not load font: " + name, e);
		}
		
		FontHeaderTable head = font.getTable(Tag.head);
		unitsPerEm = head.unitsPerEm();
	}

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
		return psName;
	}

	@Override
	public int getMissingGlyphCode() {
		return (int)'?';
	}

	@Override
	public Glyph getGlyph(char ch) {
		com.google.typography.font.sfntly.table.truetype.Glyph glyph = lookupGlyph(ch);
	    
	    double width = (glyph.xMax() - glyph.xMin()) / unitsPerEm * ((double)size);
	    double height = (glyph.yMax() - glyph.yMin()) / unitsPerEm * ((double)size);
	    
	    return new SfntlyGlyph(width, height);
	}

	private com.google.typography.font.sfntly.table.truetype.Glyph lookupGlyph(
			char ch) {
		CMapTable cmapTable = font.getTable(Tag.cmap);
	    CMap cmap = cmapTable.cmap(CMapId.WINDOWS_BMP);
	    int glyphId = cmap.glyphId(ch);
	    
	    LocaTable locaTable = font.getTable(Tag.loca);
	    GlyphTable glyphTable = font.getTable(Tag.glyf);
	    
	    int offset = locaTable.glyphOffset(glyphId);
	    int length = locaTable.glyphLength(glyphId);
	    com.google.typography.font.sfntly.table.truetype.Glyph glyph = glyphTable.glyph(offset, length);
		return glyph;
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
		return lookupGlyph(c) != null;
	}

}
