/*
 * $Id: XfdfExample.java 1742 2005-05-09 11:52:51Z blowagie $
 * $Name$
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */
package com.lowagie.examples.forms.fill;

import java.io.FileOutputStream;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.XfdfReader;

/**
 * How to merge an XFDF file with a PDF form.
 */
public class XfdfExample {
    /**
     * Merges an XFDF file with a PDF form.
     * @param args no arguments needed
     */
    public static void main(String[] args) {
        try {
            // merging the FDF file
            PdfReader pdfreader = new PdfReader("SimpleRegistrationForm.pdf");
            PdfStamper stamp = new PdfStamper(pdfreader, new FileOutputStream("registered_xfdf.pdf"));
            XfdfReader fdfreader = new XfdfReader("register.xfdf");
            AcroFields form = stamp.getAcroFields();
            form.setFields(fdfreader);
            stamp.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
