/***********************************************************************************************
 * File Info: $Id: DataSeries.java,v 1.1 2003/05/17 16:58:11 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IDataSeries;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import java.util.Iterator;


/*********************************************************************************************
 * Collection of all IAxisPlotDataSets to display in an AxisChart
 *
 **********************************************************************************************/
public class DataSeries extends AxisDataSeries implements IDataSeries, HTMLTestable
{
	private String[] axisLabels;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param axisLabels
	 * @param xAxisTitle if this is NULL, no title will be displayed
	 * @param yAxisTitle if this is NULL, no title will be displayed
	 * @param chartTitle if this is NULL, no title will be displayed
	 *******************************************************************************************/
	public DataSeries( String[] axisLabels, String xAxisTitle, String yAxisTitle, String chartTitle )
	{
		super( xAxisTitle, yAxisTitle, chartTitle );

		this.axisLabels = axisLabels;
	}


	/******************************************************************************************
	 * Returns the x-axis label corresponding to the passed index
	 *
	 * @param index
	 * @return String
	 *******************************************************************************************/
	public String getAxisLabel( int index )
	{
		return this.axisLabels[ index ];
	}


	/******************************************************************************************
	 * Returns the number of labels on the x-axis
	 *
	 * @return int
	 ******************************************************************************************/
	public int getNumberOfAxisLabels()
	{
		if( this.axisLabels != null )
		{
			return this.axisLabels.length;
		}
		else
		{
			return 0;
		}
	}


	/****************************************************************************************
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 ***************************************************************************************/
	public void validate() throws ChartDataException, PropertyException
	{
		super.validate();

		if( this.axisLabels != null && this.axisLabels.length != super.getSizeOfEachDataSet() )
		{
			throw new ChartDataException( "The size of the Axis Labels Array does not match the number of data elements to be plotted." );
		}
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableRowStart();
		{
			htmlGenerator.propertiesTableStart( this.getClass().getName() );
			htmlGenerator.addTableRow( "xAxisLabels", HTMLGenerator.arrayToString( this.axisLabels ) );
			htmlGenerator.addTableRow( "totalNumberOfDataSets", Integer.toString( totalNumberOfDataSets ) );
			htmlGenerator.propertiesTableEnd();
		}
		htmlGenerator.propertiesTableRowEnd();


		htmlGenerator.propertiesTableRowStart();
		{
			//---loop the data sets
			Iterator iterator = this.getIAxisPlotDataSetIterator();
			Object object;
			while( iterator.hasNext() )
			{
				object = iterator.next();
				if( object instanceof HTMLTestable )
				{
					((HTMLTestable) object).toHTML( htmlGenerator );
				}
			}
		}
		htmlGenerator.propertiesTableRowEnd();
	}
}
