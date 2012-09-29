// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import com.google.code.appengine.awt.Color;
import java.io.IOException;

import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFOutputStream;

/**
 * EMF TriVertex
 * 
 * @author Mark Donszelmann
 * @version $Id: TriVertex.java 10140 2006-12-07 07:50:41Z duns $
 */
public class TriVertex {

    private int x, y;

    private Color color;

    public TriVertex(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public TriVertex(EMFInputStream emf) throws IOException {
        x = emf.readLONG();
        y = emf.readLONG();
        color = emf.readCOLOR16();
    }

    public void write(EMFOutputStream emf) throws IOException {
        emf.writeLONG(x);
        emf.writeLONG(y);
        emf.writeCOLOR16(color);
    }

    public String toString() {
        return "[" + x + ", " + y + "] " + color;
    }
}
