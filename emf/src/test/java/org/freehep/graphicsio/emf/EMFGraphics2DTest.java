package org.freehep.graphicsio.emf;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Dimension;

public class EMFGraphics2DTest {

	@Test
	public void test() throws IOException {
		EMFGraphics2D g2d = new EMFGraphics2D(new File("target/test.emf"), new Dimension(500, 500));
		g2d.startExport();
		g2d.setColor(Color.BLUE);
		g2d.drawOval(0, 0, 500, 500);
		g2d.endExport();
	}
	
}
