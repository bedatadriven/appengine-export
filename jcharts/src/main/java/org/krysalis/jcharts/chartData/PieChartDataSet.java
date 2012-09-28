/***********************************************************************************************
 * File Info: $Id: PieChartDataSet.java,v 1.1 2003/05/17 16:58:11 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IPieChartDataSet;
import org.krysalis.jcharts.properties.PieChart2DProperties;

import com.google.code.appengine.awt.*;


/*******************************************************************************************
 * Implementation of the IPieChartDataSet Interface for use with the PieChart2D Object.
 *
 *******************************************************************************************/
final public class PieChartDataSet extends DataSet implements IPieChartDataSet
{
	private String chartTitle;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param chartTitle if the title is NULL, no title will be drawn
	 * @param data
	 * @param legendLabels
	 * @param paints
	 * @param pieChart2DProperties
	 * @throws ChartDataException if fails a limited validation check
	 *******************************************************************************************/
	public PieChartDataSet( String chartTitle,
									double[] data,
									String[] legendLabels,
									Paint[] paints,
									PieChart2DProperties pieChart2DProperties ) throws ChartDataException
	{
		super( new double[][]{data}, legendLabels, paints, pieChart2DProperties );

		this.chartTitle=chartTitle;

		this.validateData( data, legendLabels, paints );
	}


	/*******************************************************************************************
	 * Perform some limited validation of the structure of the passed data.  This is useful for
	 *  development.
	 *
	 * @param data
	 * @param legendLabels
	 * @param paints
	 * @throws ChartDataException
	 *******************************************************************************************/
	private void validateData( double[] data, String[] legendLabels, Paint[] paints ) throws ChartDataException
	{
		if( legendLabels != null && ( data.length != legendLabels.length ) )
		{
			throw new ChartDataException( "There is not an one to one mapping of 'legend labels' to 'data items'." );
		}

		if( data.length != paints.length )
		{
			throw new ChartDataException( "There is not an one to one mapping of 'Paint' Implementations to 'data items'." );
		}
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
	 * Returns the value in the data set at the specified position.
	 *
	 * @param index
	 * @return double
	 * @throws ArrayIndexOutOfBoundsException
	 *******************************************************************************************/
	public double getValue( int index ) throws ArrayIndexOutOfBoundsException
	{
		return super.data[ 0 ][ index ];
	}
}
