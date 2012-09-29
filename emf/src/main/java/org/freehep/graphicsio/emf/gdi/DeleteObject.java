// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import java.io.IOException;

import org.freehep.graphicsio.emf.EMFInputStream;
import org.freehep.graphicsio.emf.EMFOutputStream;
import org.freehep.graphicsio.emf.EMFTag;
import org.freehep.graphicsio.emf.EMFRenderer;

/**
 * DeleteObject TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: DeleteObject.java 10367 2007-01-22 19:26:48Z duns $
 */
public class DeleteObject extends EMFTag {

    private int index;

    public DeleteObject() {
        super(40, 1);
    }

    public DeleteObject(int index) {
        this();
        this.index = index;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return new DeleteObject(emf.readDWORD());
    }

    public void write(int tagID, EMFOutputStream emf) throws IOException {
        emf.writeDWORD(index);
    }

    public String toString() {
        return super.toString() +
            "\n  index: 0x" + Integer.toHexString(index);
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        renderer.storeGDIObject(index, null);
    }
}
