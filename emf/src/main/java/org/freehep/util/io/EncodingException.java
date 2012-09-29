// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.IOException;

/**
 * Encoding Exception for any of the encoding streams.
 * 
 * @author Mark Donszelmann
 * @version $Id: EncodingException.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EncodingException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 8496816190751796701L;

    /**
     * Creates an Encoding Exception
     * 
     * @param msg message
     */
    public EncodingException(String msg) {
        super(msg);
    }
}
