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


import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.axisChart.axis.Axis;
import org.krysalis.jcharts.axisChart.axis.XAxis;
import org.krysalis.jcharts.axisChart.axis.YAxis;
import org.krysalis.jcharts.axisChart.axis.scale.AutomaticScaleCalculator;
import org.krysalis.jcharts.axisChart.axis.scale.ScaleCalculator;
import org.krysalis.jcharts.axisChart.axis.scale.UserDefinedScaleCalculator;
import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.chartData.interfaces.IAxisDataSeries;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.chartData.interfaces.IDataSeries;
import org.krysalis.jcharts.chartData.interfaces.IStockChartDataSet;
import org.krysalis.jcharts.chartData.processors.AxisChartDataProcessor;
import org.krysalis.jcharts.chartText.NumericTagGroup;
import org.krysalis.jcharts.chartText.TextTagGroup;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.properties.AxisProperties;
import org.krysalis.jcharts.properties.AxisTypeProperties;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.properties.LegendAreaProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.test.HTMLChartTestable;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.geom.Rectangle2D;

// Dual Y axis changes integrated CMC 25Aug03
//import java.lang.Math.*;
//import java.lang.Float.*;



/*************************************************************************************
 * This Class is used to create all axis chart types.  This class knows how to render
 * 	charts based on the ChartType specified in on the iAxisChartDataSet.
 *
 * @author Nathaniel Auvil
 * @version $Id: AxisChart.java,v 1.7 2004/05/31 16:23:46 nathaniel_auvil Exp $
 ************************************************************************************/
public class AxisChart extends Chart implements HTMLChartTestable
{
	protected XAxis xAxis;
	protected YAxis yAxis;
	protected AxisProperties axisProperties;

	private IAxisDataSeries iAxisDataSeries;


	/**************************************************************************************************
	 * Constructor
	 *
	 * @param iAxisDataSeries
	 * @param chartProperties
	 * @param axisProperties
	 * @param legendProperties if no legend is desired, pass NULL
	 * @param pixelWidth
	 * @param pixelHeight
	 ***************************************************************************************************/
	public AxisChart( IAxisDataSeries iAxisDataSeries,
							ChartProperties chartProperties,
							AxisProperties axisProperties,
							LegendProperties legendProperties,
							int pixelWidth,
							int pixelHeight )
	{
		super( legendProperties, chartProperties, pixelWidth, pixelHeight );
		this.axisProperties = axisProperties;
		this.iAxisDataSeries = iAxisDataSeries;
	}


	/*************************************************************************************************
	 *
	 * @return IAxisDataSeries
	 *************************************************************************************************/
	public IAxisDataSeries getIAxisDataSeries()
	{
		return this.iAxisDataSeries;
	}


