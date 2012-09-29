//Copyright 2001-2005 FreeHep
package org.freehep.graphics2d.font;

/**
 * Abstract Character Table, inherited by all the Generated Encoding Tables
 * 
 * @author Simon Fischer
 * @version $Id: AbstractCharTable.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class AbstractCharTable implements CharTable {

    public int toEncoding(char unicode) {
        try {
            String name = toName(unicode);
            if (name == null)
                return 0;
            int enc = toEncoding(name);
            if (enc > 255) {
                System.out.println("toEncoding() returned illegal value for '"
                        + name + "': " + enc);
                return 0;
            }
            return enc;
        } catch (Exception e) {
            return 0;
        }
    }

    public String toName(char c) {
        return toName(new Character(c));
    }

    public String toName(Integer enc) {
        return toName(enc.intValue());
    }
}
