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


package org.krysalis.jcharts.axisChart.axis;


import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.chartData.interfaces.IAxisDataSeries;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Line2D;
import java.lang.reflect.Field;
import java.util.Iterator;


/*************************************************************************************
 *
 * @author Nathaniel Auvil, John Thomson
 * @version $Id: XAxis.java,v 1.3 2003/06/29 14:14:28 nathaniel_auvil Exp $
 ************************************************************************************/
public class XAxis extends Axis implements HTMLTestable
{
	//---indicates which labels to display 1=every, 2=every other, 3=every third, etc...
	private int xLabelFilter = 1;

	//---for some charts such as line, point, area, etc... we want to start plot at y-axis
	private boolean startTicksAtAxis;


	/**************************************************************************************************
	 *
	 * @param axisChart
	 * @param numberOfScaleItems
	 ***************************************************************************************************/
	public XAxis( AxisChart axisChart, int numberOfScaleItems )
	{
		super( axisChart, numberOfScaleItems );
	}


	/*************************************************************************************************
	 * Computes the minimum pixel height required for the X-Axis.
	 *  Includes space, if needed, for: axis title + padding, axis values + tick padding, and tick marks.
	 *
	 * @param axisTitle
	 **************************************************************************************************/
	public void computeMinimumHeightNeeded( String axisTitle )
	{
		float heightNeeded = 0;
		AxisProperties axisProperties = super.getAxisChart().getAxisProperties();
		AxisTypeProperties axisTypeProperties = axisProperties.getXAxisProperties();


		if( axisTypeProperties.showAxisLabels() )
		{
			if( axisProperties.xAxisLabelsAreVertical() )
			{
				//---widest label for vertical labels
				//heightNeeded = axisChartDataProcessor.getAxisLabelProcessor().getWidestLabel();
				heightNeeded = super.getAxisLabelsGroup().getWidestLabel();
			}
			else
			{
				//---tallest label for horizontal labels
				//heightNeeded = axisChartDataProcessor.getAxisLabelProcessor().getTallestLabel();
				heightNeeded = super.getAxisLabelsGroup().getTallestLabel();

				//---not sure why i need more padding
				heightNeeded += 3;
			}
		}


		if( axisTypeProperties.getShowTicks() != AxisTypeProperties.TICKS_NONE )
		{
			if( axisTypeProperties.showAxisLabels() )
			{
				//---add the padding between scale labels and tick marks
				heightNeeded += axisTypeProperties.getPaddingBetweenLabelsAndTicks();
			}

			//---add width of tick marks
			heightNeeded += axisTypeProperties.getAxisTickMarkPixelLength();
		}
		else
		{
			//---use specified distance between labels and axis
			heightNeeded += axisTypeProperties.getPaddingBetweenAxisAndLabels();
		}


		//---include axis title height if needed. Remember it is vertical for y-axis
		if( axisTitle != null )
		{
			super.computeAxisTitleDimensions( axisTitle, axisTypeProperties.getTitleChartFont() );
			heightNeeded += super.getTitleHeight();
			heightNeeded += axisTypeProperties.getPaddingBetweenAxisTitleAndLabels();
		}

		super.setMinimumHeightNeeded( heightNeeded );
	}


	/*************************************************************************************************
	 * Computes the number of pixels between each value on the axis.
	 *
	 **************************************************************************************************
	 public void computeScalePixelWidth( int numberOfValuesOnXAxis )
	 {
	 super.setScalePixelWidth( super.getPixelLength() / numberOfValuesOnXAxis );
	 }


	 /****************************************************************************************************
	 *
	 * @param axisTitle
	 * @param graphics2D
	 * @param axisTypeProperties
	 ***************************************************************************************************/
	private void renderAxisTitle( String axisTitle, Graphics2D graphics2D, AxisTypeProperties axisTypeProperties )
	{
		if( axisTitle != null )
		{
			float titleX;
			float titleY = super.getAxisChart().getYAxis().getOrigin() + this.getMinimumHeightNeeded() - super.getTitleHeight();

			//---if title is larger than the axis itself, place at top.
			if( super.getTitleWidth() > super.getPixelLength() )
			{
				titleX = ((super.getAxisChart().getImageWidth() - super.getTitleWidth()) / 2);
			}
			//---else, center on XAxis.
			else
			{
				titleX = super.getOrigin() + ((super.getPixelLength() - super.getTitleWidth()) / 2);
			}


			axisTypeProperties.getAxisTitleChartFont().setupGraphics2D( graphics2D );
			graphics2D.drawString( axisTitle, titleX, titleY );
		}
	}


