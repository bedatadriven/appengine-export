/***********************************************************************************************
 * File Info: $Id: ChartTestDriver.java,v 1.3 2004/05/26 01:58:07 nathaniel_auvil Exp $
 * Copyright (C) 2000
 * Author: Nathaniel G. Auvil
 * Contributor(s):
 *
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

package org.krysalis.jcharts.test;


import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.encoders.*;

import java.io.*;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 *******************************************************************************************/
public final class ChartTestDriver
{
	private final static String SVG = ".svg";
	private final static String PNG = ".png";
	private final static String JPEG = ".jpg";
	private final static String JPEG_LEGACY = ".jpeg";
	final static String OUTPUT_PATH = "";

	final static String EXTENSION = PNG;


	/*****************************************************************************************
	 * Main method so can run as command line.
	 *
	 * @param args command line arguements.
	 *****************************************************************************************/
	public static void main( String[] args ) throws ChartDataException, PropertyException
	{
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

      AxisChartTestBase.axisChartTest( "barChart", new BarTestDriver() );

/*
		AxisChartTestBase.axisChartTest( "stockChart", new StockTestDriver() );

		AxisChartTestBase.axisChartTest( "areaChart", new AreaTestDriver() );

		AxisChartTestBase.axisChartTest( "barChart", new BarTestDriver() );
		AxisChartTestBase.axisChartTest( "stackedBarChart", new StackedBarTestDriver() );
		AxisChartTestBase.axisChartTest( "clusteredBarChart", new ClusteredBarTestDriver() );
*/

//		AxisChartTestBase.axisChartTest( "comboChart", new ComboTestDriver() );


/*
		PieTestDriver.test();


		AxisChartTestBase.axisChartTest( "pointChart", new PointTestDriver() );

		AxisChartTestBase.axisChartTest( "lineChart", new LineTestDriver() );

*/
//		AxisChartTestBase.axisChartTest( "scatterChart", new ScatterPlotTestDriver() );


		//ChartTestDriver.testAxisScale();


		stopWatch.stop();
		System.out.println( stopWatch );
	}


	private static void testAxisScale()
	{

		double yMax = -15;
		double yMin = -130;

		// In the following line, note that Math.log is actually Natural Logarithm.
		// log base a of b = ln b / ln a => log base 10 of x = ln 10 / ln x
		double yDelta = Math.pow( 10.0, Math.round( Math.log( yMax - yMin ) / Math.log( 10 ) ) );
		double yStart = yMin - (yMin % yDelta);
		double yEnd = yMax - (yMax % yDelta) + yDelta;

		System.out.println( "yDelta= " + yDelta + "   yStart= " + yStart + "   yEnd= " + yEnd );


		// Count the number of segments this gives us.  Shoot for 20 segments or so.
		int segments = ( int ) ((yEnd - yStart) / yDelta);

		if( segments <= 2 )
		{
			// we need 10 times this many
			yDelta = yDelta / 10.0;
		}
		else if( segments <= 5 )
		{
			// we need 4 times this many
			yDelta = yDelta / 4.0;
		}
		else if( segments <= 10 )
		{
			yDelta = yDelta / 2.0;
		}

		// Recalc start and end to match with new delta.
		yStart = yMin - (yMin % yDelta);
		yEnd = yMax - (yMax % yDelta) + yDelta;
		segments = ( int ) ((yEnd - yStart) / yDelta);

/*

        axisProperties = new AxisProperties(yStart, yDelta);
        axisProperties.setYAxisNumItems(segments);
*/


	}


	/******************************************************************************************
	 * Utility method to write the image to file so I do not have to deal with file i/o
	 *  every time I write a test.
	 *
	 * @param chart verything that is renderable extends this class.
	 * @param fileName what to name the file
	 * @throws ChartDataException
	 * @throws PropertyException
	 ******************************************************************************************/
	static void exportImage( Chart chart, String fileName ) throws ChartDataException, PropertyException
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream( fileName );

			if( EXTENSION.equals( PNG ) )
			{
				PNGEncoder.encode( chart, fileOutputStream );
			}
			else if( EXTENSION.equals( JPEG ) )
			{
				JPEGEncoder.encode( chart, 1.0f, fileOutputStream );
			}
			else
			{
				System.out.println( "unknown file type to encode: " + EXTENSION );
			}

			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch( FileNotFoundException fileNotFoundException )
		{
			fileNotFoundException.printStackTrace();
		}
		catch( IOException ioException )
		{
			ioException.printStackTrace();
		}
	}


}
