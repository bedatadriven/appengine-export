// Copyright 2001, FreeHEP.
package org.freehep.util.io;

/**
 * Constants for the RunLength encoding.
 * 
 * @author Mark Donszelmann
 * @version $Id: RunLength.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface RunLength {

    /**
     * Maximum run length
     */
    public static final int LENGTH = 128;

    /**
     * End of data code
     */
    public static final int EOD = 128;

}
