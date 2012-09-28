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


import org.krysalis.jcharts.axisChart.axis.*;
import org.krysalis.jcharts.axisChart.axis.scale.*;
import org.krysalis.jcharts.chartData.interfaces.*;
import org.krysalis.jcharts.chartData.processors.AxisChartDataProcessor;
import org.krysalis.jcharts.chartData.processors.ScatterPlotDataProcessor;
import org.krysalis.jcharts.chartText.NumericTagGroup;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.test.HTMLChartTestable;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.font.FontRenderContext;



/**************************************************************************************
 * This Class is used to create all axis chart types.  This class knows how to render
 * 	charts based on the ChartType specified in on the iAxisChartDataSet.
 *
 * @author Nathaniel Auvil
 * @version $Id: ScatterPlotAxisChart.java,v 1.2 2003/06/20 01:54:58 nathaniel_auvil Exp $
 ************************************************************************************/
public class ScatterPlotAxisChart extends AxisChart implements HTMLChartTestable
{

	/**************************************************************************************************
	 * Constructor
	 *
	 * @param iScatterPlotDataSeries
	 * @param chartProperties
	 * @param axisProperties
	 * @param legendProperties if no legend is desired, pass NULL
	 * @param pixelWidth
	 * @param pixelHeight
	 ***************************************************************************************************/
	public ScatterPlotAxisChart( IScatterPlotDataSeries iScatterPlotDataSeries,
										  ChartProperties chartProperties,
										  AxisProperties axisProperties,
										  LegendProperties legendProperties,
										  int pixelWidth,
										  int pixelHeight )
	{
		super( iScatterPlotDataSeries, chartProperties, axisProperties, legendProperties, pixelWidth, pixelHeight );
	}


	/********************************************************************************************
	 * ScatterPlots create a subclass of AxisChartDataProcessor so we need this method so we can
	 * 	overload it.
	 *
	 * @return AxisChartDataProcessor
	 ********************************************************************************************/
	public AxisChartDataProcessor createAxisChartDataProcessor()
	{
		return new ScatterPlotDataProcessor();
	}


	/************************************************************************************************
	 * Once we determine which axis is the data axis, the logic to set it up is the same whether it
	 * 	is a horizontal or vertical plot.
	 *
	 * @param dataAxisProperties
	 * @param axisChartDataProcessor
	 * @param fontRenderContext
	 * @return NumericTagGroup need to set this on the right axis
	 ************************************************************************************************/
	protected NumericTagGroup setupDataAxisProperties( Axis axis,
																		DataAxisProperties dataAxisProperties,
																		AxisChartDataProcessor axisChartDataProcessor,
																		FontRenderContext fontRenderContext )
	{
		//---we know this is of this type
		ScatterPlotDataProcessor scatterPlotDataProcessor= (ScatterPlotDataProcessor) axisChartDataProcessor;

		if( dataAxisProperties.getScaleCalculator() == null )
		{
			ScaleCalculator s;

			if( dataAxisProperties.hasUserDefinedScale() )
			{
				s = new UserDefinedScaleCalculator( dataAxisProperties.getUserDefinedMinimumValue(), dataAxisProperties.getUserDefinedIncrement() );
			}
			else
			{
				s = new AutomaticScaleCalculator();
				if( axis instanceof XAxis )
				{
					s.setMaxValue( scatterPlotDataProcessor.getMaxValue() );
					s.setMinValue( scatterPlotDataProcessor.getMinValue() );
				}
				else
				{
					s.setMaxValue( scatterPlotDataProcessor.getyMax() );
					s.setMinValue( scatterPlotDataProcessor.getyMin() );
				}
			}

			axis.setScaleCalculator( s );
		}
		else
		{
			axis.setScaleCalculator( dataAxisProperties.getScaleCalculator() );

			if( axis instanceof XAxis )
			{
				axis.getScaleCalculator().setMaxValue( scatterPlotDataProcessor.getMaxValue() );
				axis.getScaleCalculator().setMinValue( scatterPlotDataProcessor.getMinValue() );
			}
			else
			{
				axis.getScaleCalculator().setMaxValue( scatterPlotDataProcessor.getyMax() );
				axis.getScaleCalculator().setMinValue( scatterPlotDataProcessor.getyMin() );
			}
		}

		axis.getScaleCalculator().setRoundingPowerOfTen( dataAxisProperties.getRoundToNearest() );
		axis.getScaleCalculator().setNumberOfScaleItems( dataAxisProperties.getNumItems() );
		axis.getScaleCalculator().computeScaleValues();


//TODO what if they do not want to display axis labels?
		NumericTagGroup numericTagGroup = new NumericTagGroup( dataAxisProperties.getScaleChartFont(),
																				 fontRenderContext,
																				 dataAxisProperties.useDollarSigns(),
																				 dataAxisProperties.usePercentSigns(),
																				 dataAxisProperties.useCommas(),
																				 dataAxisProperties.getRoundToNearest() );

		numericTagGroup.createAxisScaleLabels( axis.getScaleCalculator() );

		return numericTagGroup;
	}


