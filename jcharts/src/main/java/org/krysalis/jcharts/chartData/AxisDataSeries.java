/***********************************************************************************************
 * File Info: $Id: AxisDataSeries.java,v 1.2 2004/05/31 16:24:34 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IAxisDataSeries;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.ChartType;
import org.krysalis.jcharts.properties.PropertyException;

import java.util.Iterator;
import java.util.HashMap;


/*********************************************************************************************
 * Collection of all IAxisChartDataSets to display in an AxisChart
 *
 **********************************************************************************************/
public abstract class AxisDataSeries implements IAxisDataSeries, HTMLTestable
{
	private String chartTitle;
	private String xAxisTitle;
	private String yAxisTitle;
	protected HashMap dataSets;

	protected int totalNumberOfDataSets;
	private int sizeOfEachDataSet;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param xAxisTitle if this is NULL, no title will be displayed
	 * @param yAxisTitle if this is NULL, no title will be displayed
	 * @param chartTitle if this is NULL, no title will be displayed
	 *******************************************************************************************/
	public AxisDataSeries( String xAxisTitle, String yAxisTitle, String chartTitle )
	{
		this.xAxisTitle=xAxisTitle;
		this.yAxisTitle=yAxisTitle;
		this.chartTitle=chartTitle;
		dataSets = new HashMap();
	}


	/******************************************************************************************
	 * Returns the x-axis title
	 *
	 * @return String
	 *******************************************************************************************/
	public String getXAxisTitle()
	{
		return this.xAxisTitle;
	}


	/******************************************************************************************
	 * Returns the y-axis title
	 *
	 * @return String
	 *******************************************************************************************/
	public String getYAxisTitle()
	{
		return this.yAxisTitle;
	}


	/******************************************************************************************
	 * Returns the chart title.
	 *
	 * @return String the chart title. If this returns NULL, no title will be displayed.
	 ******************************************************************************************/
	public String getChartTitle()
	{
		return this.chartTitle;
	}


	/******************************************************************************************
	 * Returns the IAxisPlotDataSet for the passed chart type constant. Will return NULL if
	 *  if no data set exists for the passed type.
	 *
	 * @param chartType
	 * @return IAxisPlotDataSet
	 ******************************************************************************************/
	public IAxisPlotDataSet getIAxisPlotDataSet( ChartType chartType )
	{
		return (IAxisPlotDataSet) this.dataSets.get( chartType );
	}


	/******************************************************************************************
	 * Returns an Iterator over a List of IAxisPlotDataSet Objects
	 *
	 * @return Iterator over a List of IAxisPlotDataSet Objects
	 *******************************************************************************************/
	public Iterator getIAxisPlotDataSetIterator()
	{
		return this.dataSets.values().iterator();
	}


	/******************************************************************************************
	 * Returns the total number data dimensions in all of the IAxisChartDataSets contained in
	 *  this collection. For example, if this contains two IAxisChartDataSets and each one
	 *  contains 3 dimensions ( 3 lines and 3 sets of points ), this should return six. This
	 *  provides a means to avoid looping the contents of the series each time i need the value.
	 *
	 * @return int
	 ******************************************************************************************/
	public int getTotalNumberOfDataSets()
	{
		return this.totalNumberOfDataSets;
	}


	/******************************************************************************************
	 * Adds the passed IAxisPlotDataSet to the series
	 *
	 * @param iAxisPlotDataSet
	 ******************************************************************************************/
	public void addIAxisPlotDataSet( IAxisPlotDataSet iAxisPlotDataSet )
	{
		this.dataSets.put( iAxisPlotDataSet.getChartType(), iAxisPlotDataSet );

		//---set the number of dimensions
		totalNumberOfDataSets+=iAxisPlotDataSet.getNumberOfDataSets();

		this.sizeOfEachDataSet= iAxisPlotDataSet.getNumberOfDataItems();
	}


	/****************************************************************************************
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 ***************************************************************************************/
	public void validate() throws ChartDataException, PropertyException
	{
		IAxisPlotDataSet iAxisPlotDataSet;
		Iterator iterator= this.getIAxisPlotDataSetIterator();

		while( iterator.hasNext() )
		{
		 	iAxisPlotDataSet= (IAxisPlotDataSet) iterator.next();
			iAxisPlotDataSet.validate();

			if( iAxisPlotDataSet.getNumberOfDataItems() != this.sizeOfEachDataSet )
			{
				throw new ChartDataException( "All IAxisPlotDataSet implementations must contain an equal number of elements in a Combo Chart" );
			}
		}
	}


	/******************************************************************************************
	 * Returns number of elements in each data set dimension, so i can validate a that all
	 * 	IAxisPlotDataSets on a ComboChart have the same number of elements, as well as the
	 * 	number of Axis Labels equal the number of data elements other charts.
	 *
	 * @return int
	 *****************************************************************************************/
	public int getSizeOfEachDataSet()
	{
		return sizeOfEachDataSet;
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
			htmlGenerator.addTableRow( "chart title", this.chartTitle );
			htmlGenerator.addTableRow( "xAxisTitle", this.xAxisTitle );
			htmlGenerator.addTableRow( "yAxisTitle", this.yAxisTitle );
			htmlGenerator.propertiesTableEnd();
		}
		htmlGenerator.propertiesTableRowEnd();
	}


	/******************************************************************************************
	 * Returns the number of IAxisPlotDataSet Objects in this series
	 *
	 * @return int
	 ******************************************************************************************/
	public int size()
	{
		return this.dataSets.size();
	}

}
