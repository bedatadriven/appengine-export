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

package org.krysalis.jcharts.chartText;


import org.krysalis.jcharts.axisChart.axis.scale.ScaleCalculator;
import org.krysalis.jcharts.properties.util.ChartFont;

import com.google.code.appengine.awt.font.FontRenderContext;
import java.text.NumberFormat;


/*************************************************************************************
 *
 * @author Nathaniel Auvil, John Thomsen
 * @version $Id: NumericTagGroup.java,v 1.1 2003/05/17 16:58:32 nathaniel_auvil Exp $
 ************************************************************************************/
public class NumericTagGroup extends TextTagGroup
{
	private NumberFormat numberFormat;


	/******************************************************************************************
	 *
	 * @param chartFont
	 * @param fontRenderContext
	 * @param isCurrency
	 * @param isPercent
	 * @param showCommas
	 * @param roundingPowerOfTen the exponent of ten to round to: 1=10, -1=.1, 3=1000, etc...
	 *******************************************************************************************/
	public NumericTagGroup( ChartFont chartFont,
									FontRenderContext fontRenderContext,
									boolean isCurrency,
									boolean isPercent,
									boolean showCommas,
									int roundingPowerOfTen )
	{
		super( chartFont, fontRenderContext );

		this.numberFormat = getNumberFormatInstance( isCurrency, isPercent, showCommas, roundingPowerOfTen );
	}


	/*********************************************************************************************
	 * Allow use of this logic to not only this class but to the chart label values.
	 *
	 * @param isCurrency
	 * @param isPercent
	 * @param showCommas
	 * @param roundingPowerOfTen
	 * @return NumberFormat
	 ********************************************************************************************/
	public static final NumberFormat getNumberFormatInstance( boolean isCurrency,
																				 boolean isPercent,
																				 boolean showCommas,
																				 int roundingPowerOfTen )
	{
		NumberFormat numberFormat;

		//---CURRENCY
		if( isCurrency )
		{
			numberFormat = NumberFormat.getCurrencyInstance();
		}
		else if( isPercent )
		{
			numberFormat = NumberFormat.getPercentInstance();
		}
		else
		{
			numberFormat = NumberFormat.getInstance();
		}

		//---COMMAS
		if( showCommas )
		{
			numberFormat.setGroupingUsed( true );
		}
		else
		{
			numberFormat.setGroupingUsed( false );
		}

		//---TRIM OFF DECIMAL PLACES IF ROUND TO WHOLE NUMBER
		if( roundingPowerOfTen >= 0 )
		{
			numberFormat.setMaximumFractionDigits( 0 );
			numberFormat.setMinimumFractionDigits( 0 );
		}
		else
		{
			numberFormat.setMaximumFractionDigits( -roundingPowerOfTen );
			numberFormat.setMinimumFractionDigits( -roundingPowerOfTen );
		}

		return numberFormat;
	}


	/******************************************************************************************
	 *
	 * @param value
	 *****************************************************************************************/
	public void addLabel( double value )
	{
		super.addLabel( this.numberFormat.format( value ) );
	}


	/*******************************************************************************************
	 * Creates the axis labels for the passed ScaleCalculator instance.
	 *
	 * @param scaleCalculator
	 *******************************************************************************************/
	public void createAxisScaleLabels( ScaleCalculator scaleCalculator )
	{
		double value = scaleCalculator.getMinValue();
		this.addLabel( value );

		for( int i = 1; i < scaleCalculator.getNumberOfScaleItems(); i++ )
		{
			value += scaleCalculator.getIncrement();
			this.addLabel( value );
		}
	}

}
