// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import com.google.code.appengine.awt.Dimension;
import com.google.code.appengine.awt.Rectangle;
import com.google.code.appengine.awt.geom.RoundRectangle2D;
import java.io.IOException;

import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFOutputStream;
import org.freehep.graphicsio.emf.EMFTag;
import org.freehep.graphicsio.emf.EMFRenderer;

/**
 * RoundRect TAG.
 *
 * @author Mark Donszelmann
 * @version $Id: RoundRect.java 10377 2007-01-23 15:44:34Z duns $
 */
public class RoundRect extends EMFTag {

    private Rectangle bounds;

    private Dimension corner;

    public RoundRect() {
        super(44, 1);
    }

    public RoundRect(Rectangle bounds, Dimension corner) {
        this();
        this.bounds = bounds;
        this.corner = corner;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return new RoundRect(emf.readRECTL(), emf.readSIZEL());
    }

    public void write(int tagID, EMFOutputStream emf) throws IOException {
        emf.writeRECTL(bounds);
        emf.writeSIZEL(corner);
    }

    public String toString() {
        return super.toString() +
            "\n  bounds: " + bounds +
            "\n  corner: " + corner;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        renderer.fillAndDrawOrAppend(new RoundRectangle2D.Double(
            bounds.getX(),
            bounds.getX(),
            bounds.getWidth(),
            bounds.getHeight(),
            corner.getWidth(),
            corner.getHeight()));
    }
}
