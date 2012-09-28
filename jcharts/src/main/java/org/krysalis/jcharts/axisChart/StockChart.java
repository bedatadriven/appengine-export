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


import org.krysalis.jcharts.chartData.interfaces.IStockChartDataSet;
import org.krysalis.jcharts.chartData.interfaces.IDataSeries;
import org.krysalis.jcharts.imageMap.CircleMapArea;
import org.krysalis.jcharts.properties.StockChartProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.types.StockChartDataType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Line2D;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: StockChart.java,v 1.1 2003/05/17 16:56:57 nathaniel_auvil Exp $
 ************************************************************************************/
abstract class StockChart
{

	/********************************************************************************************
	 * Draws the chart
	 *
	 * @param axisChart
	 * @param iStockChartDataSet
	 *********************************************************************************************/
	static final void render( AxisChart axisChart, IStockChartDataSet iStockChartDataSet )
	{
		StockChartProperties stockChartProperties=(StockChartProperties) iStockChartDataSet.getChartTypeProperties();
		Graphics2D g2d=axisChart.getGraphics2D();

		DataAxisProperties dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();
      IDataSeries iDataSeries= (IDataSeries) axisChart.getIAxisDataSeries();


		//---cache the computed values
		//float[][] yAxisCoordinates=new float[ iStockChartDataSet.getNumberOfDataSets() ][ iStockChartDataSet.getNumberOfDataItems() ];


		//---init for first segment
		Line2D.Float line=new Line2D.Float( axisChart.getXAxis().getTickStart(),
														0,
														axisChart.getXAxis().getTickStart(),
														0 );


		Line2D.Float openLine=null;
		if( iStockChartDataSet.hasOpenValues() )
		{
			openLine=new Line2D.Float( axisChart.getXAxis().getTickStart() - stockChartProperties.getOpenPixelLength() - 1,
												0,
												axisChart.getXAxis().getTickStart() - 1,
												0 );
		}


		Line2D.Float closeLine=null;
		if( iStockChartDataSet.hasCloseValues() )
		{
			closeLine=new Line2D.Float( axisChart.getXAxis().getTickStart() + 1,
												 0,
												 axisChart.getXAxis().getTickStart() + stockChartProperties.getClosePixelLength() + 1,
												 0 );
		}


		//LOOP
		//---draw each line to the image
		for( int i=0; i < iStockChartDataSet.getNumberOfDataItems(); i++ )
		{
			line.y1= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																				  iStockChartDataSet.getLowValue( i ),
																				  axisChart.getYAxis().getScaleCalculator().getMinValue() );
			line.y2= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																				  iStockChartDataSet.getHighValue( i ),
																				  axisChart.getYAxis().getScaleCalculator().getMinValue() );

			String label;
			if( axisChart.getXAxis().getAxisLabelsGroup() != null )
			{
				label = axisChart.getXAxis().getAxisLabelsGroup().getTextTag( i ).getText();
			}
			else
			{
				label = null;
			}

			//---if we are generating an ImageMap, store the image coordinates
			if( axisChart.getGenerateImageMapFlag() )
			{
				axisChart.getImageMap().addImageMapArea( new CircleMapArea( line.x1,
																								line.y1,
																								iStockChartDataSet.getLowValue( i ),
																								label,
																								iStockChartDataSet.getLegendLabel( StockChartDataType.LOW.getInt() ) ) );

				axisChart.getImageMap().addImageMapArea( new CircleMapArea( line.x2,
																								line.y2,
																								iStockChartDataSet.getHighValue( i ),
																								label,
																								iStockChartDataSet.getLegendLabel( StockChartDataType.HIGH.getInt() ) ) );
			}

			g2d.setPaint( iStockChartDataSet.getPaint( StockChartDataType.HIGH.getInt() ) );
			g2d.setStroke( stockChartProperties.getHiLowStroke() );
			g2d.draw( line );

			line.x1+=axisChart.getXAxis().getScalePixelWidth();
			line.x2=line.x1;

			if( openLine != null )
			{
				if( iStockChartDataSet.getOpenValue( i ) != Double.NaN )
				{
					openLine.y1= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																								iStockChartDataSet.getOpenValue( i ),
																								axisChart.getYAxis().getScaleCalculator().getMinValue() );
					openLine.y2=openLine.y1;

					g2d.setPaint( iStockChartDataSet.getPaint( StockChartDataType.OPEN.getInt() ) );
					g2d.setStroke( stockChartProperties.getOpenStroke() );
					g2d.draw( openLine );

					//---if we are generating an ImageMap, store the image coordinates
					if( axisChart.getGenerateImageMapFlag() )
					{
						axisChart.getImageMap().addImageMapArea( new CircleMapArea( openLine.x1,
																										openLine.y1,
																										iStockChartDataSet.getOpenValue( i ),
																										label,
																										iStockChartDataSet.getLegendLabel( StockChartDataType.OPEN.getInt() ) ) );
					}

					openLine.x1+=axisChart.getXAxis().getScalePixelWidth();
					openLine.x2+=axisChart.getXAxis().getScalePixelWidth();
				}
			}

			if( closeLine != null )
			{
				if( iStockChartDataSet.getOpenValue( i ) != Double.NaN )
				{
					closeLine.y1= axisChart.getYAxis().computeAxisCoordinate( axisChart.getYAxis().getOrigin(),
																								 iStockChartDataSet.getCloseValue( i ),
																								 axisChart.getYAxis().getScaleCalculator().getMinValue() );
					closeLine.y2=closeLine.y1;

					g2d.setPaint( iStockChartDataSet.getPaint( StockChartDataType.CLOSE.getInt() ) );
					g2d.setStroke( stockChartProperties.getCloseStroke() );
					g2d.draw( closeLine );

					//---if we are generating an ImageMap, store the image coordinates
					if( axisChart.getGenerateImageMapFlag() )
					{
						axisChart.getImageMap().addImageMapArea( new CircleMapArea( closeLine.x2,
																										closeLine.y2,
																										iStockChartDataSet.getCloseValue( i ),
																										label,
																										iStockChartDataSet.getLegendLabel( StockChartDataType.CLOSE.getInt() ) ) );
					}

					closeLine.x1+=axisChart.getXAxis().getScalePixelWidth();
					closeLine.x2+=axisChart.getXAxis().getScalePixelWidth();
				}
			}
		}
	}

}
