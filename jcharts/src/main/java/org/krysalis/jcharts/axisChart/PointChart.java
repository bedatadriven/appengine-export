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
import org.krysalis.jcharts.properties.PointChartProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.AffineTransform;
import com.google.code.appengine.awt.geom.Rectangle2D;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: PointChart.java,v 1.1 2003/05/17 16:56:57 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class PointChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 *********************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet )
	{
		Graphics2D g2d=axisChart.getGraphics2D();
		PointChartProperties pointChartProperties=(PointChartProperties) iAxisChartDataSet.getChartTypeProperties();

		//---Point Charts can not be horizontal so we know the y-axis is DataAxisProperties
		DataAxisProperties dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();
		IDataSeries iDataSeries= (IDataSeries) axisChart.getIAxisDataSeries();


		float xPosition=axisChart.getXAxis().getTickStart();
		float yPosition;

		//---when centering the shapes on the points, need x and y offset to do this
		double[] cornerXOffset=new double[ iAxisChartDataSet.getNumberOfDataSets() ];
		double[] cornerYOffset=new double[ iAxisChartDataSet.getNumberOfDataSets() ];


		//---get the original transform so can reset it.
		AffineTransform affineTransform=g2d.getTransform();

		Rectangle2D rectangle;

		//LOOP
		//---pre-compute the dimensions of each Shape so do not do it in loop.
		for( int i=0; i < iAxisChartDataSet.getNumberOfDataSets(); i++ )
		{
			rectangle=pointChartProperties.getShape( i ).getBounds2D();
			cornerXOffset[ i ]=rectangle.getWidth() / 2;
			cornerYOffset[ i ]=rectangle.getHeight() / 2;
		}

		g2d.setStroke( PointChartProperties.DEFAULT_POINT_BORDER_STROKE );

		//LOOP
		//---for each item in data set...
		for( int i=0; i < iAxisChartDataSet.getNumberOfDataItems(); i++ )
		{
			//LOOP
			//---for each data set
			for( int dataSetIndex=0; dataSetIndex < iAxisChartDataSet.getNumberOfDataSets(); dataSetIndex++ )
			{
				//---if the value is NaN, do not display a value
				if( Double.isNaN( iAxisChartDataSet.getValue( dataSetIndex, i ) ) )
				{
					continue;
				}

				yPosition= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																						 iAxisChartDataSet.getValue( dataSetIndex, i ),
																						 axisChart.getYAxis().getScaleCalculator().getMinValue() );

				//---if we are generating an ImageMap, store the image coordinates
				if( axisChart.getGenerateImageMapFlag() )
				{
					String label;
					if( axisChart.getXAxis().getAxisLabelsGroup() != null )
					{
						label = axisChart.getXAxis().getAxisLabelsGroup().getTextTag( i ).getText();
					}
					else
					{
						label = null;
					}

					axisChart.getImageMap().addImageMapArea( new CircleMapArea( xPosition,
																									yPosition,
																									iAxisChartDataSet.getValue( dataSetIndex, i ),
																									label,
																									iAxisChartDataSet.getLegendLabel( dataSetIndex ) ) );
				}

				//---translate the Shape into position.
				g2d.translate( xPosition - cornerXOffset[ dataSetIndex ], yPosition - cornerYOffset[ dataSetIndex ] );

				g2d.setPaint( iAxisChartDataSet.getPaint( dataSetIndex ) );

				//---render the point
				if( pointChartProperties.getFillPointsFlag( dataSetIndex ) )
				{
					//---fill the point
					g2d.fill( pointChartProperties.getShape( dataSetIndex ) );

					//---if we are filling the points, see if we should outline the Shape
					if( pointChartProperties.getPointOutlinePaints( dataSetIndex ) != null )
					{
						g2d.setPaint( pointChartProperties.getPointOutlinePaints( dataSetIndex ) );
						g2d.draw( pointChartProperties.getShape( dataSetIndex ) );
					}
				}
				else
				{
					//---if we are NOT filling the points, use the Paint specified with the DataSet to outline.
					g2d.draw( pointChartProperties.getShape( dataSetIndex ) );
				}

				g2d.setTransform( affineTransform );
			}

			//---move right to the next x-axis scale item
			xPosition+=axisChart.getXAxis().getScalePixelWidth();
		}
	}
}
