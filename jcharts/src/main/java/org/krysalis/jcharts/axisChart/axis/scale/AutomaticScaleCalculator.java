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

package org.krysalis.jcharts.axisChart.axis.scale;


import org.krysalis.jcharts.axisChart.axis.scale.ScaleCalculator;


/*************************************************************************************
 *
 * @author Nathaniel Auvil, Mike Lissick
 * @version $Id: AutomaticScaleCalculator.java,v 1.1 2003/05/17 16:54:36 nathaniel_auvil Exp $
 ************************************************************************************/
public class AutomaticScaleCalculator extends ScaleCalculator
{

	/****************************************************************************************
	 *
	 ***************************************************************************************/
	public AutomaticScaleCalculator()
	{

	}


	/*********************************************************************************************
	 * Computes the axis increment taking into account the user specified criteria.
	 *
	 ********************************************************************************************/
	public void computeIncrement()
	{
		double powerOfTen = Math.pow( 10.0d, Math.abs( ( double ) super.getRoundingPowerOfTen() ) );

		double range;


		//---if MIN >= 0, MAX is the range, if MAX < 0, -MIN is the range
		if( ( super.getMinValue() >= 0 ) || ( super.getMaxValue() < 0 ) )
		{
			range = Math.max( super.getMaxValue(), -super.getMinValue() );

			super.increment = range / ( super.getNumberOfScaleItems() - 1 );
			this.roundTheIncrement( powerOfTen );

			if( super.getMinValue() >= 0 )
			{
				super.setMinValue( 0.0d );
				super.setMaxValue( super.increment * super.getNumberOfScaleItems() );
			}
			else
			{
				super.setMaxValue( 0.0d );
				super.setMinValue( -( super.increment * super.getNumberOfScaleItems() ) );
			}
		}
		//---else MIN is negative and MAX is positive, so add values together (minus a negative is a positive)
		else
		{
			super.setMinValue( super.round( super.getMinValue(), powerOfTen ) );

			//---round min value down to get the start value for axis.  Compute range from this value.
			if( super.getRoundingPowerOfTen() > 0 )
			{
				super.setMinValue( super.getMinValue() - powerOfTen );
			}
			else
			{
				super.setMinValue( super.getMinValue()- ( 1 / powerOfTen ) );
			}

			//---we want the rounded Axis min for range
			//---MIN is always negative at this point so minus a negative is a positive
			range = super.getMaxValue() - super.getMinValue();

			super.increment = range / ( super.getNumberOfScaleItems() - 1 );
			super.roundTheIncrement( powerOfTen );

			//---axis starts at minValue, not zero!
			super.setMaxValue( super.getMinValue() + ( this.increment * super.getNumberOfScaleItems() ) );
		}
	}


	/*********************************************************************************************
	 * Drives the computation of the axis increment  and related values taking into account the
	 * 	user specified rounding criteria and sets it to the super class increment variable.
	 *
	 * So if you specify to round to the nearest 100 and give an increment of 2.5, the increment
	 * 	will become 100.
	 *
	 * @param numberOfScaleItems
	 ********************************************************************************************
	public void roundScaleValues( double powerOfTen, int numberOfScaleItems )
	{
		this.roundTheIncrement( powerOfTen );


		//---if MIN >= 0, MAX is the range, if MAX < 0, -MIN is the range
		if( ( super.getMinValue() >= 0 ) || ( super.getMaxValue() < 0 ) )
		{
			if( super.getMinValue() >= 0 )
			{
				super.setMinValue( 0.0d );
				super.setMaxValue( super.increment * numberOfScaleItems );
			}
			else
			{
				super.setMaxValue( 0.0d );
				super.setMinValue( -( super.increment * numberOfScaleItems ) );
			}
		}
		//---else MIN is negative and MAX is positive, so add values together (minus a negative is a positive)
		else
		{
			super.setMinValue( super.round( super.getMinValue(), powerOfTen ) );

			//---round min value down to get the start value for axis.  Compute range from this value.
			if( super.getRoundingPowerOfTen() > 0 )
			{
				super.setMinValue( super.getMinValue() - powerOfTen );
			}
			else
			{
				super.setMinValue( super.getMinValue()- ( 1 / powerOfTen ) );
			}

			//---we want the rounded Axis min for range
			//---MIN is always negative at this point so minus a negative is a positive
			range = super.getMaxValue() - super.getMinValue();

			super.increment = range / numberOfScaleItems;
			super.roundTheIncrement( powerOfTen );

			//---axis starts at minValue, not zero!
			super.setMaxValue( super.getMinValue() + ( this.increment * numberOfScaleItems ) );
		}


	}

	*/


/*

	public static void main( String[] args )
	{
		AutomaticScaleCalculator s= new AutomaticScaleCalculator();

	}
*/
}
