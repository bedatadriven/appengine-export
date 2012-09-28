/***********************************************************************************************
 * File Info: $Id: AxisChartTestBase.java,v 1.3 2004/05/27 02:14:59 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.*;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.types.ChartType;
import org.krysalis.jcharts.imageMap.ImageMap;

import com.google.code.appengine.awt.*;


/******************************************************************************************
 *
 *
 *******************************************************************************************/
abstract class AxisChartTestBase
{

	public AxisChartTestBase()
	{
		System.out.println( "Running: " + this.getClass().getName() );
	}


	abstract DataSeries getDataSeries() throws ChartDataException;
	abstract boolean supportsImageMap();


	/******************************************************************************************
	 * Test for LineChart
	 *
	 ******************************************************************************************/
	static void axisChartTest( String name, AxisChartTestBase axisChartTestBase ) throws ChartDataException, PropertyException
	{
		LegendProperties legendProperties;
		ChartProperties chartProperties;
		AxisProperties axisProperties;
		AxisChart axisChart;

		int numTestsToRun = 10;
		String fileName;

		HTMLGenerator htmlGenerator = new HTMLGenerator( ChartTestDriver.OUTPUT_PATH + name + "Test.html" );

		for( int i = 0; i < numTestsToRun; i++ )
		{
			DataSeries dataSeries= axisChartTestBase.getDataSeries();

			boolean horizontalPlot= true; //( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );
			boolean createImageMap= true; //( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );

			axisProperties = new AxisProperties( horizontalPlot );
			axisProperties.setXAxisLabelsAreVertical( ( TestDataGenerator.getRandomNumber( 1 ) > 0.5d ) );

			TestDataGenerator.randomizeAxisProperties( axisProperties );
			//axisProperties.setYAxisNumItems( 6 );

			DataAxisProperties dataAxisProperties;
			LabelAxisProperties labelAxisProperties;

			if( horizontalPlot )
			{
				dataAxisProperties = ( DataAxisProperties ) axisProperties.getXAxisProperties();
				labelAxisProperties = ( LabelAxisProperties ) axisProperties.getYAxisProperties();
			}
			else
			{
				dataAxisProperties = ( DataAxisProperties ) axisProperties.getYAxisProperties();
				labelAxisProperties = ( LabelAxisProperties ) axisProperties.getXAxisProperties();
			}

			dataAxisProperties.setRoundToNearest( 1 );
			//dataAxisProperties.setUserDefinedScale( 10, 500 );
			

			//---if the labels are NULL, force to zero.
			if( dataSeries.getNumberOfAxisLabels() == 0 )
			{
				labelAxisProperties.setShowAxisLabels( false );
			}
			else
			{
				labelAxisProperties.setShowAxisLabels( ( TestDataGenerator.getRandomNumber( 1 ) > 0.3d ) );
			}


			int width = ( int ) TestDataGenerator.getRandomNumber( 500, 1000 );
			int height = ( int ) TestDataGenerator.getRandomNumber( 300, 800 );

			legendProperties = new LegendProperties();
			TestDataGenerator.randomizeLegend( legendProperties );
			//legendProperties.setPlacement( LegendAreaProperties.RIGHT );

			chartProperties = new ChartProperties();
			//chartProperties.setBorderStroke( new BasicStroke( 1f ) );


			axisChart = new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, width, height );


			fileName = ChartTestDriver.OUTPUT_PATH + name + i + ChartTestDriver.EXTENSION;


			ImageMap imageMap;
			if( createImageMap && axisChartTestBase.supportsImageMap() )
			{
				axisChart.renderWithImageMap();
				imageMap= axisChart.getImageMap();
			}
			else
			{
				imageMap= null;
			}

			try
			{
				ChartTestDriver.exportImage( axisChart, fileName );
			}
			catch( NullPointerException nullPointerException )
			{
				nullPointerException.printStackTrace();
				System.out.println();
			}

			axisChart.toHTML( htmlGenerator, fileName, imageMap );
			htmlGenerator.addLineBreak();
		}

		htmlGenerator.saveFile();
	}


	/*****************************************************************************************************
	 *
	 *
	 *****************************************************************************************************/
	final DataSeries createDataSeries( int numberOfValuesToCreate )
	{
		String[] xAxisLabels= null;
		if( ( TestDataGenerator.getRandomNumber( 1 ) > 0.3d ) )
		{
			xAxisLabels = TestDataGenerator.getRandomStrings( numberOfValuesToCreate, ( int ) TestDataGenerator.getRandomNumber( 10 ), false );
		}
		String xAxisTitle = TestDataGenerator.getRandomString( 15, true );
		String yAxisTitle = TestDataGenerator.getRandomString( 15, true );

		return new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, "This is a test title that is so freaking long is is going to wrap around the image for sure. lfksjg;ljs; dflgkjdfgsdgdg dsgdsgsdfg dsfgsdfgsdfgs dfgdsfgd" );
	}


	/*****************************************************************************************
	 * Generates a random MultiDataSet
	 *
	 * @param numberOfDataSets
	 * @param numberOfValuesToCreate the number of doubles to generate
	 * @param minValue
	 * @param maxValue
	 * @return AxisChartDataSet
	 ******************************************************************************************/
	AxisChartDataSet createAxisChartDataSet( ChartType chartType,
																  ChartTypeProperties chartTypeProperties,
																  int numberOfDataSets,
																  int numberOfValuesToCreate,
																  int minValue,
																  int maxValue ) throws ChartDataException
	{
		double[][] data = TestDataGenerator.getRandomNumbers( numberOfDataSets, numberOfValuesToCreate, minValue, maxValue );
		String[] legendLabels = TestDataGenerator.getRandomStrings( numberOfDataSets, 10, false );
		Paint[] paints = TestDataGenerator.getRandomPaints( numberOfDataSets );


/*
		//data[ 0 ][ 0 ]= Double.NaN;
		data[ 0 ][ 1 ]= Double.NaN;


		data[ 0 ][ 4 ]= Double.NaN;
		data[ 0 ][ 6 ]= Double.NaN;

		//data[ 0 ][ data[ 0 ].length - 1 ]= Double.NaN;
		data[ 0 ][ data[ 0 ].length - 2 ]= Double.NaN;
*/

		return new AxisChartDataSet( data, legendLabels, paints, chartType, chartTypeProperties );
	}
}
