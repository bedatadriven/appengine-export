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
 * @version $Id: UserDefinedScaleCalculator.java,v 1.1 2003/05/17 16:54:37 nathaniel_auvil Exp $
 ************************************************************************************/
public class UserDefinedScaleCalculator extends ScaleCalculator
{
   private double userDefinedMinimum;
	private double userDefinedIncrement;


	/****************************************************************************************
	 *
	 * It would seem to make sense to pass in the min and the max, but we want to allow
	 * 	people to use custom implementations which will be created when the AxisChart
	 * 	constructor gets called and we will not have looped the data to find the min
	 * 	and max yet. No sense in making people do that when we will do that already.
	 *
	 * @param userDefinedMinimum
	 * @param userDefinedIncrement
	 ***************************************************************************************/
	public UserDefinedScaleCalculator( double userDefinedMinimum, double userDefinedIncrement )
	{
		this.userDefinedMinimum = userDefinedMinimum;
		this.userDefinedIncrement = userDefinedIncrement;
	}


	/*********************************************************************************************
	 * Computes the axis increment WITHOUT taking into account the user specified rounding
	 * 	criteria and sets it to the super class increment variable. You can extend this class
	 * 	and override this method to compute you own scale.
	 *
	 ********************************************************************************************/
	protected void computeIncrement()
	{
		super.increment = this.userDefinedIncrement;

		double powerOfTen = Math.pow( 10, Math.abs( this.getRoundingPowerOfTen() ) );

		//---round the increment according to user defined power
		super.increment = super.round( super.increment, powerOfTen );

		//---if we round this down to zero, force it to the power of ten.
		//---for example, round to nearest 100, value = 35...would push down to 0 which is illegal.
		if( super.increment == 0 )
		{
			super.increment = powerOfTen;
		}

		super.setMinValue( super.round( this.userDefinedMinimum, powerOfTen ) );
		super.setMaxValue( super.getMinValue() + ( super.increment * super.getNumberOfScaleItems() ) );

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
		//---round the increment according to user defined power
		super.increment = super.round( super.increment, powerOfTen );

		//---if we round this down to zero, force it to the power of ten.
		//---for example, round to nearest 100, value = 35...would push down to 0 which is illegal.
		if( super.increment == 0 )
		{
			super.increment = powerOfTen;
		}

		super.setMinValue( super.round( this.userDefinedMinimum, powerOfTen ) );
		super.setMaxValue( super.getMinValue() + ( super.increment * numberOfScaleItems ) );
	}
	*/
}
