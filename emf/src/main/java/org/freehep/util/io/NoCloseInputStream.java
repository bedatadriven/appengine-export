// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The NoCloseInputStream ignores the close so that one can keep reading from
 * the underlying stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: NoCloseInputStream.java 8584 2006-08-10 23:06:37Z duns $
 */
public class NoCloseInputStream extends BufferedInputStream {

    /**
     * Creates a No Close Input Stream.
     * 
     * @param stream stream to read from
     */
    public NoCloseInputStream(InputStream stream) {
        super(stream);
    }

    /**
     * Creates a No Close Input Stream.
     * 
     * @param stream stream to read from
     * @param size buffer size
     */
    public NoCloseInputStream(InputStream stream, int size) {
        super(stream, size);
    }

    public void close() throws IOException {
    }

    /**
     * Close this stream (the normal close is ignored).
     * 
     * @throws IOException if close fails
     */
    public void realClose() throws IOException {
        super.close();
    }

}
