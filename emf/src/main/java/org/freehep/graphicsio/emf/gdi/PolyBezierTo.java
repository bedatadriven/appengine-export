// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import com.google.code.appengine.awt.Point;
import com.google.code.appengine.awt.Rectangle;
import com.google.code.appengine.awt.geom.GeneralPath;
import java.io.IOException;

import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFTag;
import org.freehep.graphicsio.emf.EMFRenderer;

/**
 * PolyBezierTo TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: PolyBezierTo.java 10367 2007-01-22 19:26:48Z duns $
 */
public class PolyBezierTo extends AbstractPolygon {

    public PolyBezierTo() {
        super(5, 1, null, 0, null);
    }

    public PolyBezierTo(Rectangle bounds, int numberOfPoints, Point[] points) {
        super(5, 1, bounds, numberOfPoints, points);
    }

    protected PolyBezierTo (int id, int version, Rectangle bounds, int numberOfPoints, Point[] points) {
        super(id, version, bounds, numberOfPoints, points);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        Rectangle r = emf.readRECTL();
        int n = emf.readDWORD();
        return new PolyBezierTo(r, n, emf.readPOINTL(n));
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        Point[] points = getPoints();
        int numberOfPoints = getNumberOfPoints();
        GeneralPath currentFigure = renderer.getFigure();

        if (points != null && points.length > 0) {

            Point p1, p2, p3;
            for (int point = 0; point < numberOfPoints; point = point + 3) {
                // add a point to gp
                p1 = points[point];
                p2 = points[point + 1];
                p3 = points[point + 2];
                currentFigure.curveTo(
                    (float)p1.getX(), (float)p1.getY(),
                    (float)p2.getX(), (float)p2.getY(),
                    (float)p3.getX(), (float)p3.getY());
            }
        }
    }
}
