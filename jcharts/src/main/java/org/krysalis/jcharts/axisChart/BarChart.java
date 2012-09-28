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


import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.geom.Rectangle2D;

import org.krysalis.jcharts.axisChart.customRenderers.axisValue.AxisValueRenderEvent;
import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.chartText.TextTagGroup;
import org.krysalis.jcharts.imageMap.RectMapArea;
import org.krysalis.jcharts.properties.BarChartProperties;


/*****************************************************************************************
 * @author Nathaniel Auvil
 * @version $Id: BarChart.java,v 1.3 2004/05/27 02:15:19 nathaniel_auvil Exp $
 ****************************************************************************************/
abstract class BarChart
{

	/***************************************************************************************
	 * Draws the chart uses Rectangle2D......keep having rounding problems.
	 * 
	 * @param axisChart
	 * @param iAxisChartDataSet
	 **************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet )
	{
		Graphics2D g2d = axisChart.getGraphics2D();
		BarChartProperties barChartProperties = (BarChartProperties) iAxisChartDataSet.getChartTypeProperties();
		float barWidth;

		//---y axis position on screen to start drawing.
		float startingX;
		float startingY;
		float width;
		float height;

		if( axisChart.getAxisProperties().isPlotHorizontal() )
		{
			barWidth = axisChart.getYAxis().getScalePixelWidth() * barChartProperties.getPercentage();
			startingX = axisChart.getXAxis().getZeroLineCoordinate();
			startingY = axisChart.getYAxis().getLastTickY() - (barWidth / 2);
			width = 0;
			height = barWidth;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			BarChart.horizontalPlot(	axisChart,
												iAxisChartDataSet,
												barChartProperties,
												g2d,
												rectangle,
												startingX );
		}
		else
		{
			barWidth = axisChart.getXAxis().getScalePixelWidth() * barChartProperties.getPercentage();
			startingX = axisChart.getXAxis().getTickStart() - (barWidth / 2);
			startingY = axisChart.getYAxis().getZeroLineCoordinate();
			width = barWidth;
			height = 0;
			Rectangle2D.Float rectangle = new Rectangle2D.Float( startingX, startingY, width, height );

			BarChart.verticalPlot(	axisChart,
											iAxisChartDataSet,
											barChartProperties,
											g2d,
											rectangle,
											startingY );
		}
	}


	/***************************************************************************************
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param barChartProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingX
	 **************************************************************************************/
	private static void horizontalPlot( AxisChart axisChart,
													IAxisChartDataSet iAxisChartDataSet,
													BarChartProperties barChartProperties,
													Graphics2D g2d,
													Rectangle2D.Float rectangle,
													float startingX )
	{
		int imageMapLabelIndex = axisChart.getYAxis().getNumberOfScaleItems() - 1;

		//---setup the total area rectangle
		Rectangle2D.Float totalItemArea = new Rectangle2D.Float();
		totalItemArea.y = axisChart.getYAxis().getOrigin() - axisChart.getYAxis().getPixelLength() + 1;
		totalItemArea.height = axisChart.getYAxis().getScalePixelWidth() - 1;
		totalItemArea.x = axisChart.getXAxis().getOrigin() + 1;
		totalItemArea.width = axisChart.getXAxis().getPixelLength() - 1;

		//---reuse the same Object for pre and post render events.
		AxisValueRenderEvent axisValueRenderEvent = new AxisValueRenderEvent( axisChart,
																									iAxisChartDataSet,
																									g2d,
																									totalItemArea,
																									axisChart.getXAxis().getZeroLineCoordinate() );

		//---there is only ever one data set for a regular bar chart
		axisValueRenderEvent.setDataSetIndex( 0 );

		//LOOP
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			//---reset the paint as it might have changed for the outline drawing
			g2d.setPaint( iAxisChartDataSet.getPaint( 0 ) );

			//---set values for the preRender event
			axisValueRenderEvent.setValueX( axisChart.getXAxis().getZeroLineCoordinate() );
			axisValueRenderEvent.setValueY( (float) rectangle.getCenterY() );
			axisValueRenderEvent.setValueIndex( i );

			//---we want to do this regardless if we render an item
			barChartProperties.firePreRender( axisValueRenderEvent );

			//---if value == 0 do not plot anything.
			if( iAxisChartDataSet.getValue( 0, i ) != 0.0d )
			{
				if( iAxisChartDataSet.getValue( 0, i ) < 0 )
				{
					rectangle.x = axisChart.getXAxis().computeAxisCoordinate( axisChart.getXAxis().getOrigin(),
																									iAxisChartDataSet.getValue( 0, i ),
																									axisChart.getXAxis().getScaleCalculator().getMinValue() );
					rectangle.width = startingX - rectangle.x;

					//---set values for the postRender event
					axisValueRenderEvent.setValueX( rectangle.x );
				}
				else
				{
					rectangle.x = startingX;
					rectangle.width = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( 0, i ),
																							axisChart.getXAxis().getOneUnitPixelSize() );

					//---set values for the postRender event
					axisValueRenderEvent.setValueX( rectangle.x + rectangle.width );
				}

				//---with a user defined scale, we could have non-zero data points with a height of zero.
				if( rectangle.width != 0 )
				{
					g2d.fill( rectangle );

					if( barChartProperties.getShowOutlinesFlag() )
					{
						barChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
					}

					//---if we are generating an ImageMap, store the image coordinates
					if( axisChart.getGenerateImageMapFlag() )
					{
						RectMapArea rectMapArea= createImageMapArea( axisChart.getYAxis().getAxisLabelsGroup(),
																					imageMapLabelIndex,
																					rectangle,
																					iAxisChartDataSet.getValue( 0, i ),
																					iAxisChartDataSet.getLegendLabel( 0 ) );
						axisChart.getImageMap().addImageMapArea( rectMapArea );
						
						imageMapLabelIndex--;
					}
				}
			}
			else
			{
				//---else, value is zero.

				//---if we are generating an ImageMap, store the image coordinates
				if( axisChart.getGenerateImageMapFlag() )
				{
					//---check if zero scale line is visible
					//---make sure zero is even visible on the scale. 
					//---If zero is not visible on scale nothing will be clickable for that item
					if( axisChart.getXAxis().getScaleCalculator().getMaxValue() >= 0
							&& axisChart.getXAxis().getScaleCalculator().getMinValue() <= 0 )
					{
						rectangle.x = startingX;
						rectangle.width = 1;
							
						RectMapArea rectMapArea = createImageMapArea( axisChart.getYAxis().getAxisLabelsGroup(),
																						imageMapLabelIndex,
																						rectangle,
																						iAxisChartDataSet.getValue( 0, i ),
																						iAxisChartDataSet.getLegendLabel( 0 ) );

						axisChart.getImageMap().addImageMapArea( rectMapArea );						
					}
					
					imageMapLabelIndex--;
				}
			}

