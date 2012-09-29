// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import java.io.IOException;
import com.google.code.appengine.awt.geom.GeneralPath;
import com.google.code.appengine.awt.Stroke;

import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFTag;
import org.freehep.graphicsio.emf.EMFRenderer;

/**
 * WidenPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: WidenPath.java 10367 2007-01-22 19:26:48Z duns $
 */
public class WidenPath extends EMFTag {

    public WidenPath() {
        super(66, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return this;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        GeneralPath currentPath = renderer.getPath();
        Stroke currentPenStroke = renderer.getPenStroke();
        // The WidenPath function redefines the current path as the area
        // that would be painted if the path were stroked using the pen
        // currently selected into the given device context.
        if (currentPath != null && currentPenStroke != null) {
            GeneralPath newPath = new GeneralPath(
                renderer.getWindingRule());
            newPath.append(currentPenStroke.createStrokedShape(currentPath), false);
            renderer.setPath(newPath);
        }
    }
}
