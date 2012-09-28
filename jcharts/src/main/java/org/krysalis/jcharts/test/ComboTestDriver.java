/***********************************************************************************************
 * File Info: $Id: ComboTestDriver.java,v 1.1 2003/05/17 17:01:10 nathaniel_auvil Exp $
 * Copyright (C) 2000
 * Author: Nathaniel G. Auvil
 * Contributor(s):
 *
 * Copyright 2002 (C) Nathaniel G. Auvil. All Rights Reserved.
 *
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "jCharts" or "Nathaniel G. Auvil" must not be used to
 * 	  endorse or promote products derived from this Software without
 * 	  prior written permission of Nathaniel G. Auvil.  For written
 *    permission, please contact nathaniel_auvil@users.sourceforge.net
 *
 * 4. Products derived from this Software may not be called "jCharts"
 *    nor may "jCharts" appear in their names without prior written
 *    permission of Nathaniel G. Auvil. jCharts is a registered
 *    trademark of Nathaniel G. Auvil.
 *
 * 5. Due credit should be given to the jCharts Project
 *    (http://jcharts.sourceforge.net/).
 *
 * THIS SOFTWARE IS PROVIDED BY Nathaniel G. Auvil AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * jCharts OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 ************************************************************************************************/

package org.krysalis.jcharts.test;


import org.krysalis.jcharts.chartData.*;
import org.krysalis.jcharts.types.ChartType;
import org.krysalis.jcharts.properties.StockChartProperties;

import com.google.code.appengine.awt.*;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 *******************************************************************************************/
public final class ComboTestDriver extends AxisChartTestBase
{
	boolean supportsImageMap()
	{
		return false;
	}


	/******************************************************************************************
	 * Test for LineChart
	 *
	 ******************************************************************************************/
	DataSeries getDataSeries() throws ChartDataException
	{
		int dataSize= 10; //(int) TestDataGenerator.getRandomNumber( 2, 15 );
		int numberOfDataSets=1; //(int) TestDataGenerator.getRandomNumber( 1, 3 );


		AxisChartDataSet axisChartDataSet;
		DataSeries dataSeries=super.createDataSeries( dataSize );



		double[] highs=TestDataGenerator.getRandomNumbers( dataSize, 500, 1000 );
		double[] lows=TestDataGenerator.getRandomNumbers( dataSize, 100, 300 );
		double[] opens=TestDataGenerator.getRandomNumbers( dataSize, 350, 450 );
		double[] closes=TestDataGenerator.getRandomNumbers( dataSize, 350, 450 );

		StockChartProperties stockChartProperties=new StockChartProperties();

		StockChartDataSet stockChartDataSet=new StockChartDataSet( highs, "High", lows, "Low", Color.black, stockChartProperties );
		stockChartDataSet.setOpenValues( opens, "Open", Color.red );
		stockChartDataSet.setCloseValues( closes, "Close", Color.green );

		String[] legendLabels=TestDataGenerator.getRandomStrings( numberOfDataSets, 10, false );
		Paint[] paints=TestDataGenerator.getRandomPaints( numberOfDataSets );

		dataSeries.addIAxisPlotDataSet( stockChartDataSet );



		axisChartDataSet=super.createAxisChartDataSet( ChartType.LINE,
																	  LineTestDriver.getChartTypeProperties( 2 ),
																	  2,
																	  dataSize,
																	  0,
																	  5000 );
		dataSeries.addIAxisPlotDataSet( axisChartDataSet );


		return dataSeries;
	}
}
