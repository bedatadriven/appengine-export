package com.lowagie.text.rtf;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.sanselan.util.IOUtils;
import org.junit.Test;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.ImgEMF;
import com.lowagie.text.Paragraph;

public class RtfEmfWriterTest {

	@Test
	public void test() throws Exception {
		Document document = new Document();
		FileOutputStream os = new FileOutputStream("target/test.rtf");
		RtfWriter2 writer = RtfWriter2.getInstance(document, os);
		
		byte[] bytes = IOUtils.getFileBytes(new File("src/test/resources/test.emf"));
		Image image = new ImgEMF(bytes, 500, 500);
		
		document.open();
		document.add(new Paragraph("Hello World"));
		document.add(image);
		writer.close();
	}

}
