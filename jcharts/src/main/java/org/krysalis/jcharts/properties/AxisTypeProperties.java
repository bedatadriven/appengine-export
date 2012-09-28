/***********************************************************************************************
 * File Info: $Id: AxisTypeProperties.java,v 1.2 2003/08/27 04:37:44 kiwicmc Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil
 * Contributor(s): John Thomson
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


package org.krysalis.jcharts.properties;


import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import java.lang.reflect.Field;


public class AxisTypeProperties extends Properties implements HTMLTestable
{
	public static final int TICKS_NONE = 0;
	public static final int TICKS_ALL = 1;
	public static final int TICKS_ONLY_WITH_LABELS = 2;

	public static final int GRID_LINES_NONE = 0;
	public static final int GRID_LINES_ALL = 1;
	public static final int GRID_LINES_ONLY_WITH_LABELS = 2;


	private int showTicks = TICKS_ALL;
	private ChartStroke tickChartStroke= ChartStroke.DEFAULT_TICKS;

	private int showGridLines = GRID_LINES_NONE;
	private ChartStroke gridLineChartStroke= ChartStroke.DEFAULT_GRIDLINES;

	private ChartFont scaleChartFont = ChartFont.DEFAULT_AXIS_SCALE;
	private ChartFont axisTitleChartFont= ChartFont.DEFAULT_AXIS_TITLE;

	private ChartStroke axisStroke = ChartStroke.DEFAULT_AXIS;


	private float paddingBetweenAxisTitleAndLabels = 25.0f;
	private float axisTickMarkPixelLength = 3.0f;

	//---number of pixels between axis values and tick marks.
	private float paddingBetweenLabelsAndTicks = 3.0f;

	//---number of pixels between axis values and the axis if no ticks are shown.
	private float paddingBetweenAxisAndLabels = 3.0f;

	//---number of pixels between axis values.
	private float paddingBetweenAxisLabels = 5.0f;


	private boolean showEndBorder = true;
   private boolean showAxisLabels= true;

        // Dual Y axis changes integrated CMC 25Aug03
        //variable added for the right axis
        //the method corresponding are so implemented
        private ChartFont scaleChartFontRight = ChartFont.DEFAULT_AXIS_SCALE;
        private boolean showRightAxis = false;
        private float secondScaleRight=1;
        private double minRightAxis = 0;
        private double maxRightAxis = 0;

	private ChartFont titleChartFont= ChartFont.DEFAULT_AXIS_TITLE;


	/***********************************************************************************************
	 *
	 ************************************************************************************************/
	public AxisTypeProperties()
	{
		super();
	}


	public ChartFont getTitleChartFont()
	{
		return titleChartFont;
	}


	public void setTitleChartFont( ChartFont titleChartFont )
	{
		this.titleChartFont = titleChartFont;
	}


	public boolean showAxisLabels()
	{
		return showAxisLabels;
	}


	public void setShowAxisLabels( boolean showAxisLabels )
	{
		this.showAxisLabels = showAxisLabels;
	}


	public int getShowTicks()
	{
		return showTicks;
	}


	public void setShowTicks( int showTicks )
	{
		this.showTicks = showTicks;
	}


	public ChartStroke getTickChartStroke()
	{
		return this.tickChartStroke;
	}


	public void setTickChartStroke( ChartStroke tickChartStroke )
	{
		this.tickChartStroke = tickChartStroke;
	}


	public int getShowGridLines()
	{
		return showGridLines;
	}


	public void setShowGridLines( int showGridLines )
	{
		this.showGridLines = showGridLines;
	}


	public ChartStroke getGridLineChartStroke()
	{
		return this.gridLineChartStroke;
	}


	public void setGridLineChartStroke( ChartStroke gridLineChartStroke )
	{
		this.gridLineChartStroke = gridLineChartStroke;
	}


	public float getPaddingBetweenAxisTitleAndLabels()
	{
		return paddingBetweenAxisTitleAndLabels;
	}


	public void setPaddingBetweenAxisTitleAndLabels( float paddingBetweenAxisTitleAndLabels )
	{
		this.paddingBetweenAxisTitleAndLabels = paddingBetweenAxisTitleAndLabels;
	}


	public float getAxisTickMarkPixelLength()
	{
		return axisTickMarkPixelLength;
	}


	public void setAxisTickMarkPixelLength( float axisTickMarkPixelLength )
	{
		this.axisTickMarkPixelLength = axisTickMarkPixelLength;
	}


	public float getPaddingBetweenLabelsAndTicks()
	{
		return paddingBetweenLabelsAndTicks;
	}


	public void setPaddingBetweenLabelsAndTicks( float paddingBetweenLabelsAndTicks )
	{
		this.paddingBetweenLabelsAndTicks = paddingBetweenLabelsAndTicks;
	}


	public float getPaddingBetweenAxisAndLabels()
	{
		return paddingBetweenAxisAndLabels;
	}


	public void setPaddingBetweenAxisAndLabels( float paddingBetweenAxisAndLabels )
	{
		this.paddingBetweenAxisAndLabels = paddingBetweenAxisAndLabels;
	}


	public ChartStroke getAxisStroke()
	{
		return axisStroke;
	}


	public void setAxisStroke( ChartStroke axisStroke )
	{
		this.axisStroke = axisStroke;
	}


	public ChartFont getScaleChartFont()
	{
		return scaleChartFont;
	}


	public void setScaleChartFont( ChartFont scaleChartFont )
	{
		this.scaleChartFont = scaleChartFont;
	}

        // Dual Y axis changes integrated CMC 25Aug03
	public ChartFont getScaleChartFontRight()
	{
		return scaleChartFontRight;
	}


	public void setScaleChartFontRight( ChartFont scaleChartFontRight )
	{
		this.scaleChartFontRight = scaleChartFontRight;
	}

	public ChartFont getAxisTitleChartFont()
	{
		return axisTitleChartFont;
	}


	public void setAxisTitleChartFont( ChartFont axisTitleChartFont )
	{
		this.axisTitleChartFont = axisTitleChartFont;
	}


	public boolean getShowEndBorder()
	{
		return this.showEndBorder;
	}


	public void setShowEndBorder( boolean showEndBorder )
	{
		this.showEndBorder = showEndBorder;
	}


        // Dual Y axis changes integrated CMC 25Aug03
        public boolean getShowRightAxis()
	{
		return this.showRightAxis;
	}


	public void setShowRightAxis( boolean showRightAxis )
	{
		this.showRightAxis = showRightAxis;
	}


        public float getSecondScaleRight()
        {
                return this.secondScaleRight;
        }


        public void setSecondScaleRight(float secondScaleRight)
        {
                this.secondScaleRight=secondScaleRight;
        }

        public double getMinRightAxis()
        {
                return this.minRightAxis;
        }

        
        public void setMinRightAxis(double minRightAxis)
        {
                this.minRightAxis=minRightAxis;
        }


        public double getMaxRightAxis()
        {
                return this.maxRightAxis;
        }


        public void setMaxRightAxis(double maxRightAxis)
        {
                this.maxRightAxis=maxRightAxis;
        }

        
        public float getPaddingBetweenAxisLabels()
	{
		return this.paddingBetweenAxisLabels;
	}


	public void setPaddingBetweenXAxisLabels( float paddingBetweenAxisLabels )
	{
		this.paddingBetweenAxisLabels = paddingBetweenAxisLabels;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( AxisTypeProperties.class.getName() );

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
				System.out.println( "can not access field: " + fields[i].getName() );
				//illegalAccessException.printStackTrace();
			}
		}

		htmlGenerator.propertiesTableEnd();
	}


}
