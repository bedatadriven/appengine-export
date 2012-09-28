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


import com.google.code.appengine.awt.BasicStroke;
import com.google.code.appengine.awt.Paint;

import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.PieChartDataSet;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.nonAxisChart.PieChart2D;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PieChart2DProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.util.ChartStroke;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: PieTestDriver.java,v 1.4 2003/08/08 08:51:27 nicolaken Exp $
 ************************************************************************************/
public class PieTestDriver
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
			pieChartDataSet = PieTestDriver.getPieChartDataSet( dataSize, 1, 7 );

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

		PieTestDriver.test();
/*
		double[] data = {73.6d, 5.00d, 1.50d, 3.60d};
		String[] labels = {"Equities", "Bonds", "Money Market", "Alternative Investments"};
		Paint[] paints = {Color.lightGray, Color.green, Color.blue, Color.red};
*/

/*


		double[] data = { 73.6d };
		String[] labels = { "Alternative Investments"};
		Paint[] paints = { Color.blue };



		PieChart2DProperties pieChart2DProperties = new PieChart2DProperties();
		pieChart2DProperties.setPieLabelType( PieLabelType.VALUE_LABELS );
		pieChart2DProperties.setTickLength( 5 );

		pieChart2DProperties.setZeroDegreeOffset( 10 );

		LegendProperties legendProperties = new LegendProperties();
		legendProperties.setPlacement( LegendAreaProperties.TOP );
		legendProperties.setNumColumns( 1 );
		//legendProperties.setBorderStroke( null );

		PieChartDataSet pieChartDataSet = new PieChartDataSet( "Investment Categories", data, labels, paints, pieChart2DProperties );

		ChartProperties chartProperties = new ChartProperties();
		chartProperties.setBorderStroke( ChartStroke.DEFAULT_CHART_OUTLINE );

		PieChart2D pieChart = new PieChart2D( pieChartDataSet, legendProperties, chartProperties, 600, 300 );
		//PieChart2D pieChart = new PieChart2D( pieChartDataSet, legendProperties, chartProperties, 400, 300 );

		ChartTestDriver.exportImage( pieChart, "pie.png" );
*/
	}


}
