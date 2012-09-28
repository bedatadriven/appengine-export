/***********************************************************************************************
 * File Info: $Id: DataSet.java,v 1.1 2003/05/17 16:58:11 nathaniel_auvil Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil
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

package org.krysalis.jcharts.chartData;


import org.krysalis.jcharts.chartData.interfaces.IDataSet;
import org.krysalis.jcharts.properties.ChartTypeProperties;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import com.google.code.appengine.awt.*;


public class DataSet implements IDataSet, HTMLTestable
{
	private ChartTypeProperties chartTypeProperties;

	protected double[][] data;
	protected String[] legendLabels;
	protected Paint[] paints;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param data
	 * @param legendLabels will be NULL if no Legend.
	 * @param paints
	 * @param chartTypeProperties
	 *******************************************************************************************/
	public DataSet( double[][] data, String[] legendLabels, Paint[] paints, ChartTypeProperties chartTypeProperties )
	{
		this.data = data;
		this.legendLabels = legendLabels;
		this.paints = paints;
		this.chartTypeProperties = chartTypeProperties;
	}


	/******************************************************************************************
	 * Returns the legend label for the passed index. This index corresponds to the DataSet
	 *     for which label you want.
	 *
	 * @param index
	 * @return String
	 *******************************************************************************************/
	public final String getLegendLabel( int index )
	{
		if( this.legendLabels == null )
		{
			return null;
		}
		else
		{
			return this.legendLabels[ index ];
		}
	}


	/*********************************************************************************************
	 * Returns the number of Legend Labels to display.  This may not be the same as the number of
	 *  Data Items, as in AxisCharts, or Data Sets, as in Pie Charts.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getNumberOfLegendLabels()
	{
		if( this.legendLabels == null )
		{
			return 0;
		}
		else
		{
			return this.legendLabels.length;
		}
	}


	/******************************************************************************************
	 * Returns the legend label for the passed index. This index corresponds to the DataSet
	 *     for which label you want.
	 *
	 * @param index
	 * @return Paint
	 *******************************************************************************************/
	public Paint getPaint( int index )
	{
		return this.paints[ index ];
	}


	/******************************************************************************************
	 *
	 *
	 * @return ChartTypeProperties
	 *******************************************************************************************/
	public ChartTypeProperties getChartTypeProperties()
	{
		return this.chartTypeProperties;
	}


	/******************************************************************************************
	 * Returns the number of elements in the data set. All data sets must be of the same length
	 *  so just look at the first one.
	 *
	 * @return int
	 *******************************************************************************************/
	public int getNumberOfDataItems()
	{
		return this.data[ 0 ].length;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( this.getClass().getName() );
		htmlGenerator.addTableRow( "data", HTMLGenerator.arrayToString( this.data ) );

		if( this.legendLabels != null )
		{
			htmlGenerator.addTableRow( "legendLabels", HTMLGenerator.arrayToString( this.legendLabels ) );
		}
		htmlGenerator.addTableRow( "paints", HTMLGenerator.arrayToString( this.paints ) );
		htmlGenerator.propertiesTableEnd();

		htmlGenerator.chartTableRowStart();
		this.chartTypeProperties.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();
	}

}
