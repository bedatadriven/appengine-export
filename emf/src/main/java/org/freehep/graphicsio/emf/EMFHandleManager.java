package org.freehep.graphicsio.emf;

import java.util.BitSet;

/**
 * Allocates and frees handles for EMF files
 * 
 * @author Tony Johnson
 * @version $Id: EMFHandleManager.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EMFHandleManager {
    private BitSet handles = new BitSet();

    private int maxHandle;

    public int getHandle() {
        int handle = nextClearBit();
        handles.set(handle);
        if (handle > maxHandle)
            maxHandle = handle;
        return handle;
    }

    public int freeHandle(int handle) {
        handles.clear(handle);
        return handle;
    }

    private int nextClearBit() {
        // return handles.nextClearBit(1); // JDK 1.4
        for (int i = 1;; i++)
            if (!handles.get(i))
                return i;
    }

    public int highestHandleInUse() {
        return handles.length() - 1;
    }

    public int maxHandlesUsed() {
        return maxHandle + 1;
    }
}
