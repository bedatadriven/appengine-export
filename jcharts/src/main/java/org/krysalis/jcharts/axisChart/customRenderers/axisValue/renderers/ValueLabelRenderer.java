/***********************************************************************************************
 * File Info: $Id: ValueLabelRenderer.java,v 1.3 2004/05/31 16:23:03 nathaniel_auvil Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil, John Thomsen
 * Contributor(s):
 *
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


package org.krysalis.jcharts.axisChart.customRenderers.axisValue.renderers;


import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.AxisValueRenderEvent;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.PostAxisValueRenderListener;
import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.chartText.NumericTagGroup;
import org.krysalis.jcharts.chartText.TextTag;
import org.krysalis.jcharts.properties.util.ChartFont;

import com.google.code.appengine.awt.*;
import java.text.NumberFormat;


public class ValueLabelRenderer implements PostAxisValueRenderListener
{

	private NumberFormat numberFormat;

	private ChartFont valueChartFont = ChartFont.DEFAULT_AXIS_VALUE;

	//---holds the derived Font if needed so don't have to recalculate it each time.
	private Font derivedFont;

	//---vertical labels are only used when plotting on vertical charts; not used for horizontal bar plots.
	private boolean isLabelVertical = false;

	private ValueLabelPosition valueLabelPosition = ValueLabelPosition.ON_TOP;

	private int pixelValuePadding = 4;


	/**********************************************************************************
	 *
	 * @param isCurrency
	 * @param showGrouping
	 * @param roundingPowerOfTen
	 * @deprecated use the other Constructor
	 **********************************************************************************/
	public ValueLabelRenderer( boolean isCurrency, boolean showGrouping, int roundingPowerOfTen )
	{
		this.numberFormat = NumericTagGroup.getNumberFormatInstance( isCurrency, false, showGrouping, roundingPowerOfTen );
	}


	/**********************************************************************************
	 *
	 * @param isCurrency
	 * @param isPercent
	 * @param showGrouping
	 * @param roundingPowerOfTen
	 **********************************************************************************/
	public ValueLabelRenderer( boolean isCurrency, boolean isPercent, boolean showGrouping, int roundingPowerOfTen )
	{
		this.numberFormat = NumericTagGroup.getNumberFormatInstance( isCurrency, isPercent, showGrouping, roundingPowerOfTen );
	}


	/************************************************************************************
	 * Sets where you would like to position the label
	 *
	 * @param valueLabelPosition
	 ***********************************************************************************/
	public void setValueLabelPosition( ValueLabelPosition valueLabelPosition )
	{
		this.valueLabelPosition = valueLabelPosition;
	}


	/************************************************************************************
	 *
	 * @param valueChartFont
	 ***********************************************************************************/
	public void setValueChartFont( ChartFont valueChartFont )
	{
		this.valueChartFont = valueChartFont;
	}


	/***********************************************************************************
	 *
	 * @param useVerticalLabels
	 **********************************************************************************/
	public void useVerticalLabels( boolean useVerticalLabels )
	{
		this.isLabelVertical = useVerticalLabels;

		//---set this here so can reuse same font
		if( this.isLabelVertical )
		{
			this.derivedFont = this.valueChartFont.deriveFont();
		}
	}


	/***********************************************************************************
	 * The pixel padding between the label and the data point.
	 *
	 * @param pixelValuePadding
	 **********************************************************************************/
	public void setPixelValuePadding( int pixelValuePadding )
	{
		this.pixelValuePadding = pixelValuePadding;
	}


	/***********************************************************************************
	 *
	 * @param axisValueRenderEvent
	 ***********************************************************************************/
	public void postRender( AxisValueRenderEvent axisValueRenderEvent )
	{
		AxisChart axisChart = (AxisChart) axisValueRenderEvent.getSource();
		TextTag valueTag;
		float x;
		float y;


		if( axisValueRenderEvent.getiAxisPlotDataSet() instanceof IAxisChartDataSet )
		{
			IAxisChartDataSet iAxisChartDataSet = (IAxisChartDataSet) axisValueRenderEvent.getiAxisPlotDataSet();
			double value = iAxisChartDataSet.getValue( axisValueRenderEvent.getDataSetIndex(), axisValueRenderEvent.getValueIndex() );

			valueTag = new TextTag( this.numberFormat.format( value ),
											this.valueChartFont.getFont(),
											this.derivedFont,
											axisValueRenderEvent.getFontRenderContext() );

			if( axisChart.getAxisProperties().isPlotHorizontal() )
			{
				x = this.calculateXHorizontalPlot( axisValueRenderEvent, valueTag, (value < 0) );
				y = this.calculateYHorizontalPlot( axisValueRenderEvent, valueTag );
			}
			else
			{
				x = this.calculateXVerticalPlot( axisValueRenderEvent, valueTag );
				y = this.calculateYVerticalPlot( axisValueRenderEvent, valueTag, (value < 0) );
			}
		}
		else
		{
//todo scatter and hi/low
			valueTag = null;
			x = 100;
			y = 100;

			throw new RuntimeException( "Axis Values not yet implemented for this type of chart." );
		}


/*
		Line2D.Float line= new Line2D.Float( x, y, x, y -20 );
		axisValueRenderEvent.getGraphics2D().draw( line );
*/

		valueTag.setXPosition( x );
		valueTag.setYPosition( y );
		valueTag.render( axisValueRenderEvent.getGraphics2D(), this.valueChartFont.getPaint() );
	}


	/*************************************************************************************************
	 * Calculates the label x so that the label is centered on the scale item.
	 *
	 * @param axisValueRenderEvent
	 * @param formattedTextTag
	 * @return float
	 ************************************************************************************************/
	private float calculateXVerticalPlot( AxisValueRenderEvent axisValueRenderEvent, TextTag formattedTextTag )
	{
		float x = axisValueRenderEvent.getValueX();

		if( this.isLabelVertical )
		{
			x += formattedTextTag.getFontDescent();
		}
		else
		{
			x -= (formattedTextTag.getWidth() / 2);
		}

		return x;
	}


	/*************************************************************************************************
	 *
	 * @param axisValueRenderEvent
	 * @param formattedTextTag
	 * @return float
	 ************************************************************************************************/
	private float calculateYHorizontalPlot( AxisValueRenderEvent axisValueRenderEvent,
														 TextTag formattedTextTag )
	{
		float y = axisValueRenderEvent.getValueY();

		if( this.isLabelVertical )
		{
			y += (formattedTextTag.getWidth() / 2);
		}
		else
		{
			y += formattedTextTag.getFontDescent();
		}

		return y;
	}


	/*************************************************************************************************
	 *
	 * @param axisValueRenderEvent
	 * @param formattedTextTag
	 * @param isNegative
	 ************************************************************************************************/
	private float calculateXHorizontalPlot( AxisValueRenderEvent axisValueRenderEvent,
														 TextTag formattedTextTag,
														 boolean isNegative )
	{
		float x = axisValueRenderEvent.getValueX();

		if( this.valueLabelPosition.equals( ValueLabelPosition.ON_TOP ) )
		{
			//---if the value is negative, 'top' is to the left
			if( isNegative )
			{
				x -= (this.isLabelVertical) ? 0 : formattedTextTag.getWidth();
				x -= this.pixelValuePadding;
			}
			else
			{
				x += (this.isLabelVertical) ? formattedTextTag.getFontAscent() : 0;
				x += this.pixelValuePadding;
			}
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.AT_TOP ) )
		{
			if( isNegative )
			{
				x += (this.isLabelVertical) ? formattedTextTag.getFontAscent() : 0;
				x += this.pixelValuePadding;
			}
			else
			{
				x -= (this.isLabelVertical) ? formattedTextTag.getFontDescent() : formattedTextTag.getWidth();
				x -= this.pixelValuePadding;
			}
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.ABOVE_ZERO_LINE ) )
		{
			x = axisValueRenderEvent.getZeroLineCoordinate();

			if( isNegative )
			{
				x += (this.isLabelVertical) ? formattedTextTag.getFontAscent() : 0;
				x += this.pixelValuePadding;
			}
			else
			{
				x -= (this.isLabelVertical) ? formattedTextTag.getFontDescent() : formattedTextTag.getWidth();
				x -= this.pixelValuePadding;
			}
		}


		else if( this.valueLabelPosition.equals( ValueLabelPosition.AXIS_TOP ) )
		{
			x = axisValueRenderEvent.getTotalItemAxisArea().x + axisValueRenderEvent.getTotalItemAxisArea().width;

			x -= (this.isLabelVertical) ? 0 : formattedTextTag.getWidth();
			x -= this.pixelValuePadding;
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.AXIS_BOTTOM ) )
		{
			x = axisValueRenderEvent.getTotalItemAxisArea().x;
			x += (this.isLabelVertical) ? formattedTextTag.getFontAscent() : 0;
			x += this.pixelValuePadding;
		}


		//VALIDATION - force labels into plot area, in case there is a user defined scale.