	/***************************************************************************************
	 * Determines if we should start x-axis ticks at the y-axis or space it out a half
	 * 	a scale item width.
	 *
	 * @param iAxisDataSeries
	 * @param axisTypeProperties
	 **************************************************************************************/
	public void computeShouldTickStartAtYAxis( IAxisDataSeries iAxisDataSeries,
															 AxisTypeProperties axisTypeProperties )
	{
		//---if horizontal plot, x-axis is the data axis, so always start data points at y-axis
		if( axisTypeProperties instanceof DataAxisProperties )
		{
			this.startTicksAtAxis= true;
		}
		else
		{
			this.startTicksAtAxis= true;

			//---else, there are a couple of plots we do not start x-axis values at the y-axis
			IAxisPlotDataSet iAxisPlotDataSet;
			Iterator iterator= iAxisDataSeries.getIAxisPlotDataSetIterator();
//todo what about combo charts?
			while( iterator.hasNext() )
			{
				iAxisPlotDataSet= (IAxisPlotDataSet) iterator.next();
				if( iAxisPlotDataSet.getChartType().equals( ChartType.BAR )
					|| iAxisPlotDataSet.getChartType().equals( ChartType.BAR_CLUSTERED )
					|| iAxisPlotDataSet.getChartType().equals( ChartType.BAR_STACKED )
					|| iAxisPlotDataSet.getChartType().equals( ChartType.LINE )
					|| iAxisPlotDataSet.getChartType().equals( ChartType.POINT )
					|| iAxisPlotDataSet.getChartType().equals( ChartType.STOCK ) )
				{
					this.startTicksAtAxis= false;
					break;
				}
			}
		}
	}


	/***************************************************************************************
	 * Computes the screen pixel location of the first tick mark
	 *
	 **************************************************************************************/
	public void computeTickStart()
	{
		float tickStart= super.getOrigin();

		if( ! this.startTicksAtAxis )
		{
			tickStart+= (super.getScalePixelWidth() / 2);
		}

      super.setTickStart( tickStart );
	}


	/*************************************************************************************************
	 * Computes the number of pixels between each value on the axis.
	 *
	 * @param axisTypeProperties
	 *************************************************************************************************/
	public void computeScalePixelWidth( AxisTypeProperties axisTypeProperties )
	{
		if( this.startTicksAtAxis )
		{
			super.computeScalePixelWidthDataAxis( axisTypeProperties );
		}
		else
		{
			super.setScalePixelWidth( getPixelLength() / this.getNumberOfScaleItems() );
		}
	}


