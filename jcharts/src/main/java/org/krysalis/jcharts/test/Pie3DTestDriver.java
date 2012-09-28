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


import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.PieChartDataSet;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.nonAxisChart.PieChart2D;
import org.krysalis.jcharts.nonAxisChart.PieChart3D;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendAreaProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PieChart2DProperties;
import org.krysalis.jcharts.properties.PieChart3DProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.types.PieLabelType;

import com.google.code.appengine.awt.*;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: Pie3DTestDriver.java,v 1.5 2003/06/25 01:39:48 nathaniel_auvil Exp $
 ************************************************************************************/
public class Pie3DTestDriver
{

	/******************************************************************************************
	 * Test for PieChart2D
	 *
	 * @throws ChartDataException
	 ******************************************************************************************/
	static void test() throws ChartDataException, PropertyException
	{
		PieChart2D pieChart2D;
		PieChartDataSet pieChartDataSet;
		LegendProperties legendProperties;
		ChartProperties chartProperties;

		int dataSize;
		int width;
		int height;
		int numTestsToRun = 15;
		String fileName;

		HTMLGenerator htmlGenerator = new HTMLGenerator( ChartTestDriver.OUTPUT_PATH + "pieChart2dTest.html" );

		for( int i = 0; i < numTestsToRun; i++ )
		{
			boolean createImageMap = true; //( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );

			dataSize = (int) TestDataGenerator.getRandomNumber( 1, 10 );
			pieChartDataSet = Pie3DTestDriver.getPieChartDataSet( dataSize, 1, 7 );

			width = (int) TestDataGenerator.getRandomNumber( 100, 600 );
			height = (int) TestDataGenerator.getRandomNumber( 100, 600 );

			legendProperties = new LegendProperties();
			TestDataGenerator.randomizeLegend( legendProperties );
			//legendProperties.setBorderStroke( new BasicStroke( 2.0f ) );

			chartProperties = new ChartProperties();
			//areaProperties.setEdgePadding( (int) TestDataGenerator.getRandomNumber( 0, 50 ) );
			chartProperties.setBackgroundPaint( TestDataGenerator.getRandomPaint() );
			//chartProperties.setBorderStroke( new BasicStroke( 1f ) );

			pieChart2D = new PieChart2D( pieChartDataSet, legendProperties, chartProperties, width, height );

			fileName = ChartTestDriver.OUTPUT_PATH + "pieChart2d" + i + ChartTestDriver.EXTENSION;

			ImageMap imageMap;
			if( createImageMap )
			{
				pieChart2D.renderWithImageMap();
				imageMap = pieChart2D.getImageMap();
			}
			else
			{
				imageMap = null;
			}


			ChartTestDriver.exportImage( pieChart2D, fileName );


			htmlGenerator.chartTableStart( "PieChart2D", fileName, imageMap );
			htmlGenerator.propertiesTableRowStart();
			pieChartDataSet.toHTML( htmlGenerator );
			htmlGenerator.propertiesTableRowStart();
			pieChart2D.toHTML( htmlGenerator, fileName );

			htmlGenerator.addLineBreak();
		}

		htmlGenerator.saveFile();
	}


	/*****************************************************************************************
	 * Generates a random NonAxisChartDataSet
	 *
	 * @param numToCreate the number of doubles to generate
	 * @param minValue
	 * @param maxValue
	 * @return PieChartDataSet
	 ******************************************************************************************/
	private static PieChartDataSet getPieChartDataSet( int numToCreate, int minValue, int maxValue ) throws ChartDataException
	{
		PieChart2DProperties properties = new PieChart2DProperties();
		//properties.setZeroDegreeOffset( (float) TestDataGenerator.getRandomNumber( 0, 500 ) );
		properties.setBorderChartStroke( new ChartStroke( new BasicStroke( 1.0f ), TestDataGenerator.getRandomPaint() ) );

		String[] labels = TestDataGenerator.getRandomStrings( numToCreate, (int) TestDataGenerator.getRandomNumber( 3, 20 ), false );
		Paint[] paints = TestDataGenerator.getRandomPaints( numToCreate );

		return new PieChartDataSet( "This is a test title", TestDataGenerator.getRandomNumbers( numToCreate, minValue, maxValue ), labels, paints, properties );
	}


	/***********************************************************************************************
	 *
	 * @param args
	 * @throws ChartDataException
	 * @throws PropertyException
	 ************************************************************************************************/
	public static void main( String[] args ) throws ChartDataException, PropertyException
	{


		double[] data = {45.00d, 90.00d, 45.00d, 180d};
		String[] labels = {"Equities", "Bonds", "Money Market", "Alternative Investments"};
		//Paint[] paints = TestDataGenerator.getRandomPaints( data.length );

		//Paint[] paints = { new Color( 200, 0, 0 ), new Color( 0, 200, 0 ), new Color( 0, 0, 200 ), new Color( 200, 200, 0 ) };
      Paint[] paints = { new Color( 200, 0, 0, 20 ),
								 new Color( 0, 200, 0, 220 ),
								 new Color( 0, 0, 200, 20 ),
								 new Color( 200, 200, 0, 20 ) };


/*
		double[] data = { 73.6d };
		String[] labels = { "Alternative Investments"};
		Paint[] paints = { Color.blue };
*/



		PieChart3DProperties pieChart3DProperties = new PieChart3DProperties();
		pieChart3DProperties.setPieLabelType( PieLabelType.LEGEND_LABELS );
		pieChart3DProperties.setTickLength( 5 );

		pieChart3DProperties.setZeroDegreeOffset( 60 );

		pieChart3DProperties.setDepth( 15 );

		LegendProperties legendProperties = new LegendProperties();
		legendProperties.setPlacement( LegendAreaProperties.RIGHT );
		legendProperties.setNumColumns( 1 );
		//legendProperties.setBorderStroke( null );

		PieChartDataSet pieChartDataSet = new PieChartDataSet( "Investment Categories", data, labels, paints, pieChart3DProperties );

		ChartProperties chartProperties = new ChartProperties();
		chartProperties.setBorderStroke( ChartStroke.DEFAULT_CHART_OUTLINE );

		PieChart3D pieChart = new PieChart3D( pieChartDataSet, legendProperties, chartProperties, 600, 200 );
		//PieChart2D pieChart = new PieChart2D( pieChartDataSet, legendProperties, chartProperties, 400, 300 );

		ChartTestDriver.exportImage( pieChart, "pie3d.png" );
	}


}
