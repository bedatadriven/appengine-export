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


import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.imageMap.RectMapArea;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.AxisValueRenderEvent;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Rectangle2D;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: ClusteredBarChart.java,v 1.2 2003/12/07 14:04:27 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class ClusteredBarChart
{

	/********************************************************************************************
	 * Draws the chart
	 * uses Rectangle2D......keep having rounding problems.
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 *********************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet )
	{
		Graphics2D g2d = axisChart.getGraphics2D();
		ClusteredBarChartProperties clusteredBarChartProperties = ( ClusteredBarChartProperties ) iAxisChartDataSet.getChartTypeProperties();


		float barGroupWidth;
		float barWidth;

		//---y axis position on screen to start drawing.
		float startingY;
		float startingX;
		float width;
		float height;

		DataAxisProperties dataAxisProperties;
		if( axisChart.getAxisProperties().isPlotHorizontal() )
		{
			dataAxisProperties = ( DataAxisProperties ) axisChart.getAxisProperties().getXAxisProperties();

			barGroupWidth = axisChart.getYAxis().getScalePixelWidth() * clusteredBarChartProperties.getPercentage();
			barWidth = barGroupWidth / iAxisChartDataSet.getNumberOfDataSets();

			//---where the group of bars starts
			startingX = axisChart.getXAxis().getZeroLineCoordinate();
			startingY = axisChart.getYAxis().getLastTickY() - ( barGroupWidth / 2 );
			width = 0;
			height = barWidth;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			horizontalPlot( axisChart,
								 iAxisChartDataSet,
								 clusteredBarChartProperties,
								 dataAxisProperties,
								 g2d,
								 rectangle,
								 startingX,
								 startingY,
								 barWidth );
		}
		else
		{
			dataAxisProperties = ( DataAxisProperties ) axisChart.getAxisProperties().getYAxisProperties();

			barGroupWidth = axisChart.getXAxis().getScalePixelWidth() * clusteredBarChartProperties.getPercentage();
			barWidth = barGroupWidth / iAxisChartDataSet.getNumberOfDataSets();

			//---where the group of bars starts
			startingX = axisChart.getXAxis().getTickStart() - ( barGroupWidth / 2 );
			startingY = axisChart.getYAxis().getZeroLineCoordinate();
			width = barWidth;
			height = 0;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			verticalPlot( axisChart,
							  iAxisChartDataSet,
							  clusteredBarChartProperties,
							  dataAxisProperties,
							  g2d,
							  rectangle,
							  startingX,
							  startingY,
							  barWidth );
		}
	}


	/*******************************************************************************************
	 *
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param clusteredBarChartProperties
	 * @param dataAxisProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingX
	 * @param startingY
	 * @param barWidth
	 ********************************************************************************************/
	private static void horizontalPlot( AxisChart axisChart,
													IAxisChartDataSet iAxisChartDataSet,
													ClusteredBarChartProperties clusteredBarChartProperties,
													DataAxisProperties dataAxisProperties,
													Graphics2D g2d,
													Rectangle2D.Float rectangle,
													float startingX,
													float startingY,
													float barWidth )
	{
      int imageMapLabelIndex = axisChart.getYAxis().getNumberOfScaleItems() - 1;

		//---setup the total area rectangle
		Rectangle2D.Float totalItemArea= new Rectangle2D.Float();
      totalItemArea.y = axisChart.getYAxis().getOrigin() - axisChart.getYAxis().getPixelLength() + 1;
		totalItemArea.height= axisChart.getYAxis().getScalePixelWidth() - 1;
		totalItemArea.x= axisChart.getXAxis().getOrigin() + 1;
		totalItemArea.width= axisChart.getXAxis().getPixelLength() - 1;


      //---reuse the same Object for pre and post render events.
		AxisValueRenderEvent axisValueRenderEvent= new AxisValueRenderEvent( axisChart,
																									iAxisChartDataSet,
																									g2d,
																									totalItemArea,
																									axisChart.getXAxis().getZeroLineCoordinate() );


		//LOOP
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			for( int j = 0; j < iAxisChartDataSet.getNumberOfDataSets(); j++ )
			{
				g2d.setPaint( iAxisChartDataSet.getPaint( j ) );

				//---there is only ever one data set for a regular bar chart
				axisValueRenderEvent.setDataSetIndex( j );

				//---set values for the preRender event
				axisValueRenderEvent.setValueX( axisChart.getXAxis().getZeroLineCoordinate() );
				axisValueRenderEvent.setValueY( (float) rectangle.getCenterY() );
				axisValueRenderEvent.setValueIndex( i );

				//---we want to do this regardless if we render an item
				clusteredBarChartProperties.firePreRender( axisValueRenderEvent );


				//---if value == 0 do not plot anything.
				if( iAxisChartDataSet.getValue( j, i ) != 0.0d )
				{

					if( iAxisChartDataSet.getValue( j, i ) < 0 )
					{
						rectangle.x = axisChart.getXAxis().computeAxisCoordinate( axisChart.getXAxis().getOrigin(),
																									 iAxisChartDataSet.getValue( j, i ),
																									 axisChart.getXAxis().getScaleCalculator().getMinValue() );
						rectangle.width = startingX - rectangle.x;

						//---set values for the postRender event
						axisValueRenderEvent.setValueX( rectangle.x );
					}
					else
					{
						rectangle.x = startingX;
						rectangle.width = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( j, i ),
																								axisChart.getXAxis().getOneUnitPixelSize() );

						//---set values for the postRender event
						axisValueRenderEvent.setValueX( rectangle.x + rectangle.width );
					}



					//---with a user defined scale, we could have non-zero data points with a height of zero.
					if( rectangle.width != 0 )
					{
						g2d.fill( rectangle );

						if( clusteredBarChartProperties.getShowOutlinesFlag() )
						{
							clusteredBarChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
						}


						//---if we are generating an ImageMap, store the image coordinates
						if( axisChart.getGenerateImageMapFlag() )
						{
							String label;
							if( axisChart.getYAxis().getAxisLabelsGroup() != null )
							{
								label= axisChart.getYAxis().getAxisLabelsGroup().getTextTag( imageMapLabelIndex ).getText();
							}
							else
							{
								label= null;
							}

							axisChart.getImageMap().addImageMapArea( new RectMapArea( rectangle,
																										 iAxisChartDataSet.getValue( j, i ),
																										 label,
																										 iAxisChartDataSet.getLegendLabel( j ) ) );
						}
					}
				}

				//---notify everyone we just rendered
				clusteredBarChartProperties.firePostRender( axisValueRenderEvent );

				rectangle.y += barWidth;
			}

			imageMapLabelIndex--;

			startingY += axisChart.getYAxis().getScalePixelWidth();
			rectangle.y = startingY;

			totalItemArea.y+= axisChart.getYAxis().getScalePixelWidth();
		}
	}


	/**************************************************************************************
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param barChartProperties
	 * @param dataAxisProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingY
	 **************************************************************************************/
	private static void verticalPlot( AxisChart axisChart,
												 IAxisChartDataSet iAxisChartDataSet,
												 BarChartProperties barChartProperties,
												 DataAxisProperties dataAxisProperties,
												 Graphics2D g2d,
												 Rectangle2D.Float rectangle,
												 float barGroupStartingX,
												 float startingY,
												 float barWidth )
	{

		//---setup the total area rectangle
		Rectangle2D.Float totalItemArea= new Rectangle2D.Float();
		totalItemArea.x= axisChart.getXAxis().getOrigin() + 1;
		totalItemArea.y = axisChart.getYAxis().getOrigin() - axisChart.getYAxis().getPixelLength() + 1;
		totalItemArea.width= axisChart.getXAxis().getScalePixelWidth() - 1;
		totalItemArea.height= axisChart.getYAxis().getPixelLength() - 1;


		//---reuse the same Object for pre and post render events.
		AxisValueRenderEvent axisValueRenderEvent= new AxisValueRenderEvent( axisChart,
																									iAxisChartDataSet,
																									g2d,
																									totalItemArea,
																									axisChart.getYAxis().getZeroLineCoordinate() );

		//LOOP
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			for( int j = 0; j < iAxisChartDataSet.getNumberOfDataSets(); j++ )
			{
				g2d.setPaint( iAxisChartDataSet.getPaint( j ) );


				//---there is only ever one data set for a regular bar chart
				axisValueRenderEvent.setDataSetIndex( j );


				//---if value == 0 do not plot anything.
				if( iAxisChartDataSet.getValue( j, i ) != 0.0d )
				{
					//---set values for the preRender event
					axisValueRenderEvent.setValueX( (float) rectangle.getCenterX() );
					axisValueRenderEvent.setValueY( axisChart.getYAxis().getZeroLineCoordinate() );
					axisValueRenderEvent.setValueIndex( i );

					//---we want to do this regardless if we render an item
					barChartProperties.firePreRender( axisValueRenderEvent );


					if( iAxisChartDataSet.getValue( j, i ) < 0 )
					{
						rectangle.y = startingY;
						rectangle.height = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( j, i ), axisChart.getYAxis().getOneUnitPixelSize() );

						//---set values for the postRender event
						axisValueRenderEvent.setValueY( rectangle.y + rectangle.height );
					}
					else
					{
						rectangle.y = axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																									 iAxisChartDataSet.getValue( j, i ),
																									 axisChart.getYAxis().getScaleCalculator().getMinValue() );
						rectangle.height = startingY - rectangle.y;

						//---set values for the postRender event
						axisValueRenderEvent.setValueY( rectangle.y );
					}

					//---with a user defined scale, we could have non-zero data points with a height of zero.
					if( rectangle.height != 0 )
					{
						g2d.fill( rectangle );

						if( barChartProperties.getShowOutlinesFlag() )
						{
							barChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
						}

						//---if we are generating an ImageMap, store the image coordinates
						if( axisChart.getGenerateImageMapFlag() )
						{
							String label;
							if( axisChart.getXAxis().getAxisLabelsGroup() != null )
							{
								label= axisChart.getXAxis().getAxisLabelsGroup().getTextTag( i ).getText();
							}
							else
							{
								label= null;
							}

							axisChart.getImageMap().addImageMapArea( new RectMapArea( rectangle,
																										 iAxisChartDataSet.getValue( j, i ),
																										 label,
																										 iAxisChartDataSet.getLegendLabel( j ) ) );
						}
					}

					//---notify everyone we just rendered
					barChartProperties.firePostRender( axisValueRenderEvent );
				}

				rectangle.x += barWidth;
			}

			totalItemArea.x+= axisChart.getXAxis().getScalePixelWidth();

			barGroupStartingX += axisChart.getXAxis().getScalePixelWidth();
			rectangle.x = barGroupStartingX;
		}
	}

}
