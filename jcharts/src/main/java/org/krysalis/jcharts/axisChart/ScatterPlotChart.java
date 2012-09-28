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


import org.krysalis.jcharts.chartData.interfaces.*;
import org.krysalis.jcharts.imageMap.CircleMapArea;
import org.krysalis.jcharts.properties.*;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.*;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: ScatterPlotChart.java,v 1.2 2003/11/02 13:34:17 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class ScatterPlotChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param scatterPlotAxisChart
	 * @param iScatterPlotDataSet
	 *********************************************************************************************/
	static void render( ScatterPlotAxisChart scatterPlotAxisChart, IScatterPlotDataSet iScatterPlotDataSet )
	{
		//---cache the computed values
		float[][] xAxisCoordinates = new float[ iScatterPlotDataSet.getNumberOfDataSets() ][ iScatterPlotDataSet.getNumberOfDataItems() ];
		float[][] yAxisCoordinates = new float[ iScatterPlotDataSet.getNumberOfDataSets() ][ iScatterPlotDataSet.getNumberOfDataItems() ];


//System.out.println( "x origin= " + scatterPlotAxisChart.getXAxis().getOrigin() + "  y origin= " + scatterPlotAxisChart.getYAxis().getOrigin() );

		//LOOP
		for( int index = 0; index < iScatterPlotDataSet.getNumberOfDataItems(); index++ )
		{
			//LOOP
			for( int dataSet = 0; dataSet < yAxisCoordinates.length; dataSet++ )
			{
				if( iScatterPlotDataSet.getValue( dataSet, index ) != null )
				{
					xAxisCoordinates[ dataSet ][ index ] = scatterPlotAxisChart.getXAxis().computeAxisCoordinate( scatterPlotAxisChart.getXAxis().getOrigin(),
																																				 iScatterPlotDataSet.getValue( dataSet, index ).getX(),
																																				 scatterPlotAxisChart.getXAxis().getScaleCalculator().getMinValue() );

					yAxisCoordinates[ dataSet ][ index ] = scatterPlotAxisChart.getYAxis().computeAxisCoordinate( scatterPlotAxisChart.getYAxis().getOrigin(),
																																				 iScatterPlotDataSet.getValue( dataSet, index ).getY(),
																																				 scatterPlotAxisChart.getYAxis().getScaleCalculator().getMinValue() );

//System.out.println( "x= " + xAxisCoordinates[ dataSet ][ index ] + "  y= " + yAxisCoordinates[ dataSet ][ index ] );

					//---if we are generating an ImageMap, store the image coordinates
					if( scatterPlotAxisChart.getGenerateImageMapFlag() )
					{
						scatterPlotAxisChart.getImageMap().addImageMapArea( new CircleMapArea( xAxisCoordinates[ dataSet ][ index ],
																													  yAxisCoordinates[ dataSet ][ index ],
																													  iScatterPlotDataSet.getValue( dataSet, index ),
																													  iScatterPlotDataSet.getLegendLabel( dataSet ) ) );
					}
				}
				else
				{
					xAxisCoordinates[ dataSet ][ index ] = Float.NaN;
					yAxisCoordinates[ dataSet ][ index ] = Float.NaN;
				}
			}
		}


		ScatterPlotProperties scatterPlotProperties = (ScatterPlotProperties) iScatterPlotDataSet.getChartTypeProperties();
		//DataAxisProperties xAxisProperties = (DataAxisProperties) scatterPlotAxisChart.getAxisProperties().getXAxisProperties();
		//DataAxisProperties yAxisProperties = (DataAxisProperties) scatterPlotAxisChart.getAxisProperties().getYAxisProperties();

		Graphics2D g2d = scatterPlotAxisChart.getGraphics2D();
		AffineTransform originalTransform = null;
		double[] cornerXOffset = null;
		double[] cornerYOffset = null;

		//---check if there are any points to display
		if( scatterPlotProperties.getShapes() != null )
		{
			//---when centering the shapes on the points, need x and y offset to do this
			cornerXOffset = new double[ iScatterPlotDataSet.getNumberOfDataSets() ];
			cornerYOffset = new double[ iScatterPlotDataSet.getNumberOfDataSets() ];

			//---get the original transform so can reset it.
			originalTransform = g2d.getTransform();

			Rectangle2D rectangle;

			//LOOP
			//---pre-compute the dimensions of each Shape so do not do it in loop.
			for( int i = 0; i < iScatterPlotDataSet.getNumberOfDataSets(); i++ )
			{
				if( scatterPlotProperties.getShapes()[ i ] != null )
				{
					rectangle = scatterPlotProperties.getShapes()[ i ].getBounds2D();
					cornerXOffset[ i ] = rectangle.getWidth() / 2;
					cornerYOffset[ i ] = rectangle.getHeight() / 2;
				}
			}
		}


		//---init for first segment
		Line2D.Float line = new Line2D.Float( xAxisCoordinates[ 0 ][ 0 ],
														  yAxisCoordinates[ 0 ][ 0 ],
														  xAxisCoordinates[ 0 ][ 1 ],
														  yAxisCoordinates[ 0 ][ 1 ] );
		//---make sure not plotting a chart with only one data point.
		if( yAxisCoordinates[ 0 ].length > 1 )
		{
//todo what is this for?
			line.y2 = yAxisCoordinates[ 0 ][ 1 ];
		}


		//LOOP
		//---draw each line to the image
		for( int i = 0; i < yAxisCoordinates.length; i++ )
		{
			line.x1 = xAxisCoordinates[ i ][ 0 ];
			line.y1 = yAxisCoordinates[ i ][ 0 ];
			line.x2 = line.x1;

			//LOOP
			for( int j = 1; j < yAxisCoordinates[ 0 ].length; j++ )
			{
				//---if current point on line should be drawn
				if( !Float.isNaN( yAxisCoordinates[ i ][ j ] ) )
				{
					//---if the previous point was not drawn, no line
					if( Float.isNaN( yAxisCoordinates[ i ][ j - 1 ] ) )
					{
						line.x1 = xAxisCoordinates[ i ][ j ];
						line.y1 = yAxisCoordinates[ i ][ j ];
						line.x2 = xAxisCoordinates[ i ][ j ];
						line.y2 = yAxisCoordinates[ i ][ j ];
						continue;
					}


					line.x2 = xAxisCoordinates[ i ][ j ];
					line.y2 = yAxisCoordinates[ i ][ j ];

					g2d.setPaint( iScatterPlotDataSet.getPaint( i ) );
					g2d.setStroke( scatterPlotProperties.getLineStrokes()[ i ] );
					g2d.draw( line );

					//---plot the Point
					if( scatterPlotProperties.getShapes()[ i ] != null )
					{
						//---translate the Shape into position.
						g2d.translate( line.x1 - cornerXOffset[ i ], line.y1 - cornerYOffset[ i ] );

						g2d.setPaint( iScatterPlotDataSet.getPaint( i ) );
						g2d.fill( scatterPlotProperties.getShapes()[ i ] );

						//---translate back to the original position
						g2d.setTransform( originalTransform );
					}

					line.x1 = line.x2;
					line.y1 = line.y2;
				}
				else
				{
					if( (!Float.isNaN( yAxisCoordinates[ i ][ j - 1 ] )) && ( scatterPlotProperties.getShapes()[ i ] != null ) )
					{
						//---translate the Shape into position.
						g2d.translate( line.x1 - cornerXOffset[ i ], line.y1 - cornerYOffset[ i ] );

						g2d.setPaint( iScatterPlotDataSet.getPaint( i ) );
						g2d.fill( scatterPlotProperties.getShapes()[ i ] );

						//---translate back to the original position
						g2d.setTransform( originalTransform );
					}

					line.x2 = scatterPlotAxisChart.getXAxis().getScalePixelWidth();
					line.x1 = line.x2;
				}
			}


			//---put the last shape on the line
			if( (!Float.isNaN( yAxisCoordinates[ i ][ yAxisCoordinates[ i ].length - 1 ] )) && ( scatterPlotProperties.getShapes()[ i ] != null ) )
			{
				//---translate the Shape into position.
				g2d.translate( line.x2 - cornerXOffset[ i ], line.y2 - cornerYOffset[ i ] );

				g2d.setPaint( iScatterPlotDataSet.getPaint( i ) );
				g2d.fill( scatterPlotProperties.getShapes()[ i ] );

				//---translate back to the original position
				g2d.setTransform( originalTransform );
			}
		}
	}


}
