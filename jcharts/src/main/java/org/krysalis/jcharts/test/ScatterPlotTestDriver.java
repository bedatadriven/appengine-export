/***********************************************************************************************
 * File Info: $Id: ScatterPlotTestDriver.java,v 1.3 2003/08/08 08:51:27 nicolaken Exp $
 * Copyright (C) 2002
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


import com.google.code.appengine.awt.BasicStroke;
import com.google.code.appengine.awt.Paint;
import com.google.code.appengine.awt.Shape;
import com.google.code.appengine.awt.Stroke;
import com.google.code.appengine.awt.geom.Point2D;

import org.krysalis.jcharts.axisChart.ScatterPlotAxisChart;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.DataSeries;
import org.krysalis.jcharts.chartData.ScatterPlotDataSeries;
import org.krysalis.jcharts.chartData.ScatterPlotDataSet;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.LineChartProperties;
import org.krysalis.jcharts.properties.PointChartProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.ScatterPlotProperties;


/******************************************************************************************
 * This file provides examples of how to create all the different chart types provided by
 *  this package.
 *
 *******************************************************************************************/
public class ScatterPlotTestDriver extends AxisChartTestBase
{
	boolean supportsImageMap()
	{
		return true;
	}


	/******************************************************************************************
	 * Separate this so can use for combo chart test
	 *
	 * @param numberOfDataSets
	 ******************************************************************************************/
	private ScatterPlotProperties getScatterPlotProperties( int numberOfDataSets )
	{
		Stroke[] strokes = new Stroke[ numberOfDataSets ];
		for( int j = 0; j < numberOfDataSets; j++ )
		{
			strokes[ j ] = LineChartProperties.DEFAULT_LINE_STROKE;
		}
		strokes[ 0 ] = new BasicStroke( 3.0f );

		Shape[] shapes = new Shape[ numberOfDataSets ];
		for( int j = 0; j < numberOfDataSets; j++ )
		{
			shapes[ j ] = PointChartProperties.SHAPE_DIAMOND;
		}
		shapes[ 0 ] = PointChartProperties.SHAPE_CIRCLE;


		return new ScatterPlotProperties( strokes, shapes );
	}


	/*****************************************************************************************
	 * Generates a random MultiDataSet
	 *
	 * @param numberOfDataSets
	 * @param numberOfValuesToCreate the number of doubles to generate
	 * @param xMinValue
	 * @param xMaxValue
	 * @param yMinValue
	 * @param yMaxValue
	 * @return AxisChartDataSet
	 ******************************************************************************************/
	private ScatterPlotDataSet createScatterPlotDataSet( int numberOfDataSets,
																		  int numberOfValuesToCreate,
																		  int xMinValue,
																		  int xMaxValue,
																		  int yMinValue,
																		  int yMaxValue ) throws ChartDataException
	{
		//Point2D.Double[] points= TestDataGenerator.getRandomPoints( numberOfValuesToCreate, xMinValue, xMaxValue, yMinValue, yMaxValue );


		Point2D.Double[] points= new Point2D.Double[ 20 ];
		for( int x = 0; x < 20; x++ )
		{
			//--- y = x^2
			points[ x ]= new Point2D.Double();
			points[ x ].setLocation( x, Math.pow( x, 2 ) );
		}

		String[] legendLabels = TestDataGenerator.getRandomStrings( 1, 12, false );
//		Paint[] paints = TestDataGenerator.getRandomPaints( numberOfDataSets );
      Paint paint = TestDataGenerator.getRandomPaint();

		ScatterPlotDataSet scatterPlotDataSet= new ScatterPlotDataSet( this.getScatterPlotProperties( 1 ) );
      scatterPlotDataSet.addDataPoints( points, paint, legendLabels[ 0 ]  );

System.out.println( "legendLabels[ 0 ]= " + legendLabels[ 0 ] );
		return scatterPlotDataSet;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	DataSeries getDataSeries() throws ChartDataException
	{
		DataSeries dataSeries= null;

		int dataSize = (int) TestDataGenerator.getRandomNumber( 3, 3 );
		int numberOfDataSets = (int) TestDataGenerator.getRandomNumber( 1, 1 );

		//ScatterPlotDataSet scatterPlotDataSet= this.createScatterPlotDataSet(  )

		//String[] xAxisLabels = TestDataGenerator.getRandomStrings( numberOfValuesToCreate, ( int ) TestDataGenerator.getRandomNumber( 10 ), false );
		String xAxisTitle = TestDataGenerator.getRandomString( 15, true );
		String yAxisTitle = TestDataGenerator.getRandomString( 15, true );
		//dataSeries = new DataSeries( xAxisLabels, xAxisTitle, yAxisTitle, "This is a test title that is so freaking long is is going to wrap around the image for sure. lfksjg;ljs; dflgkjdfgsdgdg dsgdsgsdfg dsfgsdfgsdfgs dfgdsfgd" );


		//dataSeries.addIAxisPlotDataSet( axisChartDataSet );

		return dataSeries;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************
	 DataSeries getDataSeries() throws ChartDataException
	 {
	 String[] xAxisLabels={"1", "2", "3", "4", "5"};
	 DataSeries dataSeries=new DataSeries( xAxisLabels, "numbers", "numbers", "Bug #559177" );
	 AxisChartDataSet axisChartDataSet;

	 double[][] data={{1, 2, 3, 4, 5},
	 {7, 8, Double.NaN, Double.NaN, Double.NaN},
	 {Double.NaN, Double.NaN, Double.NaN, Double.NaN, 2}};

	 String[] legendLabels={"set 1", "set 2", "set 3"};
	 Paint[] paints={Color.blue, Color.red, Color.green};

	 axisChartDataSet=new AxisChartDataSet( data,
	 legendLabels,
	 paints,
	 ChartType.LINE,
	 this.getChartTypeProperties( 3 ) );

	 dataSeries.addIAxisPlotDataSet( axisChartDataSet );

	 return dataSeries;

	 }
	 */


	public static void main( String[] args ) throws ChartDataException, PropertyException
	{
      ScatterPlotTestDriver s= new ScatterPlotTestDriver();

      ScatterPlotDataSet scatterPlotDataSet= s.createScatterPlotDataSet( 1, 5, -1000, 3000, 200, 500 );
		ScatterPlotDataSeries scatterPlotDataSeries = new ScatterPlotDataSeries( scatterPlotDataSet,
																										 "X-Axis Title",
																										 "Y-Axis Title",
																										 "Chart Title" );

		DataAxisProperties xAxisProperties= new DataAxisProperties();
		xAxisProperties.setUserDefinedScale( -5, 3 );
		xAxisProperties.setNumItems( 10 );
		xAxisProperties.setRoundToNearest( 0 );

		DataAxisProperties yAxisProperties= new DataAxisProperties();
		yAxisProperties.setUserDefinedScale( -30, 50 );
		yAxisProperties.setNumItems( 10 );
		yAxisProperties.setRoundToNearest( 1 );

		AxisProperties axisProperties = new AxisProperties( xAxisProperties, yAxisProperties );
      ChartProperties chartProperties = new ChartProperties();
		LegendProperties legendProperties = new LegendProperties();

		ScatterPlotAxisChart scatterPlotAxisChart = new ScatterPlotAxisChart( scatterPlotDataSeries,
																									 chartProperties,
																									 axisProperties,
																									 legendProperties,
																									 500,
																									 400 );

		ChartTestDriver.exportImage( scatterPlotAxisChart, "ScatterPlotTest.png" );
	}


}