	/********************************************************************************************
	 * ScatterPlots create a subclass of AxisChartDataProcessor so we need this method so we can
	 * 	overload it.
	 *
	 * @return AxisChartDataProcessor
	 ********************************************************************************************/
	public AxisChartDataProcessor createAxisChartDataProcessor()
	{
		return new AxisChartDataProcessor();
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
                                // Dual Y axis changes integrated CMC 25Aug03
//				s.setMaxValue( axisChartDataProcessor.getMaxValue() );
//				s.setMinValue( axisChartDataProcessor.getMinValue() );
                                if (this.axisProperties.getYAxisProperties().getMaxRightAxis()>0)
                                {
                                  s.setMaxValue( this.axisProperties.getYAxisProperties().getMaxRightAxis() );
                                }
                                else
                                {
                                  s.setMaxValue( axisChartDataProcessor.getMaxValue() );
                                }

                                                        if (this.axisProperties.getYAxisProperties().getMinRightAxis()<0)
                                {
                                  s.setMinValue( this.axisProperties.getYAxisProperties().getMinRightAxis() );
                                }
                                else
                                {
                                  s.setMinValue( axisChartDataProcessor.getMinValue() );
                                }
                        }
                        axis.setScaleCalculator( s );
		}
		else
		{
			axis.setScaleCalculator( dataAxisProperties.getScaleCalculator() );
                        // Dual Y axis changes integrated CMC 25Aug03
//			axis.getScaleCalculator().setMaxValue( axisChartDataProcessor.getMaxValue() );
//			axis.getScaleCalculator().setMinValue( axisChartDataProcessor.getMinValue() );

			if (this.axisProperties.getYAxisProperties().getMaxRightAxis()>0)
                        {
                            axis.getScaleCalculator().setMaxValue( this.axisProperties.getYAxisProperties().getMaxRightAxis() );
                        }
                        else
                        {
                            axis.getScaleCalculator().setMaxValue( axisChartDataProcessor.getMaxValue() );
                        }

			if (this.axisProperties.getYAxisProperties().getMinRightAxis()<0)
			{
                            axis.getScaleCalculator().setMinValue( this.axisProperties.getYAxisProperties().getMinRightAxis() );
                        }
                        else
                        {
                            axis.getScaleCalculator().setMinValue( axisChartDataProcessor.getMinValue() );
                        }
                }

		axis.getScaleCalculator().setRoundingPowerOfTen( dataAxisProperties.getRoundToNearest() );
		axis.getScaleCalculator().setNumberOfScaleItems( dataAxisProperties.getNumItems() );
		axis.getScaleCalculator().computeScaleValues();


//		if( dataAxisProperties.showAxisLabels() )
		{
//TODO what if they do not want to display axis labels?
//todo we still need to know how to size the axis
			NumericTagGroup numericTagGroup = new NumericTagGroup( dataAxisProperties.getScaleChartFont(),
																					 fontRenderContext,
																					 dataAxisProperties.useDollarSigns(),
																					 dataAxisProperties.usePercentSigns(),
																					 dataAxisProperties.useCommas(),
																					 dataAxisProperties.getRoundToNearest() );

			numericTagGroup.createAxisScaleLabels( axis.getScaleCalculator() );
			return numericTagGroup;
		}
