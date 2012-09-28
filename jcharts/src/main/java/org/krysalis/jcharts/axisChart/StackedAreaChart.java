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
import org.krysalis.jcharts.imageMap.ImageMapNotSupportedException;
import org.krysalis.jcharts.properties.DataAxisProperties;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Area;
import com.google.code.appengine.awt.geom.GeneralPath;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: StackedAreaChart.java,v 1.1 2003/05/17 16:56:57 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class StackedAreaChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param axisChart
	 * @param iAxisChartDataSet
	 *********************************************************************************************/
	static void render( AxisChart axisChart, IAxisChartDataSet iAxisChartDataSet )
	{
		//---hopefully eliminate support requests asking about this...
		if( axisChart.getImageMap() != null )
		{
			throw new ImageMapNotSupportedException( "HTML client-side image maps are not supported on Area Charts." );
		}

		//AreaChartProperties areaChartProperties=(AreaChartProperties) iAxisChartDataSet.getChartTypeProperties();

		float xPosition=axisChart.getXAxis().getTickStart();

		GeneralPath generalPaths[]=new GeneralPath[ iAxisChartDataSet.getNumberOfDataSets() ];


		//---cache the computed values
		//float[][] yAxisCoordinates= new float[ iAxisChartDataSet.getNumberOfDataSets() ][ iAxisChartDataSet.getNumberOfDataItems() ];

		//---StackedAreaCharts can not be drawn on a horizontal axis so y-axis will always be the data axis
		DataAxisProperties dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();


		float stackedValue=0f;


		//LOOP
		//---initial postion of each line must be set with call to moveTo()
		//---Do this here so every point does not have to check....if( i == 0 )... in loop
		for( int i=0; i < generalPaths.length; i++ )
		{
			generalPaths[ i ]=new GeneralPath();

			generalPaths[ i ].moveTo( xPosition, axisChart.getYAxis().getZeroLineCoordinate() );

			stackedValue+=iAxisChartDataSet.getValue( i, 0 );
			generalPaths[ i ].lineTo( xPosition, axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																														stackedValue,
																														axisChart.getYAxis().getScaleCalculator().getMinValue() ) );
		}

		//LOOP
		for( int j=1; j < iAxisChartDataSet.getNumberOfDataItems(); j++ )
		{
			xPosition+=axisChart.getXAxis().getScalePixelWidth();

			stackedValue=0f;

			//LOOP
			for( int i=0; i < generalPaths.length; i++ )
			{
				stackedValue+=iAxisChartDataSet.getValue( i, j );
				generalPaths[ i ].lineTo( xPosition, axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																															stackedValue,
																															axisChart.getYAxis().getScaleCalculator().getMinValue() ) );
			}
		}


		Area[] areas=new Area[ generalPaths.length ];

		Area totalArea=null;
		Area newArea;


		//LOOP
		//---close the path and create an Area so can stack them
		for( int i=0; i < generalPaths.length; i++ )
		{
			generalPaths[ i ].lineTo( xPosition, axisChart.getYAxis().getZeroLineCoordinate() );

			generalPaths[ i ].closePath();
			//generalPaths[ i ].lineTo( axisChart.getXAxisProperties().getTickStartX(), axisChart.getYAxisProperties().getZeroLineCoordinate() );

			newArea=new Area( generalPaths[ i ] );

			//---if this is not the first area, subtract cumulative area
			if( i > 0 )
			{
				areas[ i ]=(Area) newArea.clone();
				areas[ i ].subtract( totalArea );

				totalArea=newArea;
			}
			else
			{
				areas[ 0 ]=newArea;
				totalArea=areas[ 0 ];
			}
		}


		Graphics2D g2d=axisChart.getGraphics2D();

		//LOOP
		//---draw each path to the image
		for( int i=0; i < areas.length; i++ )
		{
			g2d.setPaint( iAxisChartDataSet.getPaint( i ) );
			//g2d.setStroke( lineChartProperties.getLineStrokes()[ i ] );
			g2d.fill( areas[ i ] );
		}
	}

}
