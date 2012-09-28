/***********************************************************************************************
 * File Info: $Id: PieChartDataProcessor.java,v 1.1 2003/05/17 16:57:50 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.chartData.processors;


import org.krysalis.jcharts.chartData.interfaces.IPieChartDataSet;


/*******************************************************************************************
 * Utility class to process the PieChartDataSet
 *
 *******************************************************************************************/
final public class PieChartDataProcessor
{
	private IPieChartDataSet iPieChartDataSet;

	private double sumOfData = 0;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param iPieChartDataSet
	 *******************************************************************************************/
	public PieChartDataProcessor( IPieChartDataSet iPieChartDataSet )
	{
		this.iPieChartDataSet = iPieChartDataSet;
	}


	/*******************************************************************************************
	 * This method should do a single pass through the data set and calculate all needed values,
	 *  such as: min, max, sum, etc... so that we can do this in one pass through the data.
	 *  Rather than once for each.
	 *
	 ********************************************************************************************/
	public void processData()
	{
		int size = this.iPieChartDataSet.getNumberOfDataItems();
		for( int i = 0; i < size; i++ )
		{
			this.sumOfData += this.iPieChartDataSet.getValue( i );
		}
	}


	/******************************************************************************************
	 * Returns percentage of pie(360 degrees) data point at specified index.
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************/
	public double getPercentageOfPie( int index )
	{
		return (this.iPieChartDataSet.getValue( index ) / this.sumOfData) * 360;
	}


}
