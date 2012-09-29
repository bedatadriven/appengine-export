package org.freehep.util.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Equivalent to writing to /dev/nul
 * 
 * @author tonyj
 * @version $Id: DummyOutputStream.java 8584 2006-08-10 23:06:37Z duns $
 */
public class DummyOutputStream extends OutputStream {
    /**
     * Creates a Dummy output steram.
     */
    public DummyOutputStream() {
    }

    public void write(int b) throws IOException {
    }

    public void write(byte[] b) throws IOException {
    }

    public void write(byte[] b, int off, int len) throws IOException {
    }
}
