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

package org.krysalis.jcharts.test;


import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Paint;

import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.AxisChartDataSet;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.DataSeries;
import org.krysalis.jcharts.chartData.interfaces.IAxisDataSeries;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.StackedBarChartProperties;
import org.krysalis.jcharts.types.ChartType;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 * @author Nathaniel Auvil
 * @version $Id: StackedBarTestDriver.java,v 1.3 2003/08/08 08:51:27 nicolaken Exp $
 *******************************************************************************************/
public class StackedBarTestDriver extends AxisChartTestBase
{
	boolean supportsImageMap()
	{
		return true;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	DataSeries getDataSeries() throws ChartDataException
	{
		StackedBarChartProperties stackedBarChartProperties;
		DataSeries dataSeries;
		AxisChartDataSet axisChartDataSet;

		int dataSize=(int) TestDataGenerator.getRandomNumber( 1, 30 );
		int numberOfDataSets=(int) TestDataGenerator.getRandomNumber( 1, 5 );


		dataSeries=super.createDataSeries( dataSize );


		stackedBarChartProperties=new StackedBarChartProperties();


		axisChartDataSet=super.createAxisChartDataSet( ChartType.BAR_STACKED,
																	  stackedBarChartProperties,
																	  numberOfDataSets,
																	  dataSize,
																	  0,
																	  5000 );

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
		StackedBarChartProperties stackedBarChartProperties = new StackedBarChartProperties();

		//BackgroundRenderer backgroundRenderer = new BackgroundRenderer( new Color( 20, 20, 20, 50 ) );
		//clusteredBarChartProperties.addPreRenderEventListener( backgroundRenderer );

/*

		ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer( false, true, -1 );
		valueLabelRenderer.setValueLabelPosition( ValueLabelPosition.ON_TOP );
		valueLabelRenderer.useVerticalLabels( false );
		clusteredBarChartProperties.addPostRenderEventListener( valueLabelRenderer );
*/



		double[][] data = {{280, 16, 150, 90, 60, 200, 150, 60 }, {80, 216, 10, 30, 15, 90, 150, 87 } };
		Paint[] paints = {Color.yellow, Color.blue };
		String[] legendLabels = {"Test Legend Label", "second set"};
		AxisChartDataSet axisChartDataSet = new AxisChartDataSet( data, legendLabels, paints, ChartType.BAR_STACKED, stackedBarChartProperties );

		String[] axisLabels = {"1900", "1950", "2000", "2050", "3000", "3050", "4000", "4050" };
		IAxisDataSeries dataSeries = new DataSeries( axisLabels, "Cookies", "Years", null );
		dataSeries.addIAxisPlotDataSet( axisChartDataSet );


		ChartProperties chartProperties = new ChartProperties();
		AxisProperties axisProperties = new AxisProperties( false );


		//axisProperties.getXAxisProperties().setShowAxisLabels( false );
		//axisProperties.getYAxisProperties().setShowAxisLabels( false );
      //axisProperties.getYAxisProperties().setShowTicks( AxisTypeProperties.TICKS_NONE );

		//axisProperties.getYAxisProperties().setShowGridLines( AxisTypeProperties.GRID_LINES_NONE );
		//axisProperties.getYAxisProperties().setAxisStroke( new ChartStroke( new BasicStroke( 1.5f ), Color.red ) );

/*

		DataAxisProperties xAxis = (DataAxisProperties) axisProperties.getXAxisProperties();
		xAxis.setRoundToNearest( 1 );
		xAxis.setUserDefinedScale( -300, 200 );
*/

		LegendProperties legendProperties = new LegendProperties();

		AxisChart axisChart = new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 500, 400 );

		axisChart.renderWithImageMap();

		ChartTestDriver.exportImage( axisChart, "StackedBarChartTest.png" );
	}

}
