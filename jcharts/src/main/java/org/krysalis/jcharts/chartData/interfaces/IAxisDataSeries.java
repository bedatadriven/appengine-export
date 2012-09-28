/***********************************************************************************************
 * File Info: $Id: IAxisDataSeries.java,v 1.2 2003/08/28 00:56:34 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.chartData.interfaces;


import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.types.ChartType;

import java.util.Iterator;


public interface IAxisDataSeries extends IData
{

	/******************************************************************************************
	 * Returns the x-axis title.
	 *
	 * @return String the x-axis title. If this returns NULL, no title will be displayed.
	 ******************************************************************************************/
	public String getXAxisTitle();


	/******************************************************************************************
	 * Returns the y-axis title.
	 *
	 * @return String the y-axis title. If this returns NULL, no title will be displayed.
	 ******************************************************************************************/
	public String getYAxisTitle();


	/******************************************************************************************
	 * Returns the chart title.
	 *
	 * @return String the chart title. If this returns NULL, no title will be displayed.
	 ******************************************************************************************/
	public String getChartTitle();


	/*********************************************************************************************
	 * Validates the DataSeries implementation.  This will only get called if the ChartProperties
	 * 	flag to validate is true.
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 * @since 0.7.0
	 * @see org.krysalis.jcharts.properties.ChartProperties#setValidate( boolean )
	 *********************************************************************************************/
	public void validate() throws ChartDataException, PropertyException;


	public Iterator getIAxisPlotDataSetIterator();


	public void addIAxisPlotDataSet( IAxisPlotDataSet iAxisPlotDataSet );


	public IAxisPlotDataSet getIAxisPlotDataSet( ChartType chartType );


	/******************************************************************************************
	 * Returns the total number data dimensions in all of the IAxisChartDataSets contained in
	 *  this collection. For example, if this contains two IAxisChartDataSets and each one
	 *  contains 3 dimensions ( 3 lines and 3 sets of points ), this should return six. This
	 *  provides a means to avoid looping the contents of the series each time i need the value.
	 *
	 * @return int
	 ******************************************************************************************/
	public int getTotalNumberOfDataSets();


	/******************************************************************************************
	 * Returns the number of IAxisPlotDataSet Objects in this series
	 *
	 * @return int
	 ******************************************************************************************/
	int size();

}
