// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The CountedByteOutputStream counts the number of bytes written.
 * 
 * @author Mark Donszelmann
 * @version $Id: CountedByteOutputStream.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CountedByteOutputStream extends FilterOutputStream {

    private int count;

    /**
     * Creates a Counted Bytes output stream from the given stream.
     * 
     * @param out stream to write to
     */
    public CountedByteOutputStream(OutputStream out) {
        super(out);
        count = 0;
    }

    public void write(int b) throws IOException {
        out.write(b);
        count++;
    }

    public void write(byte[] b) throws IOException {
        out.write(b);
        count += b.length;
    }

    public void write(byte[] b, int offset, int len) throws IOException {
        out.write(b, offset, len);
        count += len;
    }

    /**
     * @return number of bytes written.
     */
    public int getCount() {
        return count;
    }
}
