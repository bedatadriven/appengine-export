/***********************************************************************************************
 * File Info: $Id: DataAxisProperties.java,v 1.1 2003/05/17 17:00:34 nathaniel_auvil Exp $
 * Copyright (C) 2002
 * Author: John Thomson, Nathaniel G. Auvil
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


package org.krysalis.jcharts.properties;


import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.axisChart.axis.scale.ScaleCalculator;

import java.lang.reflect.Field;


public final class DataAxisProperties extends LabelAxisProperties implements HTMLTestable
{
	//---round to nearest power of ten. 2==100, -3=.001
	private int roundToNearest = 0;

	private boolean showZeroLine = true;
	private ChartStroke zeroLineChartStroke= ChartStroke.DEFAULT_ZERO_LINE;

	//---user defined scale
	private boolean userDefinedScale = false;
	private double userDefinedMinimumValue;
	private double userDefinedIncrement;

	//---number of items visible on the axis
	private int numItems = 5;

	private boolean useDollarSigns = false;
	private boolean useCommas = true;
	private boolean usePercentSigns = false;


	//---holds data relevant to values displayed on the axis.  may be null
	private ScaleCalculator scaleCalculator;




	/***********************************************************************************************
	 *
	 ************************************************************************************************/
	public DataAxisProperties()
	{
		super();
	}


	/****************************************************************************************
	 *
	 * @param axisMinimum
	 * @param axisIncrement
	 * @throws PropertyException
	 **************************************************************************************/
	public void setUserDefinedScale( double axisMinimum, double axisIncrement ) throws PropertyException
	{
		if( axisIncrement <= 0 )
		{
			throw new PropertyException( "The Axis Increment can not be a negative value or zero." );
		}

		this.userDefinedScale = true;
		this.userDefinedMinimumValue = axisMinimum;
		this.userDefinedIncrement = axisIncrement;
	}



	public int getRoundToNearest()
	{
		return roundToNearest;
	}


	public void setRoundToNearest( int roundToNearest )
	{
		this.roundToNearest = roundToNearest;
	}


	public boolean showZeroLine()
	{
		return showZeroLine;
	}


	public void setShowZeroLine( boolean showZeroLine )
	{
		this.showZeroLine = showZeroLine;
	}


	public ChartStroke getZeroLineChartStroke()
	{
		return this.zeroLineChartStroke;
	}


	public void setZeroLineChartStroke( ChartStroke zeroLine )
	{
		this.zeroLineChartStroke = zeroLine;
	}


	public boolean hasUserDefinedScale()
	{
		return userDefinedScale;
	}


	public double getUserDefinedMinimumValue()
	{
		return this.userDefinedMinimumValue;
	}


	public double getUserDefinedIncrement()
	{
		return this.userDefinedIncrement;
	}


	public int getNumItems()
	{
		return numItems;
	}


	public void setNumItems( int numItems )
	{
		this.numItems = numItems;
	}


	public boolean useDollarSigns()
	{
		return useDollarSigns;
	}


	public void setUseDollarSigns( boolean useDollarSigns )
	{
		this.useDollarSigns = useDollarSigns;
	}


	public boolean useCommas()
	{
		return useCommas;
	}


	public void setUseCommas( boolean useCommas )
	{
		this.useCommas = useCommas;
	}


	public boolean usePercentSigns()
	{
		return usePercentSigns;
	}


	public void setUsePercentSigns( boolean usePercentSigns )
	{
		this.usePercentSigns = usePercentSigns;
	}


	public ScaleCalculator getScaleCalculator()
	{
		return scaleCalculator;
	}


	/******************************************************************************************
	 * You do not have to explicitly set a ScaleCalculator implementation as jCharts will
	 * 	create one, but if you do not like the way Scale ranges are created, you could
	 * 	create your own implementation of ScaleCalculator and jCharts will use it!
	 *
	 * @param scaleCalculator
	 ******************************************************************************************/
	public void setScaleCalculator( ScaleCalculator scaleCalculator )
	{
		this.scaleCalculator = scaleCalculator;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( DataAxisProperties.class.getName() );
		//super.toHTML( htmlGenerator );

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