	/***************************************************************************************
	 *
	 *
	 * @param axisChartDataProcessor
	 * @param fontRenderContext
	 **************************************************************************************/
	protected void setupAxis( AxisChartDataProcessor axisChartDataProcessor, FontRenderContext fontRenderContext )
	{
		//---X AXIS---------------------------------------------------------------------------
		DataAxisProperties dataAxisProperties = (DataAxisProperties) this.getAxisProperties().getXAxisProperties();
		this.xAxis = new XAxis( this, dataAxisProperties.getNumItems() );
		NumericTagGroup numericTagGroup = this.setupDataAxisProperties( this.xAxis, dataAxisProperties, axisChartDataProcessor, fontRenderContext );
		this.xAxis.setAxisLabelsGroup( numericTagGroup );

		//---Y AXIS---------------------------------------------------------------------------
		dataAxisProperties = (DataAxisProperties) this.getAxisProperties().getYAxisProperties();
		this.yAxis = new YAxis( this, dataAxisProperties.getNumItems() );
		numericTagGroup = this.setupDataAxisProperties( this.yAxis, dataAxisProperties, axisChartDataProcessor, fontRenderContext );
		this.yAxis.setAxisLabelsGroup( numericTagGroup );


		//---if yAxisTitle is null, do not show title
		this.yAxis.computeMinimumWidthNeeded( super.getIAxisDataSeries().getYAxisTitle() );
		this.xAxis.computeMinimumHeightNeeded( super.getIAxisDataSeries().getXAxisTitle() );
	}


	/******************************************************************************************
	 *
	 *****************************************************************************************/
	protected void deriveAxisValues()
	{
		//---Determine how many labels will fit on the x-axis
//TODO should we do this also for the YAxis?
//todo what if they do not want labels on the x-axis?
		this.xAxis.computeLabelFilter();

		this.xAxis.computeShouldTickStartAtYAxis( super.getIAxisDataSeries(), this.axisProperties.getXAxisProperties() );


		//---X Axis--------------------
		//DataAxisProperties dataAxisProperties = (DataAxisProperties) this.axisProperties.getXAxisProperties();
		this.xAxis.computeScalePixelWidthDataAxis( this.axisProperties.getXAxisProperties() );
		this.xAxis.computeOneUnitPixelSize( this.xAxis.getScalePixelWidth(), this.xAxis.getScaleCalculator().getIncrement() );

		//---we ADD to the origin position when doing x-axis
		float zeroLineCoordinate = (float) (this.xAxis.getOrigin() + (this.xAxis.getScalePixelWidth() * (-this.xAxis.getScaleCalculator().getMinValue())) / this.xAxis.getScaleCalculator().getIncrement());
		this.xAxis.setZeroLineCoordinate( zeroLineCoordinate );


		//---Y Axis--------------------
		//dataAxisProperties = (DataAxisProperties) this.axisProperties.getYAxisProperties();
		this.yAxis.computeScalePixelWidthDataAxis( this.axisProperties.getYAxisProperties() );
		this.yAxis.computeOneUnitPixelSize( this.yAxis.getScalePixelWidth(), this.yAxis.getScaleCalculator().getIncrement() );

		//---we SUBTRACT to the origin position when doing y-axis
		zeroLineCoordinate = (float) (this.yAxis.getOrigin() - (this.yAxis.getScalePixelWidth() * (-this.yAxis.getScaleCalculator().getMinValue())) / this.yAxis.getScaleCalculator().getIncrement());
		this.yAxis.setZeroLineCoordinate( zeroLineCoordinate );

		this.xAxis.computeTickStart();
	}


	/********************************************************************************************
	 * Draws the charts over the axis.  We have to render in a specific order so combo charts
	 * 	get drawn correctly
	 *
	 * @throws PropertyException
	 ******************************************************************************************/
	protected void overlayCharts() throws PropertyException
	{
		IAxisPlotDataSet iAxisPlotDataSet = super.getIAxisDataSeries().getIAxisPlotDataSet( ChartType.SCATTER_PLOT );
		ScatterPlotChart.render( this, (IScatterPlotDataSet) iAxisPlotDataSet );
	}


	/**********************************************************************************************
	 * Enables the testing routines to display the contents of this Object. Override Chart
	 *  implementation as PieCharts use AreaProperties directly rather than a child.
	 *
	 * @param htmlGenerator
	 * @param imageFileName
	 * @param imageMap if this is NULL we are not creating image map data in html
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator, String imageFileName, ImageMap imageMap )
	{
		htmlGenerator.chartTableStart( this.getClass().getName(), imageFileName, imageMap );

/*
		if( iDataSeries instanceof HTMLTestable )
		{
			( ( HTMLTestable ) this.iDataSeries ).toHTML( htmlGenerator );
		}
*/

		//---AxisProperties
		htmlGenerator.chartTableRowStart();
		this.axisProperties.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();


		//---XAxis
		htmlGenerator.chartTableRowStart();
		this.xAxis.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();

		//---YAxis
		htmlGenerator.chartTableRowStart();
		this.yAxis.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();


		//---ChartProperties
		htmlGenerator.chartTableRowStart();
		super.getChartProperties().toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();


		if( super.getLegend() != null )
		{
			htmlGenerator.chartTableRowStart();
			this.getLegend().toHTML( htmlGenerator );
			htmlGenerator.chartTableRowEnd();
		}

		htmlGenerator.chartTableEnd();
	}

}
