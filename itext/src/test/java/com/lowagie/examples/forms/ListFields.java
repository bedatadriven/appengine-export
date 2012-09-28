/*
 * $Id: ListFields.java 2368 2006-09-14 23:52:28Z xlv $
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

package com.lowagie.examples.forms;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PRIndirectReference;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfLister;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;

/**
 * Demonstrates the use of PageSize.
 * @author blowagie
 */
public class ListFields {
    /**
     * Creates a PDF document with a certain pagesize
     * @param args no arguments needed here
     */
    public static void main(String[] args) {
        
        System.out.println("Listfields");
                
        try {
            PrintStream stream = new PrintStream(new FileOutputStream("listfields.txt"));
            stream.println("ListFields output file");
            stream.println("==================================================");
            for (int i = 0; i < args.length; i++) {
            	stream.print("Filename: ");
            	stream.println(args[i]);
            	stream.println();
                PdfReader reader = new PdfReader(args[i]);
                PRAcroForm form = reader.getAcroForm();
                if (form == null) {
                    stream.println("This document has no fields.");
                    break;
                }
                PdfLister list = new PdfLister(stream);
                HashMap refToField = new HashMap();
                ArrayList fields = form.getFields();
                for (int k = 0; k < fields.size(); ++k) {
                    PRAcroForm.FieldInformation field = (PRAcroForm.FieldInformation)fields.get(k);
                    refToField.put(new Integer(field.getRef().getNumber()), field);
                }
                for (int page = 1; page <= reader.getNumberOfPages(); ++page) {
                    PdfDictionary dPage = reader.getPageN(page);
                    PdfArray annots = (PdfArray)PdfReader.getPdfObject(dPage.get(PdfName.ANNOTS));
                    if (annots == null)
                        continue;
                    ArrayList ali = annots.getArrayList();
                    for (int annot = 0; annot < ali.size(); ++annot) {
                        PdfObject refObj = (PdfObject)ali.get(annot);
                        PRIndirectReference ref = null;
                        PdfDictionary an = (PdfDictionary)PdfReader.getPdfObject(refObj);
                        PdfName name = (PdfName)an.get(PdfName.SUBTYPE);
                        if (name == null || !name.equals(PdfName.WIDGET))
                            continue;
                        PdfArray rect = (PdfArray)an.get(PdfName.RECT);
                        String fName = "";
                        PRAcroForm.FieldInformation field = null;
                        while (an != null) {
                            PdfString tName = (PdfString)an.get(PdfName.T);
                            if (tName != null)
                                fName = tName.toString() + "." + fName;
                            if (refObj.type() == PdfObject.INDIRECT && field == null) {
                                ref = (PRIndirectReference)refObj;
                                field = (PRAcroForm.FieldInformation)refToField.get(new Integer(ref.getNumber()));
                            }
                            refObj = an.get(PdfName.PARENT);
                            an = (PdfDictionary)PdfReader.getPdfObject(refObj);
                        }
                        if (fName.endsWith("."))
                            fName = fName.substring(0, fName.length() - 1);
                        stream.println("page " + page + ", name - " + fName);
                        list.listAnyObject(rect);
                        if (field != null) {
                            stream.println("Merged attributes of " + field.getName());
                            list.listAnyObject(field.getInfo());
                            stream.println("Dictionary of " + field.getName());
                            list.listAnyObject(PdfReader.getPdfObject(field.getRef()));
                        }
                    }
                }
                stream.println("==================================================");
            }
            stream.flush();
            stream.close();
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }
}