	/*********************************************************************************************
	 * Renders the YAxis on the passes Graphics2D object
	 *
	 * @param graphics2D
	 * @param axisProperties
	 * @param axisTitle
	 **********************************************************************************************/
	public void render( Graphics2D graphics2D,
							  AxisProperties axisProperties,
							  String axisTitle )
	{
		AxisTypeProperties axisTypeProperties = axisProperties.getXAxisProperties();


		//---AXIS TITLE
		this.renderAxisTitle( axisTitle, graphics2D, axisTypeProperties );

		Line2D.Float line2D = new Line2D.Float( super.getTickStart(), 0.0f, super.getTickStart(), 0.0f );
		float tickY1 = super.getAxisChart().getYAxis().getOrigin();
		float tickY2 = super.getAxisChart().getYAxis().getOrigin() + axisTypeProperties.getAxisTickMarkPixelLength();
		float gridLineY1 = super.getAxisChart().getYAxis().getOrigin();
		float gridLineY2 = super.getAxisChart().getYAxis().getOrigin() - super.getAxisChart().getYAxis().getPixelLength();


		float stringX = super.getTickStart();
		float stringY = super.getAxisChart().getYAxis().getOrigin();
		if( axisTypeProperties.getShowTicks() != AxisTypeProperties.TICKS_NONE )
		{
			stringY += axisTypeProperties.getAxisTickMarkPixelLength() + axisTypeProperties.getPaddingBetweenLabelsAndTicks();
		}
		else
		{
			stringY += axisTypeProperties.getPaddingBetweenAxisAndLabels();
		}


		if( axisTypeProperties.showAxisLabels() )
		{
			//---if the scale labels are horizontal, simply add the tallest label height.
			//---Otherwise we will have to calculate it when we draw the label
			if( !axisProperties.xAxisLabelsAreVertical() )
			{
				stringY += super.getAxisLabelsGroup().getTallestLabel();
				graphics2D.setFont( axisTypeProperties.getScaleChartFont().getFont() );
			}
			else
			{
				stringX -= super.getAxisLabelsGroup().getTextTag( 0 ).getFontDescent();
				graphics2D.setFont( axisTypeProperties.getScaleChartFont().deriveFont() );
			}
		}


		//LOOP
		//for( int i = 0; i < super.getAxisLabelsGroup().size(); i++ )
		for( int i = 0; i < super.getNumberOfScaleItems(); i++ )
		{
			//---GRID LINES
			if( axisTypeProperties.getShowGridLines() != AxisTypeProperties.GRID_LINES_NONE )
			{
				if( ( i == 0 && !( axisTypeProperties instanceof DataAxisProperties ) )
					|| ( i > 0 && ( (axisTypeProperties.getShowGridLines() == AxisTypeProperties.GRID_LINES_ALL)	|| (axisTypeProperties.getShowGridLines() == AxisTypeProperties.GRID_LINES_ONLY_WITH_LABELS && (i % this.xLabelFilter == 0)) ) ) )
				{
					line2D.y1 = gridLineY1;
					line2D.y2 = gridLineY2;

					if( i < super.getAxisLabelsGroup().size()
						|| (i == super.getAxisLabelsGroup().size() && !axisTypeProperties.getShowEndBorder()) )
					{
						axisTypeProperties.getGridLineChartStroke().draw( graphics2D, line2D );
					}
				}
			}

			//---TICK MARKS
			//if( i != super.getAxisLabelsGroup().size() )
			if( i != super.getNumberOfScaleItems() )
			{
				if( (axisTypeProperties.getShowTicks() == AxisTypeProperties.TICKS_ALL)
					|| (axisTypeProperties.getShowTicks() == AxisTypeProperties.TICKS_ONLY_WITH_LABELS
					&& (i % this.xLabelFilter == 0)) )
				{
					line2D.y1 = tickY1;
					line2D.y2 = tickY2;

					axisTypeProperties.getTickChartStroke().setupGraphics2D( graphics2D );
					graphics2D.draw( line2D );
				}
			}

			line2D.x1 += super.getScalePixelWidth();
			line2D.x2 = line2D.x1;


			//---AXIS LABEL
			if( axisTypeProperties.showAxisLabels() && (i % this.xLabelFilter == 0) )
			{
				graphics2D.setPaint( axisTypeProperties.getScaleChartFont().getPaint() );

				if( !axisProperties.xAxisLabelsAreVertical() )
				{
					//graphics2D.drawString( iDataSeries.getXAxisLabel( i ), stringX - super.getAxisLabelsGroup().getTextTag( i ).getWidth() / 2, stringY );
					float x = stringX - super.getAxisLabelsGroup().getTextTag( i ).getWidth() / 2;

					//---we can not only look at the last label as there could be a filter and labels near the last might go off the edge of the screen.
					if( x + super.getAxisLabelsGroup().getTextTag( i ).getWidth() < super.getAxisChart().getImageWidth() )
					{
						super.getAxisLabelsGroup().getTextTag( i ).render( graphics2D, x, stringY );
					}
				}
				else
				{
					float x = stringX + super.getAxisLabelsGroup().getTextTag( i ).getHeight() / 2;

					//---we can not only look at the last label as there could be a filter and labels near the last might go off the edge of the screen.
					if( x + super.getAxisLabelsGroup().getTextTag( i ).getHeight() < super.getAxisChart().getImageWidth() )
					{
						graphics2D.drawString( super.getAxisLabelsGroup().getTextTag( i ).getText(), x, stringY + super.getAxisLabelsGroup().getTextTag( i ).getWidth() );
					}
				}
			}
			stringX += super.getScalePixelWidth();
		}


		//---RIGHT BORDER-----------------------------------------------------------
		if( axisTypeProperties.getShowEndBorder() )
		{
			//---make sure no rounding errors
			line2D.x1 = super.getOrigin() + super.getPixelLength();
			line2D.x2 = line2D.x1;
			line2D.y1 = gridLineY1;
			line2D.y2 = gridLineY2;
			axisProperties.getYAxisProperties().getAxisStroke().draw( graphics2D, line2D );
		}


		//---AXIS-------------------------------------------------------------------
		line2D.x1 = super.getOrigin();
		line2D.x2 = super.getOrigin() + super.getPixelLength();
		line2D.y1 = super.getAxisChart().getYAxis().getOrigin();
		line2D.y2 = line2D.y1;
		axisTypeProperties.getAxisStroke().setupGraphics2D( graphics2D );
		graphics2D.draw( line2D );


		//---ZERO LINE-----------------------------------------------------------------
		if( axisTypeProperties instanceof DataAxisProperties )
		{
			DataAxisProperties dataAxisProperties = (DataAxisProperties) axisTypeProperties;

			if( dataAxisProperties.showZeroLine()
				&& super.getScaleCalculator().getMinValue() < 0.0d
				&& super.getScaleCalculator().getMaxValue() > 0.0d )
			{
				line2D.x1 = super.getZeroLineCoordinate();
				line2D.x2 = line2D.x1;
				line2D.y1 = super.getAxisChart().getYAxis().getOrigin();
				line2D.y2 = super.getAxisChart().getYAxis().getOrigin() - super.getAxisChart().getYAxis().getPixelLength();
				dataAxisProperties.getZeroLineChartStroke().draw( graphics2D, line2D );
			}
		}
	}


