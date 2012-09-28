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
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Line2D;
import java.lang.reflect.Field;


/*************************************************************************************
 *
 * @author Nathaniel Auvil, John Thomson
 * @version $Id: YAxis.java,v 1.5 2004/05/31 16:29:47 nathaniel_auvil Exp $
 ************************************************************************************/
public class YAxis extends Axis implements HTMLTestable
{

   private float lastTickY;


	/**************************************************************************************************
	 * Constructor
	 *
	 * @param axisChart
	 ***************************************************************************************************/
	public YAxis( AxisChart axisChart, int numberOfScaleItems )
	{
		super( axisChart, numberOfScaleItems );
	}


	/************************************************************************************
	 * Need this value as horizontal plots start from the top of the axis and go down
	 *
	 * @return float
	 ************************************************************************************/
	public float getLastTickY()
	{
		return lastTickY;
	}


	/*************************************************************************************************
	 * Computes the minimum pixel width required for the Y-Axis.
	 *  Includes space, if needed, for: axis title + padding, axis values + tick padding, and tick marks.
	 *
	 **************************************************************************************************/
	public void computeMinimumWidthNeeded( String yAxisTitle )
	{
		AxisTypeProperties axisTypeProperties= super.getAxisChart().getAxisProperties().getYAxisProperties();


		float widthNeeded = 0;


		//---if we are displaying axis scale labels, add width of widest label
		if( axisTypeProperties.showAxisLabels() )
		{
                        // Dual Y axis changes integrated CMC 25Aug03
			//widthNeeded+= super.getAxisLabelsGroup().getWidestLabel();
                        if ( axisTypeProperties.getShowRightAxis() )
                        {
                                if ( super.getAxisLabelsGroupRight().getWidestLabel()>super.getAxisLabelsGroup().getWidestLabel())
                                {
                                        widthNeeded+= super.getAxisLabelsGroupRight().getWidestLabel();
                                }
                                else
                                {
                                        widthNeeded+= super.getAxisLabelsGroup().getWidestLabel();
                                }
                            }
                        else
                        {
                                widthNeeded+= super.getAxisLabelsGroup().getWidestLabel();
                        }
                }


		if( axisTypeProperties.getShowTicks() != AxisTypeProperties.TICKS_NONE )
		{
			//---add the padding between scale labels and tick marks
			widthNeeded += axisTypeProperties.getPaddingBetweenLabelsAndTicks();

			//---add width of tick marks
			widthNeeded += axisTypeProperties.getAxisTickMarkPixelLength();
		}
		else
		{
			//---else, if we are not showing any ticks, add padding between labels and axis, if we are displaying labels
			if( axisTypeProperties.showAxisLabels() )
			{
				widthNeeded += axisTypeProperties.getPaddingBetweenAxisAndLabels();
			}
		}


		//---include axis title height if needed. Remember it is vertical for y-axis
		//if( super.getAxisChart().getAxisProperties().getShowAxisTitle( AxisProperties.Y_AXIS ) )
		if( yAxisTitle != null )
		{
			super.computeAxisTitleDimensions( yAxisTitle, axisTypeProperties.getTitleChartFont() );
			widthNeeded += super.getTitleHeight();
			widthNeeded += axisTypeProperties.getPaddingBetweenAxisTitleAndLabels();
		}

		super.setMinimumWidthNeeded( widthNeeded );
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
			float titleY; // = super.getAxisChart().getChartProperties().getEdgePadding();
			float titleX = super.getAxisChart().getXAxis().getOrigin() - super.getMinimumWidthNeeded() + super.getTitleHeight();

			//---if YAxis title is larger than the YAxis itself, center it on image.
			if( super.getTitleWidth() > super.getPixelLength() )
			{
				//titleY= super.getTitleWidth();
				titleY= super.getAxisChart().getImageHeight() - ( ( super.getAxisChart().getImageHeight() - super.getTitleWidth() ) / 2 );
			}
			//---else, center on YAxis.
			else
			{
				titleY = this.getOrigin() - ( ( super.getPixelLength() - super.getTitleWidth() ) / 2 );
			}

//TODO this should use a TextTag
			graphics2D.setFont( axisTypeProperties.getAxisTitleChartFont().getFont().deriveFont( ChartFont.VERTICAL_ROTATION ) );
			graphics2D.setPaint( axisTypeProperties.getAxisTitleChartFont().getPaint() );
			graphics2D.drawString( axisTitle, titleX, titleY );
		}
	}


	/*********************************************************************************************
	 * Renders the YAxis on the passes Graphics2D object
	 *
	 * @param graphics2D
	 * @param axisProperties
	 * @param yAxisTitle
	 **********************************************************************************************/
	public void render( Graphics2D graphics2D, AxisProperties axisProperties, String yAxisTitle )
	{
		AxisTypeProperties axisTypeProperties = axisProperties.getYAxisProperties();


		//---AXIS TITLE
		this.renderAxisTitle( yAxisTitle, graphics2D, axisProperties.getYAxisProperties() );


		Line2D.Float line2D;
		float stringY= 0;


		if( axisTypeProperties instanceof DataAxisProperties )
		{
			//---start at the axis
			line2D = new Line2D.Float( 0.0f, super.getOrigin(), 0.0f, super.getOrigin() );

			if( axisTypeProperties.showAxisLabels() )
			{
				stringY = super.getOrigin() + ( super.getAxisLabelsGroup().getTallestLabel() / 4 );
			}
		}
		else
		{
			//--start at half a axis item width
			float y = super.getOrigin() - ( super.getScalePixelWidth() / 2 );
			line2D = new Line2D.Float( 0.0f, y, 0.0f, y );

			if( axisTypeProperties.showAxisLabels() )
			{
				stringY = y + ( super.getAxisLabelsGroup().getTallestLabel() / 4 );

				//---horizontal plots start at top of axis and go down
				super.getAxisLabelsGroup().reverse();
			}
		}


		float tickX1 = super.getAxisChart().getXAxis().getOrigin() - axisTypeProperties.getAxisTickMarkPixelLength();
		float tickX2 = super.getAxisChart().getXAxis().getOrigin();
		float gridLineX1 = super.getAxisChart().getXAxis().getOrigin() + 1;
		float gridLineX2 = super.getAxisChart().getXAxis().getOrigin() + super.getAxisChart().getXAxis().getPixelLength();


		float stringX = super.getAxisChart().getXAxis().getOrigin() - axisTypeProperties.getAxisTickMarkPixelLength();

		if( axisTypeProperties.showAxisLabels() )
		{
			stringX-= axisTypeProperties.getPaddingBetweenLabelsAndTicks();
		}


		for( int i = 0; i < super.getNumberOfScaleItems(); i++ )
		{
			//---GRID LINES
			if( axisTypeProperties.getShowGridLines() != AxisTypeProperties.GRID_LINES_NONE )
			{
				//---we do not want to draw a grid line over the axis as data axis first value is on the axis
				if( i > 0 || ( i == 0 && !( axisTypeProperties instanceof DataAxisProperties ) ) )
				{
					line2D.x1 = gridLineX1;
					line2D.x2 = gridLineX2;

					//TODO what is this doing????  How could i ever equal the size?

					if( i < super.getAxisLabelsGroup().size()
						|| ( i == super.getAxisLabelsGroup().size() && !axisTypeProperties.getShowEndBorder() ) )

//					if( i == super.getNumberOfScaleItems() - 1 && !axisTypeProperties.getShowEndBorder() )
					{
						axisTypeProperties.getGridLineChartStroke().draw( graphics2D, line2D );
					}
					else
					{
						//---draw top border with the same ChartStroke as the X-Axis
						axisProperties.getXAxisProperties().getAxisStroke().draw( graphics2D, line2D );
					}
				}
			}

			//---TICK MARKS
			if( axisTypeProperties.getShowTicks() != AxisTypeProperties.TICKS_NONE )
			{
				line2D.x1 = tickX1;
				line2D.x2 = tickX2;
				axisTypeProperties.getTickChartStroke().draw( graphics2D, line2D );
			}

			//---need this value as horizontal plots start from the top of the axis and go down
			//---must set this no matter if no ticks are present as horizontal plots start their rendering based on this screen coordinate.
			this.lastTickY= line2D.y1;

			line2D.y1 -= super.getScalePixelWidth();
			line2D.y2 = line2D.y1;


			//---AXIS LABEL
			if( axisTypeProperties.showAxisLabels() )
			{
				super.getAxisLabelsGroup().render( i, graphics2D, stringX - super.getAxisLabelsGroup().getTextTag( i ).getWidth(), stringY );
				//graphics2D.setPaint( axisProperties.getScaleFontColor() );
				//graphics2D.drawString( this.formattedLabels[ i ], stringX - this.labelWidths[ i ], stringY );
			}

			stringY -= super.getScalePixelWidth();
		}


		//---AXIS----------------------------------------------------------------------
		line2D.x1 = super.getAxisChart().getXAxis().getOrigin();
		line2D.x2 = line2D.x1;
		line2D.y1 = super.getOrigin() - super.getPixelLength();
		line2D.y2 = super.getOrigin();
		axisTypeProperties.getAxisStroke().draw( graphics2D, line2D );


		//---TOP BORDER----------------------------------------------------------------
		if( axisTypeProperties.getShowEndBorder() )
		{
			line2D.x1 = super.getAxisChart().getXAxis().getOrigin();
			line2D.x2 = super.getAxisChart().getXAxis().getOrigin() + super.getAxisChart().getXAxis().getPixelLength();
			line2D.y1 = super.getOrigin() - super.getPixelLength();
			line2D.y2 = line2D.y1;
			axisProperties.getXAxisProperties().getAxisStroke().draw( graphics2D, line2D );
		}


		//---ZERO LINE-----------------------------------------------------------------
		if( axisTypeProperties instanceof DataAxisProperties )
		{
			DataAxisProperties dataAxisProperties = ( DataAxisProperties ) axisTypeProperties;

			if( dataAxisProperties.showZeroLine()
				&& super.getScaleCalculator().getMinValue() < 0.0d
				&& super.getScaleCalculator().getMaxValue() > 0.0d )
			{
				line2D.y1 = super.getZeroLineCoordinate();
				line2D.y2 = line2D.y1;
				line2D.x1 = super.getAxisChart().getXAxis().getOrigin();
				line2D.x2 = super.getAxisChart().getXAxis().getOrigin() + super.getAxisChart().getXAxis().getPixelLength();
				dataAxisProperties.getZeroLineChartStroke().draw( graphics2D, line2D );
			}
		}

                // Dual Y axis changes integrated CMC 25Aug03
                //---if AXIS at the right----------------------------------------------------------------------

                if (axisTypeProperties.getShowRightAxis())
                {

                        line2D.x1 = super.getAxisChart().getXAxis().getOrigin() + super.getAxisChart().getXAxis().getPixelLength();
                        line2D.x2 = line2D.x1;
                        line2D.y1 = super.getOrigin() - super.getPixelLength() - 10 ;
                        line2D.y2 = super.getOrigin();
                        axisTypeProperties.getAxisStroke().draw( graphics2D, line2D );

                        float tickRightX1 = super.getAxisChart().getXAxis().getOrigin() + super.getAxisChart().getXAxis().getPixelLength();
                        float tickRightX2 = tickRightX1 + axisTypeProperties.getAxisTickMarkPixelLength();
                        line2D.y1 = super.getOrigin();
                        line2D.y2 = line2D.y1;

                        stringY = super.getOrigin() + ( super.getAxisLabelsGroupRight().getTallestLabel() / 4 );
                        stringX = super.getAxisChart().getXAxis().getOrigin() + axisTypeProperties.getAxisTickMarkPixelLength()
                                    + super.getAxisChart().getXAxis().getPixelLength();

                        stringX+= axisTypeProperties.getPaddingBetweenLabelsAndTicks();

                        for( int i = 0; i < super.getNumberOfScaleItems(); i++ )
                        {
                                //---TICK MARKS at the right
                                if( axisTypeProperties.getShowTicks() != AxisTypeProperties.TICKS_NONE )
                                {
                                        line2D.x1 = tickRightX1;
                                        line2D.x2 = tickRightX2;
                                        axisTypeProperties.getTickChartStroke().draw( graphics2D, line2D );
                                }

                                line2D.y1 -= super.getScalePixelWidth();
                                line2D.y2 = line2D.y1;


                                //---AXIS LABEL at the right
                                if( axisTypeProperties.showAxisLabels() )
                                {

                                        // Font and Paint set in TextTagGroup object in 1.0 it seems CMC 25Aug03
                                        //graphics2D.setFont( axisTypeProperties.getScaleChartFontRight().getFont());
                                        //graphics2D.setPaint( axisTypeProperties.getScaleChartFontRight().getPaint() );
                                        super.getAxisLabelsGroupRight().render( i, graphics2D, stringX , stringY );
                                }
                                stringY -= super.getScalePixelWidth();
                        }
                }
	}


	/*************************************************************************************************
	 * Takes a value and determines the screen coordinate it should be drawn at. THe only difference
	 * 	between this and the x-axis is we subtract to the origin versus subtract from it.

	 *
	 * @param origin
	 * @param value
	 * @param axisMinValue the minimum value on the axis
	 * @return float the screen pixel coordinate
	 **************************************************************************************************/
	public float computeAxisCoordinate( float origin, double value, double axisMinValue )
	{
		double returnValue = origin - ( value - axisMinValue ) * this.getOneUnitPixelSize();
		return ( float ) returnValue;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
//todo is this method needed?
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

}
