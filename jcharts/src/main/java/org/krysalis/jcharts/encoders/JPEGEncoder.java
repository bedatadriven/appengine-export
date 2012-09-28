/***********************************************************************************************
 * Copyright 2002 (C) Nathaniel G. Auvil. All Rights Reserved.
 *
 * Redistribution and use of this software and associated documentation ("Software"), with or
 * without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright statements and notices.
 * 	Redistributions must also contain a copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * 	conditions and the following disclaimer in the documentation and/or other materials
 * 	provided with the distribution.
 *
 * 3. The name "jCharts" or "Nathaniel G. Auvil" must not be used to endorse or promote
 * 	products derived from this Software without prior written permission of Nathaniel G.
 * 	Auvil.  For written permission, please contact nathaniel_auvil@users.sourceforge.net
 *
 * 4. Products derived from this Software may not be called "jCharts" nor may "jCharts" appear
 * 	in their names without prior written permission of Nathaniel G. Auvil. jCharts is a
 * 	registered trademark of Nathaniel G. Auvil.
 *
 * 5. Due credit should be given to the jCharts Project (http://jcharts.sourceforge.net/).
 *
 * THIS SOFTWARE IS PROVIDED BY Nathaniel G. Auvil AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * jCharts OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 ************************************************************************************************/

package org.krysalis.jcharts.encoders;


import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.properties.PropertyException;

import com.google.code.appengine.imageio.IIOImage;
import com.google.code.appengine.imageio.ImageIO;
import com.google.code.appengine.imageio.ImageTypeSpecifier;
import com.google.code.appengine.imageio.ImageWriteParam;
import com.google.code.appengine.imageio.ImageWriter;
import com.google.code.appengine.imageio.plugins.jpeg.JPEGImageWriteParam;
import com.google.code.appengine.imageio.stream.ImageOutputStream;
import com.google.code.appengine.awt.image.BufferedImage;
import com.google.code.appengine.awt.image.IndexColorModel;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;


/*************************************************************************************
 * This class REQUIRES the jdk 1.4
 *
 * @author Nathaniel Auvil
 * @version $Id: JPEGEncoder.java,v 1.2 2003/05/26 13:40:20 nathaniel_auvil Exp $
 ************************************************************************************/
public class JPEGEncoder
{


	private static final String JPEG = "jpeg";


	static
	{
		//---do not use a file cache as hurts performance
		ImageIO.setUseCache( false );
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	private JPEGEncoder() throws Exception
	{
		throw new Exception( "No need to create an instance of this class!" );
	}


	/******************************************************************************************
	 * Encodes the chart to a JPEG format. If you are generating large dimension images, the file
	 *  size can get quite large.  You can try decreasing the quality to decrease the file size.
	 *
	 * @param outputStream
	 * @param quality float value from 0.0f(worst image quality) - 1.0f(best image quality)
	 * @throws ChartDataException
	 * @throws PropertyException
	 * @throws IOException
	 *******************************************************************************************/
	public static void encode( Chart chart,
										float quality,
										OutputStream outputStream ) throws ChartDataException, PropertyException, IOException
	{
		BufferedImage bufferedImage = BinaryEncoderUtil.render( chart );

		Iterator writers = ImageIO.getImageWritersByFormatName( JPEG );
		ImageWriter imageWriter = (ImageWriter) writers.next();

		JPEGImageWriteParam params = new JPEGImageWriteParam( null );
		params.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
		params.setCompressionQuality( quality );
		params.setProgressiveMode( com.google.code.appengine.imageio.ImageWriteParam.MODE_DISABLED );
		params.setDestinationType( new ImageTypeSpecifier( IndexColorModel.getRGBdefault(),
																			IndexColorModel.getRGBdefault().createCompatibleSampleModel( 16, 16 ) ) );

		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream( outputStream );
		imageWriter.setOutput( imageOutputStream );
		imageWriter.write( null, new IIOImage( bufferedImage, null, null ), params );

		imageOutputStream.close();

		imageWriter.dispose();
	}
}


