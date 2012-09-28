package org.apache.harmony.awt.gl.font;


public class NullLineMetricsImpl extends LineMetricsImpl {

	private int length;

	public NullLineMetricsImpl(int length) {
		this.length = length;
	}

	
	@Override
	public float[] getBaselineOffsets() {
		return new float[] { 1 };
	}

	@Override
	public int getNumChars() {
		return length;
	}

	@Override
	public int getBaselineIndex() {
		return 0;
	}

	@Override
	public float getUnderlineThickness() {
		return 1;
	}

	@Override
	public float getUnderlineOffset() {
		return 1;
	}

	@Override
	public float getStrikethroughThickness() {
		return 1;
	}

	@Override
	public float getStrikethroughOffset() {
		return 1;
	}

	@Override
	public float getLeading() {
		return 1;
	}

	@Override
	public float getHeight() {
		return 10;
	}

	@Override
	public float getDescent() {
		return 3;
	}

	@Override
	public float getAscent() {
		return 2;
	}

}