			//---notify everyone we just rendered
			barChartProperties.firePostRender( axisValueRenderEvent );
			totalItemArea.y += axisChart.getYAxis().getScalePixelWidth();

			rectangle.y += axisChart.getYAxis().getScalePixelWidth();
		}
	}


	/***************************************************************************************
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @param barChartProperties
	 * @param g2d
	 * @param rectangle
	 * @param startingY
	 **************************************************************************************/
	private static void verticalPlot( AxisChart axisChart,
													IAxisChartDataSet iAxisChartDataSet,
													BarChartProperties barChartProperties,
													Graphics2D g2d,
													Rectangle2D.Float rectangle,
													float startingY )
	{
		//---setup the total area rectangle
		Rectangle2D.Float totalItemArea = new Rectangle2D.Float();
		totalItemArea.x = axisChart.getXAxis().getOrigin() + 1;
		totalItemArea.y = axisChart.getYAxis().getOrigin() - axisChart.getYAxis().getPixelLength() + 1;
		totalItemArea.width = axisChart.getXAxis().getScalePixelWidth() - 1;
		totalItemArea.height = axisChart.getYAxis().getPixelLength() - 1;

		//---reuse the same Object for pre and post render events.
		AxisValueRenderEvent axisValueRenderEvent = new AxisValueRenderEvent( axisChart,
																									iAxisChartDataSet,
																									g2d,
																									totalItemArea,
																									axisChart.getYAxis().getZeroLineCoordinate() );

		//---there is only ever one data set for a regular bar chart
		axisValueRenderEvent.setDataSetIndex( 0 );

		//LOOP
		for( int i = 0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			//---reset the paint as it might have changed for the outline drawing
			g2d.setPaint( iAxisChartDataSet.getPaint( 0 ) );

			//---set values for the preRender event
			axisValueRenderEvent.setValueX( (float) rectangle.getCenterX() );
			axisValueRenderEvent.setValueY( axisChart.getYAxis().getZeroLineCoordinate() );
			axisValueRenderEvent.setValueIndex( i );

			//---we want to do this regardless if we render an item
			barChartProperties.firePreRender( axisValueRenderEvent );

			//---if value == 0 do not plot anything.
			if( iAxisChartDataSet.getValue( 0, i ) != 0.0d )
			{
				if( iAxisChartDataSet.getValue( 0, i ) < 0 )
				{
					rectangle.y = startingY;
					rectangle.height = BarChart.computeScaleHeightOfValue( iAxisChartDataSet.getValue( 0, i ), axisChart.getYAxis().getOneUnitPixelSize() );

					axisValueRenderEvent.setValueY( rectangle.y + rectangle.height );
				}
				else
				{
					rectangle.y = axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																								 iAxisChartDataSet.getValue( 0, i ),
																								 axisChart.getYAxis().getScaleCalculator().getMinValue() );
					rectangle.height = startingY - rectangle.y;

					axisValueRenderEvent.setValueY( rectangle.y );
				}

				
				//---with a user defined scale, we could have non-zero data points with a height of zero.
				if( rectangle.height != 0 )
				{
					g2d.fill( rectangle );

					if( barChartProperties.getShowOutlinesFlag() )
					{
						barChartProperties.getBarOutlineStroke().draw( g2d, rectangle );
						g2d.setPaint( iAxisChartDataSet.getPaint( 0 ) );
					}
				}

				//---even if the bar is not visible, we still need to generate an ImageMapArea
				if( axisChart.getGenerateImageMapFlag() )
				{
					RectMapArea rectMapArea = createImageMapArea( axisChart.getXAxis().getAxisLabelsGroup(),
																					i,
																					rectangle,
																					iAxisChartDataSet.getValue( 0, i ),
																					iAxisChartDataSet.getLegendLabel( 0 ) );

					axisChart.getImageMap().addImageMapArea( rectMapArea );
				}
			}
			else
			{
				//---if we are generating an ImageMap, store the image coordinates
				if( axisChart.getGenerateImageMapFlag() )
				{
					//---make sure zero is even visible on the scale. 
					//---If zero is not visible on scale nothing will be clickable for that item
					if( axisChart.getYAxis().getScaleCalculator().getMaxValue() >= 0
							&& axisChart.getYAxis().getScaleCalculator().getMinValue() <= 0 )
					{
						rectangle.y = startingY;
						rectangle.height = 1;
							
						RectMapArea rectMapArea = createImageMapArea( axisChart.getXAxis().getAxisLabelsGroup(),
																						i,
																						rectangle,
																						iAxisChartDataSet.getValue( 0, i ),
																						iAxisChartDataSet.getLegendLabel( 0 ) );

						axisChart.getImageMap().addImageMapArea( rectMapArea );						
					}
				}
			}

			//---notify everyone we just rendered
			barChartProperties.firePostRender( axisValueRenderEvent );
			totalItemArea.x += axisChart.getXAxis().getScalePixelWidth();

			rectangle.x += axisChart.getXAxis().getScalePixelWidth();
		}
	}


	/***************************************************************************************
	 * Takes a value and determines the number of pixels it should fill on the screen. If
	 * there is a user defined scale and the passed value is greater than the MAX or less
	 * than the MIN, the height will be forced to the MAX or MIN respectively.
	 * 
	 * @param value
	 * @param oneUnitPixelSize
	 * @return float the screen pixel coordinate
	 **************************************************************************************/
	static float computeScaleHeightOfValue( double value, double oneUnitPixelSize )
	{
		return (float) Math.abs( (value) * oneUnitPixelSize );
	}


	/***************************************************************************************
	 * @param axisLabelsGroup
	 * @param labelIndex
	 * @param rectangle
	 * @param value
	 * @param legendLabel
	 * @return RectMapArea
	 **************************************************************************************/
	private static RectMapArea createImageMapArea( TextTagGroup axisLabelsGroup,
																   int labelIndex,
																	Rectangle2D.Float rectangle,
																	double value,
																	String legendLabel )
	{
		String label;
		if( axisLabelsGroup != null )
		{
			label = axisLabelsGroup.getTextTag( labelIndex ).getText();
		}
		else
		{
			label = "";
		}

		return new RectMapArea( rectangle, value, label, legendLabel );
	}
}