//todo could we skip this validation for non-user defined scales?
		//---if label goes off the right edge, force it to stay in the plot area.
		if( (x + formattedTextTag.getWidth())
			> (axisValueRenderEvent.getTotalItemAxisArea().x + axisValueRenderEvent.getTotalItemAxisArea().width) )
		{
			x = axisValueRenderEvent.getTotalItemAxisArea().x + axisValueRenderEvent.getTotalItemAxisArea().width;
			x -= formattedTextTag.getWidth();
			x -= this.pixelValuePadding;
		}
		//---if label goes off left edge, force to the right
		else if( x < axisValueRenderEvent.getTotalItemAxisArea().x )
		{
			x = axisValueRenderEvent.getTotalItemAxisArea().x;
			x += this.pixelValuePadding;
		}

		return x;
	}


	/*************************************************************************************************
	 *
	 * @param axisValueRenderEvent
	 * @param formattedTextTag
	 * @param isNegative
	 ************************************************************************************************/
	private float calculateYVerticalPlot( AxisValueRenderEvent axisValueRenderEvent,
													  TextTag formattedTextTag,
													  boolean isNegative )
	{
		float y = axisValueRenderEvent.getValueY();

		if( this.valueLabelPosition.equals( ValueLabelPosition.ON_TOP ) )
		{
			//---if the value is negative, 'top' is to the bottom
			if( isNegative )
			{
				y += (this.isLabelVertical) ? formattedTextTag.getWidth() : formattedTextTag.getHeight();
				y += this.pixelValuePadding;
			}
			else
			{
				y -= this.pixelValuePadding;
			}
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.AT_TOP ) )
		{
			//---if the value is negative, 'top' is to the bottom
			if( isNegative )
			{
				y -= this.pixelValuePadding;
			}
			else
			{
				y += (this.isLabelVertical) ? formattedTextTag.getWidth() : formattedTextTag.getHeight();
				y += this.pixelValuePadding;
			}
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.ABOVE_ZERO_LINE ) )
		{
			y = axisValueRenderEvent.getZeroLineCoordinate();

			//---if the value is negative, 'top' is to the bottom
			if( isNegative )
			{
				y -= this.pixelValuePadding;
			}
			else
			{
				y += (this.isLabelVertical) ? formattedTextTag.getWidth() : formattedTextTag.getHeight();
				y += this.pixelValuePadding;
			}
		}

		else if( this.valueLabelPosition.equals( ValueLabelPosition.AXIS_TOP ) )
		{
			y = axisValueRenderEvent.getTotalItemAxisArea().y;
			y += (this.isLabelVertical) ? formattedTextTag.getWidth() : formattedTextTag.getHeight();
			y += this.pixelValuePadding;
		}
		else if( this.valueLabelPosition.equals( ValueLabelPosition.AXIS_BOTTOM ) )
		{
			y = axisValueRenderEvent.getTotalItemAxisArea().y + axisValueRenderEvent.getTotalItemAxisArea().height;
			y -= this.pixelValuePadding;
		}



		//VALIDATION - force labels into plot area, in case there is a user defined scale.
		if( isLabelVertical )
		{
			//---if label goes off the bottom edge, force it to stay in the plot area.
			if( ( y - formattedTextTag.getWidth() ) < axisValueRenderEvent.getTotalItemAxisArea().y )
			{
				y = axisValueRenderEvent.getTotalItemAxisArea().y;
				y += formattedTextTag.getWidth();
				y += this.pixelValuePadding;
			}
			//---if label goes off bottom edge, force to the up
			else if( y > axisValueRenderEvent.getTotalItemAxisArea().y + axisValueRenderEvent.getTotalItemAxisArea().height )
			{
				y = axisValueRenderEvent.getTotalItemAxisArea().y + axisValueRenderEvent.getTotalItemAxisArea().height;
				y -= this.pixelValuePadding;
			}
		}
		else
		{
			//---if label goes off the top edge, force it to stay in the plot area.
			if( ( y - formattedTextTag.getHeight() ) < axisValueRenderEvent.getTotalItemAxisArea().y )
			{
				y = axisValueRenderEvent.getTotalItemAxisArea().y;
				y += formattedTextTag.getHeight();
				y += this.pixelValuePadding;
			}
			//---if label goes off bottom edge, force to the up
			else if( y > axisValueRenderEvent.getTotalItemAxisArea().y + axisValueRenderEvent.getTotalItemAxisArea().height )
			{
				y = axisValueRenderEvent.getTotalItemAxisArea().y + axisValueRenderEvent.getTotalItemAxisArea().height;
				y -= this.pixelValuePadding;
			}
		}

		return y;
	}
}
