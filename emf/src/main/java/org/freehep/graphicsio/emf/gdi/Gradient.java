// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf.gdi;

import java.io.IOException;

import org.freehep.graphicsio.emf.EMFOutputStream;

/**
 * EMF Gradient
 * 
 * @author Mark Donszelmann
 * @version $Id: Gradient.java 10140 2006-12-07 07:50:41Z duns $
 */
public abstract class Gradient {

    public Gradient() {
    }

    public abstract void write(EMFOutputStream emf) throws IOException;
}
