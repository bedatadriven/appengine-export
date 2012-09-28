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
import org.krysalis.jcharts.axisChart.axis.scale.ScaleCalculator;
import org.krysalis.jcharts.chartText.TextTagGroup;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.properties.AxisTypeProperties;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import com.google.code.appengine.awt.font.TextLayout;
import java.io.Serializable;
import java.lang.reflect.Field;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: Axis.java,v 1.3 2003/08/27 04:37:44 kiwicmc Exp $
 ************************************************************************************/
abstract public class Axis implements HTMLTestable, Serializable
{
	//---reference to containing chart
	private AxisChart axisChart;

	//---number of pixels between each label
	private float scalePixelWidth;

	//---the length of the axis
	private float pixelLength;

	//---the pixel coordinate of the start of the axis
	private float origin;


	private float titleHeight;
	private float titleWidth;

	private float minimumWidthNeeded;
	private float minimumHeightNeeded;

	//---Note, if no labels are displayed, axisLabelsGroup will be NULL!
	private TextTagGroup axisLabelsGroup;

        // Dual Y axis changes integrated CMC 25Aug03
        // Variable to use to display the labels of the right Y axis
        // the corresponding methods are also implemented
        private TextTagGroup axisLabelsGroupRight;

	//---this number of items to plot on the Axis. Note, if no labels are displayed, axisLabelsGroup will be NULL so we can not depend on that.
	private int numberOfScaleItems;

	//---pixel location of tick start needed for reference when start drawing charts.
	private float tickStart;


	//---multiplication value used to determine the coordinate location of values on YAxis
	private double oneUnitPixelSize;

	private float zeroLineCoordinate;


	private ScaleCalculator scaleCalculator;


	/**************************************************************************************************
	 * Constructor
	 *
	 * @param axisChart
	 * @param numberOfScaleItems
	 ***************************************************************************************************/
	public Axis( AxisChart axisChart, int numberOfScaleItems )
	{
		this.axisChart = axisChart;
		this.numberOfScaleItems= numberOfScaleItems;
	}


	/**************************************************************************************************
	 * Returns reference to AxisChart Object.
	 *
	 * @return axisChart
	 ***************************************************************************************************/
	public final AxisChart getAxisChart()
	{
		return this.axisChart;
	}


	public int getNumberOfScaleItems()
	{
		return numberOfScaleItems;
	}


	public TextTagGroup getAxisLabelsGroup()
	{
		return axisLabelsGroup;
	}


	public void setAxisLabelsGroup( TextTagGroup axisLabelsGroup )
	{
		this.axisLabelsGroup = axisLabelsGroup;
	}


        // Dual Y axis changes integrated CMC 25Aug03
        public TextTagGroup getAxisLabelsGroupRight()
	{
		return axisLabelsGroupRight;
	}

	public void setAxisLabelsGroupRight( TextTagGroup axisLabelsGroupRight )
	{
		this.axisLabelsGroupRight = axisLabelsGroupRight;
	}

        public final float getTitleWidth()
	{
		return this.titleWidth;
	}


	public final float getTitleHeight()
	{
		return this.titleHeight;
	}


	public final float getPixelLength()
	{
		return this.pixelLength;
	}


	public final void setPixelLength( float pixelLength )
	{
		this.pixelLength = pixelLength;
	}


	public final float getOrigin()
	{
		return this.origin;
	}


	public final void setOrigin( float origin )
	{
		this.origin = origin;
	}


	public final float getMinimumWidthNeeded()
	{
		return this.minimumWidthNeeded;
	}


	public final void setMinimumWidthNeeded( float minimumWidthNeeded )
	{
		this.minimumWidthNeeded = minimumWidthNeeded;
	}


	public final float getMinimumHeightNeeded()
	{
		return this.minimumHeightNeeded;
	}


	public final void setMinimumHeightNeeded( float minimumHeightNeeded )
	{
		this.minimumHeightNeeded = minimumHeightNeeded;
	}


	public final float getScalePixelWidth()
	{
		return this.scalePixelWidth;
	}


