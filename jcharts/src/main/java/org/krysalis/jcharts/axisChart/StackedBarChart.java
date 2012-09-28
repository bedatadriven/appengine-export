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


package org.krysalis.jcharts.axisChart;


import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.imageMap.RectMapArea;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.properties.StackedBarChartProperties;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Rectangle2D;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: StackedBarChart.java,v 1.1 2003/05/17 16:56:57 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class StackedBarChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param axisChart
	 *********************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet ) throws ChartDataException
	{
		Graphics2D g2d = axisChart.getGraphics2D();
		StackedBarChartProperties stackedBarChartProperties = (StackedBarChartProperties) iAxisChartDataSet.getChartTypeProperties();


		float barWidth;

		//---y axis position on screen to start drawing.
		float startingX;
		float startingY;
		float width;
		float height;


		DataAxisProperties dataAxisProperties;


		if( axisChart.getAxisProperties().isPlotHorizontal() )
		{
			dataAxisProperties = (DataAxisProperties) axisChart.getAxisProperties().getXAxisProperties();
			barWidth = axisChart.getYAxis().getScalePixelWidth() * stackedBarChartProperties.getPercentage();

			startingX = axisChart.getXAxis().getZeroLineCoordinate();
			startingY = axisChart.getYAxis().getLastTickY() - (barWidth / 2);
			width = 0;
			height = barWidth;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			StackedBarChart.horizontalPlot( axisChart, iAxisChartDataSet, stackedBarChartProperties, dataAxisProperties, g2d, rectangle, startingX );
		}
		else
		{
			dataAxisProperties = (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();
			barWidth = axisChart.getXAxis().getScalePixelWidth() * stackedBarChartProperties.getPercentage();

			startingX = axisChart.getXAxis().getTickStart() - (barWidth / 2);
			startingY = axisChart.getYAxis().getZeroLineCoordinate();
			width = barWidth;
			height = 0;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			StackedBarChart.verticalPlot( axisChart, iAxisChartDataSet, stackedBarChartProperties, dataAxisProperties, g2d, rectangle, startingY );
		}
	}


	/**************************************************************************************
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param stackedBarChartProperties
	 * @param dataAxisProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingX
	 **************************************************************************************/
	private static void horizontalPlot( AxisChart axisChart,
													IAxisChartDataSet iAxisChartDataSet,
													StackedBarChartProperties stackedBarChartProperties,
													DataAxisProperties dataAxisProperties,
													Graphics2D g2d,
													Rectangle2D.Float rectangle,
													float startingX ) throws ChartDataException
	{
		int imageMapLabelIndex = axisChart.getYAxis().getNumberOfScaleItems() - 1;

		//LOOP
		//---initial postion of each line.
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			//---draw each bar in stack
			for( int j = 0; j < iAxisChartDataSet.getNumberOfDataSets(); j++ )
			{
				//---if segment has a zero value, draw nothing.
				if( iAxisChartDataSet.getValue( j, i ) == 0 )
				{
					continue;
				}
				else if( iAxisChartDataSet.getValue( j, i ) < 0 )
				{
//todo i think we could support this, but it can wait
					throw new ChartDataException( "Negative values in Stacked Bar charts are not supported yet... Coming soon..." );
/*
					rectangle.x = axisChart.getXAxis().computeAxisCoordinate( axisChart.getXAxis().getOrigin(),
																								 iAxisChartDataSet.getValue( 0, i ),
																								 dataAxisProperties.getScaleCalculator().getMinValue() );
					rectangle.width = startingX - rectangle.x;
*/
				}
				else
				{

					rectangle.width = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( j, i ), axisChart.getXAxis().getOneUnitPixelSize() );


					//rectangle.x = startingX;
					//rectangle.width = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( j, i ), axisChart.getXAxis().getOneUnitPixelSize() );
				}


				g2d.setPaint( iAxisChartDataSet.getPaint( j ) );
				g2d.fill( rectangle );

				if( stackedBarChartProperties.getShowOutlinesFlag() )
				{
					stackedBarChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
				}


				//---if we are generating an ImageMap, store the image coordinates
				if( axisChart.getGenerateImageMapFlag() )
				{
					String label = null;
					if( axisChart.getYAxis().getAxisLabelsGroup() != null )
					{
						label = axisChart.getYAxis().getAxisLabelsGroup().getTextTag( imageMapLabelIndex ).getText();
					}
					axisChart.getImageMap().addImageMapArea( new RectMapArea( rectangle,
																								 iAxisChartDataSet.getValue( j, i ),
																								 label,
																								 iAxisChartDataSet.getLegendLabel( j ) ) );
				}

				rectangle.x += rectangle.width;
			}

			imageMapLabelIndex--;

			rectangle.y += axisChart.getYAxis().getScalePixelWidth();
			rectangle.x = startingX;
		}
	}


	/**************************************************************************************
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param stackedBarChartProperties
	 * @param dataAxisProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingY
	 **************************************************************************************/
	private static void verticalPlot( AxisChart axisChart,
												 IAxisChartDataSet iAxisChartDataSet,
												 StackedBarChartProperties stackedBarChartProperties,
												 DataAxisProperties dataAxisProperties,
												 Graphics2D g2d,
												 Rectangle2D.Float rectangle,
												 float startingY )
	{
		//IDataSeries iDataSeries= (IDataSeries) axisChart.getIAxisDataSeries();

		//LOOP
		//---initial postion of each line.
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			//---draw each bar in stack
			for( int j = 0; j < iAxisChartDataSet.getNumberOfDataSets(); j++ )
			{
				//---if segment has a zero value, draw nothing.
				if( iAxisChartDataSet.getValue( j, i ) == 0 )
				{
					continue;
				}

				rectangle.height = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( j, i ), axisChart.getYAxis().getOneUnitPixelSize() );
				rectangle.y -= rectangle.height;


				g2d.setPaint( iAxisChartDataSet.getPaint( j ) );
				g2d.fill( rectangle );

				if( stackedBarChartProperties.getShowOutlinesFlag() )
				{
					stackedBarChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
				}


				//---if we are generating an ImageMap, store the image coordinates
				if( axisChart.getGenerateImageMapFlag() )
				{
					String label = null;
					if( axisChart.getXAxis().getAxisLabelsGroup() != null )
					{
						label = axisChart.getXAxis().getAxisLabelsGroup().getTextTag( i ).getText();
					}
					axisChart.getImageMap().addImageMapArea( new RectMapArea( rectangle,
																								 iAxisChartDataSet.getValue( j, i ),
																								 label,
																								 iAxisChartDataSet.getLegendLabel( j ) ) );
				}

			}

			rectangle.x += axisChart.getXAxis().getScalePixelWidth();
			rectangle.y = startingY;
		}
	}

}