/*
		else
		{
			return null;
		}
*/
	}


	/***************************************************************************************
	 *
	 *
	 * @param axisChartDataProcessor
	 * @param fontRenderContext
	 **************************************************************************************/
	protected void setupAxis( AxisChartDataProcessor axisChartDataProcessor, FontRenderContext fontRenderContext ) throws ChartDataException
	{
      IDataSeries iDataSeries= (IDataSeries) this.getIAxisDataSeries();

		if( this.axisProperties.isPlotHorizontal() )
		{
			//---X AXIS---------------------------------------------------------------------------
			DataAxisProperties dataAxisProperties = (DataAxisProperties) this.getAxisProperties().getXAxisProperties();
			this.xAxis = new XAxis( this, dataAxisProperties.getNumItems() );

			NumericTagGroup numericTagGroup= setupDataAxisProperties( this.xAxis, dataAxisProperties, axisChartDataProcessor, fontRenderContext );
			this.xAxis.setAxisLabelsGroup( numericTagGroup );


			//---Y AXIS---------------------------------------------------------------------------
			AxisTypeProperties axisTypeProperties = this.getAxisProperties().getYAxisProperties();
			this.yAxis = new YAxis( this, axisChartDataProcessor.getNumberOfElementsInADataSet() );
			if( axisTypeProperties.showAxisLabels() )
			{
				TextTagGroup textTagGroup = new TextTagGroup( axisTypeProperties.getScaleChartFont(), fontRenderContext );

				//LOOP
				for( int i = 0; i < iDataSeries.getNumberOfAxisLabels(); i++ )
				{
					if( iDataSeries.getAxisLabel( i ) == null )
					{
						throw new ChartDataException( "None of the axis labels can be NULL." );
					}
					textTagGroup.addLabel( iDataSeries.getAxisLabel( i ) );
				}

				this.yAxis.setAxisLabelsGroup( textTagGroup );
			}
		}
		else
		{
			//---X AXIS---------------------------------------------------------------------------
			AxisTypeProperties axisTypeProperties = this.getAxisProperties().getXAxisProperties();
			this.xAxis = new XAxis( this, axisChartDataProcessor.getNumberOfElementsInADataSet() );
			if( axisTypeProperties.showAxisLabels() )
			{
				TextTagGroup textTagGroup = new TextTagGroup( axisTypeProperties.getScaleChartFont(), fontRenderContext );

				//LOOP
				for( int i = 0; i < iDataSeries.getNumberOfAxisLabels(); i++ )
				{
					if( iDataSeries.getAxisLabel( i ) == null )
					{
						throw new ChartDataException( "None of the axis labels can be NULL." );
					}
					textTagGroup.addLabel( iDataSeries.getAxisLabel( i ) );
				}

				this.xAxis.setAxisLabelsGroup( textTagGroup );
			}

			//---Y AXIS---------------------------------------------------------------------------
			DataAxisProperties dataAxisProperties = ( DataAxisProperties ) this.getAxisProperties().getYAxisProperties();
			this.yAxis = new YAxis( this, dataAxisProperties.getNumItems() );
			NumericTagGroup numericTagGroup= setupDataAxisProperties( this.yAxis, dataAxisProperties, axisChartDataProcessor, fontRenderContext );
			this.yAxis.setAxisLabelsGroup( numericTagGroup );
                        // Dual Y axis changes integrated CMC 25Aug03
                        // compute the labels of the right axis if necessary
                        if ( this.axisProperties.getYAxisProperties().getSecondScaleRight()!=1 )
                        {
                            NumericTagGroup numericTagGroup2 = new NumericTagGroup( dataAxisProperties.getScaleChartFontRight(),
                                                                                                                                                                                         fontRenderContext,
                                                                                                                                                                                         dataAxisProperties.useDollarSigns(),
                                                                                                                                                                                         dataAxisProperties.usePercentSigns(),
                                                                                                                                                                                         dataAxisProperties.useCommas(),
                                                                                                                                                                                         dataAxisProperties.getRoundToNearest() );
                            int j=0;
                            while (j<this.getYAxis().getNumberOfScaleItems())
                            {
                                Float myFloat = new Float (this.yAxis.getAxisLabelsGroup().getTextTag(j).getText());
                                float temp = myFloat.floatValue();
                                // Rounding problems were causing errors
                                //Integer myInteger = new Integer ( Math.round(Math.round((float)(temp / this.axisProperties.getYAxisProperties().getSecondScaleRight()))/10)*10 );
                                //String myString = new String ("");
                                //myString = myInteger.toString();
                                String myString = new String (String.valueOf(Math.round(temp / this.axisProperties.getYAxisProperties().getSecondScaleRight())) );
                                numericTagGroup2.addLabel(myString);
                                j++;
                            }
                            this.yAxis.setAxisLabelsGroupRight(numericTagGroup2);
                        }
		}


		//---if yAxisTitle is null, do not show title
		this.yAxis.computeMinimumWidthNeeded( iDataSeries.getYAxisTitle() );
		this.xAxis.computeMinimumHeightNeeded( iDataSeries.getXAxisTitle() );
	}


	/***********************************************************************************************
	 * Finalizes the size of both Axis and sets the origin position
	 *
	 * @param xAxisWidth
	 * @param yAxisHeight
	 * @param chartTitleHeight
	 **********************************************************************************************/
	private void sizeAndPositionAxis( float xAxisWidth, float yAxisHeight, float chartTitleHeight )
	{
		//---SUBTRACT space for axis titles, labels, ticks...
                // Dual Y axis changes integrated CMC 25Aug03
                // if there is a right axis to render we subtract twice the minimum width needed
                if (this.axisProperties.getYAxisProperties().getShowRightAxis())
                    xAxisWidth -= 2*this.yAxis.getMinimumWidthNeeded();
                else
                    xAxisWidth -= this.yAxis.getMinimumWidthNeeded();

                yAxisHeight -= this.xAxis.getMinimumHeightNeeded();

		//---SET THE PIXEL LENGTH OF THE AXIS
		this.xAxis.setPixelLength( xAxisWidth );

		if( axisProperties.getYAxisProperties().showAxisLabels() )
		{
			this.yAxis.setPixelLength( yAxisHeight - this.yAxis.getAxisLabelsGroup().getTallestLabel() / 2 );
		}
		else
		{
			this.yAxis.setPixelLength( yAxisHeight );
		}

		if( this.getLegend() != null )
		{
			//---SET THE ORIGIN COORDINATES
			if( ( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.RIGHT )
				|| ( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.BOTTOM ) )
			{
				this.xAxis.setOrigin( this.yAxis.getMinimumWidthNeeded() + super.getChartProperties().getEdgePadding() );
				this.yAxis.setOrigin( yAxisHeight + super.getChartProperties().getEdgePadding() + chartTitleHeight );
			}
			else if( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.LEFT )
			//---else, LegendAreaProperties.LEFT, OR LegendAreaProperties.TOP
			{
				this.xAxis.setOrigin( super.getImageWidth() - xAxisWidth - super.getChartProperties().getEdgePadding() );
				this.yAxis.setOrigin( yAxisHeight + super.getChartProperties().getEdgePadding() + chartTitleHeight );
			}
			else if( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.TOP )
			{
				this.xAxis.setOrigin( this.yAxis.getMinimumWidthNeeded() + super.getChartProperties().getEdgePadding() );
				this.yAxis.setOrigin( super.getImageHeight() - super.getChartProperties().getEdgePadding() - this.xAxis.getMinimumHeightNeeded() );
			}
		}
		else
		{
         this.xAxis.setOrigin( this.yAxis.getMinimumWidthNeeded() + super.getChartProperties().getEdgePadding() );
			this.yAxis.setOrigin( yAxisHeight + super.getChartProperties().getEdgePadding() + chartTitleHeight );
		}
	}


	/******************************************************************************************
	 *
	 *****************************************************************************************/
	protected void deriveAxisValues()
	{
		this.xAxis.computeLabelFilter();
		this.xAxis.computeShouldTickStartAtYAxis( this.iAxisDataSeries, this.axisProperties.getXAxisProperties() );

		if( this.axisProperties.isPlotHorizontal() )
		{
			//DataAxisProperties dataAxisProperties = (DataAxisProperties) this.axisProperties.getXAxisProperties();
			//LabelAxisProperties labelAxisProperties= (LabelAxisProperties) this.axisProperties.getYAxisProperties();

			//---Determine how many labels will fit on the x-axis

			this.xAxis.computeScalePixelWidthDataAxis( this.axisProperties.getXAxisProperties() );

			this.yAxis.computeScalePixelWidth( this.axisProperties.getYAxisProperties() );
			this.xAxis.computeOneUnitPixelSize( this.xAxis.getScalePixelWidth(), this.xAxis.getScaleCalculator().getIncrement() );


			//---we ADD to the origin position when doing x-axis
			float zeroLineCoordinate = ( float ) ( this.xAxis.getOrigin() + ( this.xAxis.getScalePixelWidth() * ( -this.xAxis.getScaleCalculator().getMinValue() ) ) / this.xAxis.getScaleCalculator().getIncrement() );
			this.xAxis.setZeroLineCoordinate( zeroLineCoordinate );
		}
		else
		{
			//DataAxisProperties dataAxisProperties = ( DataAxisProperties ) this.axisProperties.getYAxisProperties();

			this.xAxis.computeScalePixelWidth( this.axisProperties.getXAxisProperties() );
			this.yAxis.computeScalePixelWidthDataAxis( this.axisProperties.getYAxisProperties() );
         this.yAxis.computeOneUnitPixelSize( this.yAxis.getScalePixelWidth(), this.yAxis.getScaleCalculator().getIncrement() );

			//---we SUBTRACT to the origin position when doing y-axis
			float zeroLineCoordinate = ( float ) ( this.yAxis.getOrigin() - ( this.yAxis.getScalePixelWidth() * ( -this.yAxis.getScaleCalculator().getMinValue() ) ) / this.yAxis.getScaleCalculator().getIncrement() );
			this.yAxis.setZeroLineCoordinate( zeroLineCoordinate );
		}

		this.xAxis.computeTickStart();
	}


	/********************************************************************************************
	 * Implement the method to render the Axis based chart
	 *
	 * @throws ChartDataException
	 * @throws PropertyException there are several validations done to aid in development of the
	 * 	charts.
	 ********************************************************************************************/
	protected void renderChart() throws ChartDataException, PropertyException
	{
		if( super.getChartProperties().validate() )
		{
			this.iAxisDataSeries.validate();
		}

		//---this is not an optional validation
		this.validateHorizontalPlot();


		Graphics2D g2d = super.getGraphics2D();


		FontRenderContext fontRenderContext = g2d.getFontRenderContext();


		//---cache calcs used more than once
		float edgePaddingTimesTwo = super.getChartProperties().getEdgePadding() * 2;


		//---start off with total image width and we will subtract components from that.
		float xAxisWidth = super.getImageWidth() - edgePaddingTimesTwo;
		float yAxisHeight = super.getImageHeight() - edgePaddingTimesTwo;


		//---render the TITLE. If no title, this will return zero.
		float chartTitleHeight = super.renderChartTitle( this.getIAxisDataSeries().getChartTitle(), fontRenderContext );
		yAxisHeight -= chartTitleHeight;


		//---if there is a legend...
		if( super.getLegend() != null )
		{
			//---PROCESS the size needed for drawing the legend.
			super.getLegend().computeLegendXY( this.iAxisDataSeries, chartTitleHeight );

			if( ( super.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.RIGHT )
				|| ( super.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.LEFT ) )
			{
				xAxisWidth -= super.getLegend().getLegendProperties().getChartPadding();
				xAxisWidth -= super.getLegend().getWidth();
			}
			else //LegendAreaProperties.BOTTOM, OR LegendAreaProperties.TOP
			{
				yAxisHeight -= this.getLegend().getLegendProperties().getChartPadding();
				yAxisHeight -= this.getLegend().getHeight();
			}

			super.getLegend().render();
		}


		AxisChartDataProcessor axisChartDataProcessor = this.createAxisChartDataProcessor();
		axisChartDataProcessor.processData( this, fontRenderContext );


		this.setupAxis( axisChartDataProcessor, fontRenderContext );
		this.sizeAndPositionAxis( xAxisWidth, yAxisHeight, chartTitleHeight );
		this.deriveAxisValues();



		//---PAINT THE BACKGROUND OF AXIS
		if( this.getAxisProperties().getBackgroundPaint() != null )
		{
			Rectangle2D.Float rectangle = new Rectangle2D.Float( this.xAxis.getOrigin() + 1,
																				  this.yAxis.getOrigin() - this.yAxis.getPixelLength(),
																				  this.xAxis.getPixelLength(),
																				  this.yAxis.getPixelLength() );
			g2d.setPaint( this.axisProperties.getBackgroundPaint() );
			g2d.fill( rectangle );
		}


		this.yAxis.render( g2d, this.getAxisProperties(), iAxisDataSeries.getYAxisTitle() );
		this.xAxis.render( g2d, this.getAxisProperties(), iAxisDataSeries.getXAxisTitle() );


		//---SCALE CLIPPING REGION
		//---if the user defined the scale, chart may be off the 'screen' so set a clipping region so only draw in the chart.
		Rectangle2D.Float rectangle = new Rectangle2D.Float( this.getXAxis().getOrigin(),
																				  this.getYAxis().getOrigin() - this.getYAxis().getPixelLength() + 1,
																				  this.xAxis.getPixelLength() + 1,
																				  this.yAxis.getPixelLength() - 2 );
		g2d.setClip( rectangle );


		//---IMAGE MAP setup
		//---if we are saving all the coordinates for an ImageMap, create the ImageMap Object as we
		//---	know how many area elements there are.
		if( super.getGenerateImageMapFlag() )
		{
			//---pass the size to try and avoid having the expense of resizing the ArrayList
			ImageMap imageMap = new ImageMap( iAxisDataSeries.size() * iAxisDataSeries.getTotalNumberOfDataSets() );
			super.setImageMap( imageMap );
		}


		//---draw the charts over the axis...
		overlayCharts();
	}


	/********************************************************************************************
	 * Draws the charts over the axis.  We have to render in a specific order so combo charts
	 * 	get drawn correctly
	 *
	 * @throws PropertyException
	 ******************************************************************************************/
	protected void overlayCharts() throws PropertyException, ChartDataException
	{
		IAxisPlotDataSet iAxisPlotDataSet;

		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.AREA_STACKED );
		if( iAxisPlotDataSet != null )
		{
			StackedAreaChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}
		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.AREA );
		if( iAxisPlotDataSet != null )
		{
			AreaChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}


		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR );
		if( iAxisPlotDataSet != null )
		{
			BarChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}
		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR_STACKED );
		if( iAxisPlotDataSet != null )
		{
			StackedBarChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}
		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR_CLUSTERED );
		if( iAxisPlotDataSet != null )
		{
			ClusteredBarChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}


		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.STOCK );
		if( iAxisPlotDataSet != null )
		{
			StockChart.render( this, ( IStockChartDataSet ) iAxisPlotDataSet );
		}


		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.LINE );
		if( iAxisPlotDataSet != null )
		{
			LineChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}

		iAxisPlotDataSet = this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.POINT );
		if( iAxisPlotDataSet != null )
		{
			PointChart.render( this, ( IAxisChartDataSet ) iAxisPlotDataSet );
		}
	}


	/**********************************************************************************************
	 * Currently, we only support the bar chart types being horizontal, and you can not have a
	 * 	horizontally plotted bar chart in a combo chart.
	 *
	 * @throws PropertyException
	 **********************************************************************************************/
	private void validateHorizontalPlot() throws PropertyException
	{
		if( axisProperties.isPlotHorizontal() )
		{
			//---if there is only one data set, there is no need to do any validations.
			if( this.iAxisDataSeries.size() > 1 )
			{
				throw new PropertyException( "You can not have a combo chart on a horizontal plot." );
			}

			if( !this.allowHorizontalPlot() )
			{
				throw new PropertyException( "Horizontal plots are only supported in the Bar, Stacked Bar, and Clustered Bar Chart Types." );
			}
		}
	}


	/******************************************************************************************
	 * We only allow horizontal plots for the Bar Chart types in this release.
	 *
	 * @return boolean
	 ******************************************************************************************/
	private boolean allowHorizontalPlot()
	{
		if( this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR ) != null )
		{
			return true;
		}

		if( this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR_STACKED ) != null )
		{
			return true;
		}

		if( this.iAxisDataSeries.getIAxisPlotDataSet( ChartType.BAR_CLUSTERED ) != null )
		{
			return true;
		}

		return false;
	}


	/**************************************************************************************************
	 *
	 ***************************************************************************************************/
	public AxisProperties getAxisProperties()
	{
		return this.axisProperties;
	}


	/**************************************************************************************************
	 *
	 ***************************************************************************************************/
	public XAxis getXAxis()
	{
		return this.xAxis;
	}


	/**************************************************************************************************
	 *
	 ***************************************************************************************************/
	public YAxis getYAxis()
	{
		return this.yAxis;
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

		if( this.iAxisDataSeries instanceof HTMLTestable )
		{
			( ( HTMLTestable ) this.iAxisDataSeries ).toHTML( htmlGenerator );
		}

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
