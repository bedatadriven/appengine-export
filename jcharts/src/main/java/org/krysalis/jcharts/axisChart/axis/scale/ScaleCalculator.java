/***********************************************************************************************
 * File Info: $Id: ScaleCalculator.java,v 1.2 2004/05/31 16:28:44 nathaniel_auvil Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil, Mike Lissick
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


package org.krysalis.jcharts.axisChart.axis.scale;


/***************************************************************************************
 * Base class for the logic used to compute the scale on the charts.  There are two
 * 	implementations provided and you are free to implement your own if you do not like
 * 	the default implementations provided.
 *
 *
 ***************************************************************************************/
public abstract class ScaleCalculator
{
	private double minValue;
	private double maxValue;
	private int roundingPowerOfTen;
	private int numberOfScaleItems;

	protected double increment;



	/***************************************************************************************
	 * Computes the scale increment.
	 *
	 **************************************************************************************/
	protected abstract void computeIncrement();


	/*********************************************************************************************
	 * Drives the computation of the axis increment and related values taking into account the
	 * 	user specified rounding criteria.
	 *
	 * So if you specify to round to the nearest 100 and give an increment of 2.5, the increment
	 * 	will become 100.
	 *
	 ********************************************************************************************/
	public final void computeScaleValues()
	{
		this.computeIncrement();

		//double powerOfTen = Math.pow( 10, Math.abs( this.getRoundingPowerOfTen() ) );
		//this.roundScaleValues( powerOfTen, numberOfScaleItems );
	}


	public final void setMinValue( double minValue )
	{
		this.minValue = minValue;
	}


	public final double getMinValue()
	{
		return this.minValue;
	}


	public final void setMaxValue( double maxValue )
	{
		this.maxValue = maxValue;
	}


	public final double getMaxValue()
	{
		return this.maxValue;
	}


	public final double getIncrement()
	{
		return increment;
	}


	public int getNumberOfScaleItems()
	{
		return numberOfScaleItems;
	}


	public void setNumberOfScaleItems( int numberOfScaleItems )
	{
		this.numberOfScaleItems = numberOfScaleItems;
	}


	/***********************************************************************************************
	 * Sets the exponent power of ten to round values to.
	 *
	 * @param powerOfTen exponent of ten to round to: 1=10, 2=100, -2=.01
	 ***********************************************************************************************/
	public final void setRoundingPowerOfTen( int powerOfTen )
	{
		this.roundingPowerOfTen = powerOfTen;
	}


	public final int getRoundingPowerOfTen()
	{
		return this.roundingPowerOfTen;
	}


	/***********************************************************************************************
	 * Rounds the passed value by the power of ten specified
	 *
	 * @param value the value to round
	 * @param powerOfTen the product of 10 times the rounding property.
	 * @return double the rounded result
	 ************************************************************************************************/
	protected double round( double value, double powerOfTen )
	{
		if( this.roundingPowerOfTen > 0 )
		{
			return ( Math.round( value / powerOfTen ) * powerOfTen );
		}
		else if( this.roundingPowerOfTen < 0 )
		{
			return ( Math.round( value * powerOfTen ) / powerOfTen );
		}
		else
		{
			return ( Math.round( value ) );
		}
	}


	/***********************************************************************************************
	 * Rounds the scale increment up by the power of ten specified in the properties.
	 *
	 * @param powerOfTen the value of 10 times the rounding property.
	 ************************************************************************************************/
	protected void roundTheIncrement( double powerOfTen )
	{
		this.increment = this.round( this.increment, powerOfTen );

		//---round the increment up or down
		if( this.roundingPowerOfTen > 0 )
		{
			this.increment += powerOfTen;
		}
		else
		{
			this.increment += ( 1 / powerOfTen );
		}
	}


   /*******************************************************************************
	 *
	 * @return String
	 *******************************************************************************/
	public String toString()
	{
		StringBuffer s= new StringBuffer( 90 );
		s.append( "ScaleCalculator-> min= " );
		s.append( this.minValue );
		s.append( "  max= " );
		s.append( this.maxValue );
		s.append( "  increment= " );
		s.append( this.increment );
		return s.toString();
	}
}