	public final void setScalePixelWidth( float scalePixelWidth )
	{
		this.scalePixelWidth = scalePixelWidth;
	}


	public float getTickStart()
	{
		return tickStart;
	}


	public void setTickStart( float tickStart )
	{
		this.tickStart = tickStart;
	}


	public ScaleCalculator getScaleCalculator()
	{
		return scaleCalculator;
	}


	public void setScaleCalculator( ScaleCalculator scaleCalculator )
	{
		this.scaleCalculator = scaleCalculator;
	}


	/*********************************************************************************************
	 *
	 *
	 * @param title
	 * @param axisTitleFont
	 **********************************************************************************************/
	public final void computeAxisTitleDimensions( String title, ChartFont axisTitleFont )
	{
		TextLayout textLayout = new TextLayout( title,
															 axisTitleFont.getFont(),
															 this.getAxisChart().getGraphics2D().getFontRenderContext() );

		this.titleWidth = textLayout.getAdvance();
		this.titleHeight = textLayout.getAscent() + textLayout.getDescent();
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * Can only see 'private' fields from this class.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		String name = this.getClass().getSuperclass().getName() + "->";

		//---calling on instance of YAxis or XAxis
		Field[] fields = this.getClass().getSuperclass().getDeclaredFields();
		for( int i = 0; i < fields.length; i++ )
		{
			try
			{
				htmlGenerator.addField( name + fields[ i ].getName(), fields[ i ].get( this ) );
			}
			catch( IllegalAccessException illegalAccessException )
			{
				illegalAccessException.printStackTrace();
			}
		}
	}


	/*************************************************************************************************
	 * Computes the number of pixels between each value on the axis.
	 *
	 **************************************************************************************************/
	public void computeScalePixelWidth( AxisTypeProperties axisTypeProperties )
	{
		setScalePixelWidth( getPixelLength() / this.getNumberOfScaleItems() );
	}


	/*************************************************************************************************
	 * Computes the number of pixels between each value on the axis.
	 *
	 **************************************************************************************************/
	public void computeScalePixelWidthDataAxis( AxisTypeProperties axisTypeProperties )
	{
		//---if we are plotting data items, allow for a padding beyond the tallest value so we are not
		//---	hitting the top of the axis
		if( axisTypeProperties instanceof DataAxisProperties ) {
			setScalePixelWidth( (getPixelLength() - 5) / ( this.numberOfScaleItems - 1) );
		}
		else {
			setScalePixelWidth( (getPixelLength() ) / ( this.numberOfScaleItems - 1 ) );
		}
	}


	public void setOneUnitPixelSize( double oneUnitPixelSize )
	{
		this.oneUnitPixelSize = oneUnitPixelSize;
	}


	/**************************************************************************************************
	 * Returns the number of pixels one value unit occupies.
	 *
	 * @return double the number of pixels one value unit occupies.
	 ***************************************************************************************************/
	public double getOneUnitPixelSize()
	{
		return this.oneUnitPixelSize;
	}


	/**************************************************************************************************
	 * Returns the screen coordinate of the zero line.  This will not always be the same as the origin
	 *  	as not all charts start at zero. This is needed not only by the Axis, but some of the Chart
	 *		implementations as well.
	 *
	 * @return float the screen pixel location of the zero line.
	 ***************************************************************************************************/
	public float getZeroLineCoordinate()
	{
		return this.zeroLineCoordinate;
	}


	/**************************************************************************************************
	 * Sets the screen coordinate of the zero line.  This will not always be the same as the origin
	 *  as not all charts start at zero.
	 *
	 * @param value the screen pixel location of the zero line.
	 ***************************************************************************************************/
	public void setZeroLineCoordinate( float value )
	{
		this.zeroLineCoordinate = value;
	}


	/*************************************************************************************************
	 * Computes the relationship of data point values to pixel values so know where along the axis
	 * 	a value is.
	 *
	 * @param scalePixelLength
	 * @param increment
	 **************************************************************************************************/
	public void computeOneUnitPixelSize( float scalePixelLength, double increment )
	{
		this.oneUnitPixelSize = scalePixelLength / increment;
	}


}
