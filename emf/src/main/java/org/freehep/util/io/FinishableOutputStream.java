// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.IOException;

/**
 * The FinishableOutputStream allows a generic way of calling finish on an
 * output stream without closing it.
 * 
 * @author Mark Donszelmann
 * @version $Id: FinishableOutputStream.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface FinishableOutputStream {

    /**
     * Finishes the current outputstream (compresses, flushes, caluclates CRC)
     * and writes whatever is left in the buffers, but does not close the
     * stream.
     * 
     * @throws IOException if write fails
     */
    public void finish() throws IOException;
}
