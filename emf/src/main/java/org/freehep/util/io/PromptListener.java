// Copyright 2002, FreeHEP.
package org.freehep.util.io;

import java.io.IOException;

/**
 * Listener to inform that Prompt of the PromptInputStream has been found.
 * 
 * @author Mark Donszelmann
 * @version $Id: PromptListener.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface PromptListener {

    /**
     * Prompt was found, and can now be read.
     * 
     * @param route stream for reading prompt (and more)
     * @throws IOException if read fails
     */
    public void promptFound(RoutedInputStream.Route route) throws IOException;
}
