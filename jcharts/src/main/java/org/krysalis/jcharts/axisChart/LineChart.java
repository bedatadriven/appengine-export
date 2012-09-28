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
import org.krysalis.jcharts.chartData.interfaces.IDataSeries;
import org.krysalis.jcharts.imageMap.CircleMapArea;
import org.krysalis.jcharts.properties.*;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.*;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: LineChart.java,v 1.3 2003/09/26 00:44:02 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class LineChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 * @throws PropertyException
	 *********************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet ) throws PropertyException
	{
		Graphics2D g2d=axisChart.getGraphics2D();

		LineChartProperties lineChartProperties=(LineChartProperties) iAxisChartDataSet.getChartTypeProperties();
		lineChartProperties.validate( iAxisChartDataSet );

		//DataAxisProperties dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();
      IDataSeries iDataSeries= (IDataSeries) axisChart.getIAxisDataSeries();

		//---cache the computed values
		float[][] yAxisCoordinates=new float[ iAxisChartDataSet.getNumberOfDataSets() ][ iAxisChartDataSet.getNumberOfDataItems() ];

		//---need this for image map calculation
		float xMapCoordinate=axisChart.getXAxis().getTickStart();


		//LOOP
		for( int j=0; j < iAxisChartDataSet.getNumberOfDataItems(); j++ )
		{
			//LOOP
			for( int i=0; i < yAxisCoordinates.length; i++ )
			{
				if( iAxisChartDataSet.getValue( i, j ) != Double.NaN )
				{
                                        // Dual Y axis changes integrated CMC 25Aug03
					//yAxisCoordinates[ i ][ j ]= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
					//																								iAxisChartDataSet.getValue( i, j ),
					//																								axisChart.getYAxis().getScaleCalculator().getMinValue() );

                                        // The coordinates of the line charts are drawn with the default scale and
                                        // multiplicate with the second scale for the right axis (by default equal 1)
                                        // if the second scale at the right is unchanged then there will be no impact
                                        yAxisCoordinates[ i ][ j ]= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
                                                                                    iAxisChartDataSet.getValue( i, j )*axisChart.axisProperties.getYAxisProperties().getSecondScaleRight(),
                                                                                    axisChart.getYAxis().getScaleCalculator().getMinValue() );

					//---if we are generating an ImageMap, store the image coordinates
					if( axisChart.getGenerateImageMapFlag() )
					{
						String label;
						if( axisChart.getXAxis().getAxisLabelsGroup() != null )
						{
							label = axisChart.getXAxis().getAxisLabelsGroup().getTextTag( j ).getText();
						}
						else
						{
							label = null;
						}

						axisChart.getImageMap().addImageMapArea( new CircleMapArea( xMapCoordinate,
																										yAxisCoordinates[ i ][ j ],
																										iAxisChartDataSet.getValue( i, j ),
																										label,
																										iAxisChartDataSet.getLegendLabel( i ) ) );
					}
				}
				else
				{
					yAxisCoordinates[ i ][ j ]=Float.NaN;
				}
			}

			xMapCoordinate+=axisChart.getXAxis().getScalePixelWidth();
		}


		AffineTransform originalTransform=null;
		double[] cornerXOffset=null;
		double[] cornerYOffset=null;

		//---check if there are any points to display
		if( lineChartProperties.getShapes() != null )
		{
			//---when centering the shapes on the points, need x and y offset to do this
			cornerXOffset=new double[ iAxisChartDataSet.getNumberOfDataSets() ];
			cornerYOffset=new double[ iAxisChartDataSet.getNumberOfDataSets() ];

			//---get the original transform so can reset it.
			originalTransform=g2d.getTransform();

			Rectangle2D rectangle;

			//LOOP
			//---pre-compute the dimensions of each Shape so do not do it in loop.
			for( int i=0; i < iAxisChartDataSet.getNumberOfDataSets(); i++ )
			{
				if( lineChartProperties.getShapes()[ i ] != null )
				{
					rectangle=lineChartProperties.getShapes()[ i ].getBounds2D();
					cornerXOffset[ i ]=rectangle.getWidth() / 2;
					cornerYOffset[ i ]=rectangle.getHeight() / 2;
				}
			}
		}


		//---init for first segment
		Line2D.Float line=new Line2D.Float( axisChart.getXAxis().getTickStart(),
														yAxisCoordinates[ 0 ][ 0 ],
														axisChart.getXAxis().getTickStart(),
														yAxisCoordinates[ 0 ][ 0 ] );
		//---make sure not plotting a chart with only one data point.
		if( yAxisCoordinates[ 0 ].length > 1 )
		{
			line.y2= yAxisCoordinates[ 0 ][ 1 ];
		}


		//LOOP
		//---draw each line to the image
		for( int i=0; i < yAxisCoordinates.length; i++ )
		{
			line.x1=axisChart.getXAxis().getTickStart();
			line.y1=yAxisCoordinates[ i ][ 0 ];
			line.x2=line.x1;

			//LOOP
			for( int j=1; j < yAxisCoordinates[ 0 ].length; j++ )
			{
				//---if current point on line should be drawn
				if( !Float.isNaN( yAxisCoordinates[ i ][ j ] ) )
				{
					//---if the previous point was not drawn, no line
					if( Float.isNaN( yAxisCoordinates[ i ][ j - 1 ] ) )
					{
						line.x2+=axisChart.getXAxis().getScalePixelWidth();
						line.x1=line.x2;
						line.y1=yAxisCoordinates[ i ][ j ];
						line.y2=yAxisCoordinates[ i ][ j ];

						continue;
					}


					line.x2+=axisChart.getXAxis().getScalePixelWidth();
					line.y2=yAxisCoordinates[ i ][ j ];

					g2d.setPaint( iAxisChartDataSet.getPaint( i ) );
					g2d.setStroke( lineChartProperties.getLineStrokes()[ i ] );
					g2d.draw( line );

					//---plot the Point
					if( lineChartProperties.getShapes()[ i ] != null )
					{
						//---translate the Shape into position.
						g2d.translate( line.x1 - cornerXOffset[ i ], line.y1 - cornerYOffset[ i ] );

						g2d.setPaint( iAxisChartDataSet.getPaint( i ) );
						g2d.fill( lineChartProperties.getShapes()[ i ] );

						//---translate back to the original position
						g2d.setTransform( originalTransform );
					}

					line.x1=line.x2;
					line.y1=line.y2;
				}
				else
				{
					if( ( !Float.isNaN( yAxisCoordinates[ i ][ j - 1 ] ) ) && ( lineChartProperties.getShapes()[ i ] != null ) )
					{
						//---translate the Shape into position.
						g2d.translate( line.x1 - cornerXOffset[ i ], line.y1 - cornerYOffset[ i ] );

						g2d.setPaint( iAxisChartDataSet.getPaint( i ) );
						g2d.fill( lineChartProperties.getShapes()[ i ] );

						//---translate back to the original position
						g2d.setTransform( originalTransform );
					}

					line.x2+=axisChart.getXAxis().getScalePixelWidth();
					line.x1=line.x2;
				}
			}


			//---put the last shape on the line
			if( ( !Float.isNaN( yAxisCoordinates[ i ][ yAxisCoordinates[ i ].length - 1 ] ) ) && ( lineChartProperties.getShapes()[ i ] != null ) )
			{
				//---translate the Shape into position.
				g2d.translate( line.x2 - cornerXOffset[ i ], line.y2 - cornerYOffset[ i ] );

				g2d.setPaint( iAxisChartDataSet.getPaint( i ) );
				g2d.fill( lineChartProperties.getShapes()[ i ] );

				//---translate back to the original position
				g2d.setTransform( originalTransform );
			}
		}
	}


}