	/************************************************************************************************
	 * Method to compute the filter to use on the x-axis label display so labels do not overlap
	 *
	 *************************************************************************************************/
	public void computeLabelFilter()
	{
		if( super.getAxisChart().getAxisProperties().getXAxisProperties().showAxisLabels() )
		{
			float widestLabelSize;
			AxisTypeProperties axisTypeProperties = super.getAxisChart().getAxisProperties().getXAxisProperties();

			if( super.getAxisChart().getAxisProperties().xAxisLabelsAreVertical() )
			{
				widestLabelSize = super.getAxisLabelsGroup().getTallestLabel();
			}
			else
			{
				widestLabelSize = super.getAxisLabelsGroup().getWidestLabel();
			}

			double numberLabelsCanDisplay = this.getPixelLength() / (widestLabelSize + axisTypeProperties.getPaddingBetweenAxisLabels());
			this.xLabelFilter = (int) Math.ceil( super.getAxisLabelsGroup().size() / numberLabelsCanDisplay );
		}
		else
		{
			this.xLabelFilter= 1;
		}
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( this.getClass().getName() );

		super.toHTML( htmlGenerator );

		Field[] fields = this.getClass().getDeclaredFields();
		for( int i = 0; i < fields.length; i++ )
		{
			try
			{
				htmlGenerator.addField( fields[ i ].getName(), fields[ i ].get( this ) );
			}
			catch( IllegalAccessException illegalAccessException )
			{
				illegalAccessException.printStackTrace();
			}
		}

		htmlGenerator.propertiesTableEnd();
	}


	/*************************************************************************************************
	 * Takes a value and determines the screen coordinate it should be drawn at. THe only difference
	 * 	between this and the y-axis is we add to the origin versus subtract from it.
	 *
	 * @param origin
	 * @param value
	 * @param axisMinValue the minimum value on the axis
	 * @return float the screen pixel coordinate
	 **************************************************************************************************/
	public float computeAxisCoordinate( float origin, double value, double axisMinValue )
	{
		double returnValue = origin + (value - axisMinValue) * this.getOneUnitPixelSize();
//System.out.println( "computeAxisCoordinate( " + origin + ", " + value + ", " + axisMinValue + " ) = " + returnValue );
		return (float) returnValue;
	}

}
