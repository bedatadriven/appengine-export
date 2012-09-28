/***********************************************************************************************
 * File Info: $Id: BarTestDriver.java,v 1.5 2004/05/27 02:06:39 nathaniel_auvil Exp $
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


import com.google.code.appengine.awt.BasicStroke;
import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Paint;

import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.AxisChartDataSet;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.DataSeries;
import org.krysalis.jcharts.chartData.interfaces.IAxisDataSeries;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.AxisTypeProperties;
import org.krysalis.jcharts.properties.BarChartProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.ChartTypeProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.types.ChartType;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 *******************************************************************************************/
public final class BarTestDriver extends AxisChartTestBase
{
	boolean supportsImageMap()
	{
		return true;
	}


	/******************************************************************************************
	 * Separate this so can use for combo chart test
	 *
	 ******************************************************************************************/
	static ChartTypeProperties getChartTypeProperties( int numberOfDataSets )
	{
		BarChartProperties barChartProperties = new BarChartProperties();
		barChartProperties.setWidthPercentage( 1f );

		return barChartProperties;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	DataSeries getDataSeries() throws ChartDataException
	{
		int dataSize = (int) TestDataGenerator.getRandomNumber( 2, 25 );
		int numberOfDataSets = 1; //(int) TestDataGenerator.getRandomNumber( 1, 3 );


		AxisChartDataSet axisChartDataSet;


		DataSeries dataSeries = super.createDataSeries( dataSize );

		axisChartDataSet = super.createAxisChartDataSet( ChartType.BAR,
																		 getChartTypeProperties( numberOfDataSets ),
																		 numberOfDataSets,
																		 dataSize,
																		 -200,
																		 400 );

		dataSeries.addIAxisPlotDataSet( axisChartDataSet );

		return dataSeries;
	}


	/*****************************************************************************************
	 *
	 * @param args
	 * @throws PropertyException
	 * @throws ChartDataException
	 *****************************************************************************************/
	public static void main( String[] args ) throws PropertyException, ChartDataException
	{
		BarChartProperties barChartProperties = new BarChartProperties();

		//BackgroundRenderer backgroundRenderer = new BackgroundRenderer( new Color( 20, 20, 20, 50 ) );
		//barChartProperties.addPreRenderEventListener( backgroundRenderer );

/*
		ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer( false, true, -1 );
		valueLabelRenderer.setValueLabelPosition( ValueLabelPosition.ON_TOP );
		valueLabelRenderer.useVerticalLabels( false );
		barChartProperties.addPostRenderEventListener( valueLabelRenderer );
*/





		double[][] data = {{.40, .60, .04, .3 }};
		Paint[] paints = {Color.green};
		String[] legendLabels = {"Test Legend Label"};
		AxisChartDataSet axisChartDataSet = new AxisChartDataSet( data, legendLabels, paints, ChartType.BAR, barChartProperties );

		String[] axisLabels = {"1900", "2000", "2010", "2050" };
		//String[] axisLabels = {"1900", "1910", "1920", "1930", "1940", "1950", "1960", "1970", "1980", "1990", "2000" };
		IAxisDataSeries dataSeries = new DataSeries( axisLabels, "Wonka Bars", "Years", "Oompa Loompa Productivity" );
		dataSeries.addIAxisPlotDataSet( axisChartDataSet );


		ChartProperties chartProperties = new ChartProperties();
		AxisProperties axisProperties = new AxisProperties( false );

		axisProperties.getYAxisProperties().setShowGridLines( AxisTypeProperties.GRID_LINES_ALL );
		axisProperties.getYAxisProperties().setAxisStroke( new ChartStroke( new BasicStroke( 1.5f ), Color.red ) );
      axisProperties.getYAxisProperties().setGridLineChartStroke( new ChartStroke( new BasicStroke( 1.5f ), Color.red ) );

		//axisProperties.setXAxisLabelsAreVertical( true );


		DataAxisProperties yAxis = (DataAxisProperties) axisProperties.getYAxisProperties();
//		yAxis.setUsePercentSigns( true );
//		yAxis.setUserDefinedScale( 0, 0.05 );
		yAxis.setNumItems( 10 );
		yAxis.setRoundToNearest( -3 );



		LegendProperties legendProperties = new LegendProperties();

		AxisChart axisChart = new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 500, 400 );

		ChartTestDriver.exportImage( axisChart, "BarChartTest.png" );


	}

}
