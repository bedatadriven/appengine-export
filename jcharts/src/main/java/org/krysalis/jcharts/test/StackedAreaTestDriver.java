/***********************************************************************************************
 * File Info: $Id: StackedAreaTestDriver.java,v 1.1 2003/05/17 17:01:13 nathaniel_auvil Exp $
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
import org.krysalis.jcharts.properties.ChartTypeProperties;
import org.krysalis.jcharts.properties.StackedAreaChartProperties;
import org.krysalis.jcharts.types.ChartType;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 *******************************************************************************************/
class StackedAreaTestDriver extends AxisChartTestBase
{
	boolean supportsImageMap()
	{
		return false;
	}


	/******************************************************************************************
	 * Separate this so can use for combo chart test
	 *
	 ******************************************************************************************/
	static ChartTypeProperties getChartTypeProperties( int numberOfDataSets )
	{
		/*
		Stroke[] strokes= new Stroke[ numberOfDataSets ];
		for( int j=0; j < numberOfDataSets; j++ )
		{
			strokes[ j ]= LineChartProperties.DEFAULT_LINE_STROKE;
		}
		strokes[ 0 ]= new BasicStroke( 3.0f );

		Shape[] shapes= new Shape[ numberOfDataSets ];
		for( int j=0; j < numberOfDataSets; j++ )
		{
			shapes[ j ]= PointChartProperties.SHAPE_DIAMOND;
		}
		shapes[ 0 ]= PointChartProperties.SHAPE_CIRCLE;
		*/

		return new StackedAreaChartProperties();
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	DataSeries getDataSeries() throws ChartDataException
	{
		DataSeries dataSeries;
		AxisChartDataSet axisChartDataSet;

		int dataSize=(int) TestDataGenerator.getRandomNumber( 10, 50 );
		int numberOfDataSets=(int) TestDataGenerator.getRandomNumber( 1, 3 );


		dataSeries=super.createDataSeries( dataSize );

		axisChartDataSet=super.createAxisChartDataSet( ChartType.AREA_STACKED,
																	  getChartTypeProperties( numberOfDataSets ),
																	  numberOfDataSets,
																	  dataSize,
																	  0,
																	  5000 );

		dataSeries.addIAxisPlotDataSet( axisChartDataSet );

		return dataSeries;
	}

}
