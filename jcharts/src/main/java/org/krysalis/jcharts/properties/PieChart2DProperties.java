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

package org.krysalis.jcharts.properties;


import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.PieLabelType;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: PieChart2DProperties.java,v 1.4 2004/05/31 16:31:07 nathaniel_auvil Exp $
 ************************************************************************************/
public class PieChart2DProperties extends ChartTypeProperties implements HTMLTestable
{
/*
	public static final Paint DEFAULT_BORDER_PAINT = Color.black;
	public static final Stroke DEFAULT_BORDER_STROKE = new BasicStroke( 1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND );

	private Paint borderPaint = DEFAULT_BORDER_PAINT;
	private Stroke borderStroke = DEFAULT_BORDER_STROKE;
*/

	private ChartStroke borderChartStroke= ChartStroke.DEFAULT_PIE_OUTLINE;


	//---draw a twenty degrees by default.
	private float zeroDegreeOffset = 20.0f;

	private PieLabelType pieLabelType = PieLabelType.NO_LABELS;
	private boolean isCurrency = false;
	private boolean showGrouping = false;
	private int roundingPowerOfTen = 0;
	private ChartFont valueLabelFont = ChartFont.DEFAULT_PIE_VALUE;
	private float tickLength = 5;


	/*********************************************************************************************
	 *
	 **********************************************************************************************/
	public PieChart2DProperties()
	{
		super();
	}


	/*********************************************************************************************
	 * The offset from zero degrees which the first slice of pie is drawn.  Charts look better to
	 *  my eye when they do not start at zero degrees, but beauty is in the eye of the beholder.
	 *
	 * @param zeroDegreeOffset
	 **********************************************************************************************/
	public void setZeroDegreeOffset( float zeroDegreeOffset )
	{
		this.zeroDegreeOffset = zeroDegreeOffset;
	}


	/*********************************************************************************************
	 *
	 * @return double
	 **********************************************************************************************/
	public float getZeroDegreeOffset()
	{
		return this.zeroDegreeOffset;
	}


	public ChartStroke getBorderChartStroke()
	{
		return borderChartStroke;
	}


	public void setBorderChartStroke( ChartStroke borderChartStroke )
	{
		this.borderChartStroke = borderChartStroke;
	}


	/*****************************************************************************
	 *
	 *****************************************************************************/
	public PieLabelType getPieLabelType()
	{
		return pieLabelType;
	}


	/****************************************************************************
	 * Sets the type of label to draw on the PieChart. The default value is:
	 * 	PieLabelType.NO_LABELS so no labels will be drawn on chart.
	 *
	 * @param pieLabelType
	 ***************************************************************************/
	public void setPieLabelType( PieLabelType pieLabelType )
	{
		this.pieLabelType = pieLabelType;
	}


	public ChartFont getValueLabelFont()
	{
		return valueLabelFont;
	}


	/*****************************************************************************
	 * Sets the ChartFont used to draw the chart value labels.
	 *
	 * @param valueLabelFont
	 *****************************************************************************/
	public void setValueLabelFont( ChartFont valueLabelFont )
	{
		this.valueLabelFont = valueLabelFont;
	}


	public void setCurrency( boolean currency )
	{
		isCurrency = currency;
	}


	public void setShowGrouping( boolean showGrouping )
	{
		this.showGrouping = showGrouping;
	}


	public void setRoundingPowerOfTen( int roundingPowerOfTen )
	{
		this.roundingPowerOfTen = roundingPowerOfTen;
	}


	public void setTickLength( float tickLength )
	{
		this.tickLength = tickLength;
	}


	public float getTickLength()
	{
		return tickLength;
	}


	public boolean showValueLabelCurrency()
	{
		return isCurrency;
	}


	public boolean showValueLabelGrouping()
	{
		return showGrouping;
	}


	public int getValueLabelRoundingPowerOfTen()
	{
		return roundingPowerOfTen;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "PieChart2DProperties" );
		htmlGenerator.addTableRow( "Zero Degree Offset", Double.toString( this.getZeroDegreeOffset() ) );
		htmlGenerator.addTableRow( "Border Chart Stroke", this.getBorderChartStroke() );
		htmlGenerator.addTableRow( "Label Type", this.pieLabelType );
		htmlGenerator.propertiesTableEnd();
	}


	/******************************************************************************************
	 * Validates the properties.
	 *
	 * @param chart
	 * @throws PropertyException
	 *****************************************************************************************/
	public void validate( Chart chart ) throws PropertyException
	{
//TODO
	}

}
