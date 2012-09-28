/***********************************************************************************************
 * File Info: $Id: AxisChartDataSet.java,v 1.1 2003/05/17 16:58:10 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.*;


public final class AxisChartDataSet extends DataSet implements IAxisChartDataSet, HTMLTestable
{
	private ChartType chartType;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param data the data sets to be displayed in the chart.
	 * @param legendLabels if this is: NULL there will be no Legend. Otherwise, there must be an
	 *           one to one mapping of labels to data sets.
	 * @param paints paints to use for the data sets. There must be an one to one mapping of
	 *           labels to data sets.
	 * @param chartType constant defining how this data should be rendered
	 * @param chartTypeProperties properties Object specific to the type of chart you are rendering.
	 * @throws ChartDataException if data is not in correct form.
	 *******************************************************************************************/
	public AxisChartDataSet( double[][] data,
									 String[] legendLabels,
									 Paint[] paints,
									 ChartType chartType,
									 ChartTypeProperties chartTypeProperties ) throws ChartDataException
	{
		super( data, legendLabels, paints, chartTypeProperties );
		this.chartType=chartType;
	}


	/************************************************************************************************
	 * Performs a limited validation of data. This is static and not called by the rendering engine
	 *	so as to avoid the, albeit small, cost of validation checking during deployment; this is viewed
	 * 	more so as a development time test.
	 *
	 * @throws ChartDataException
	 *************************************************************************************************/
	public void validate() throws ChartDataException, PropertyException
	{
		if( ( super.legendLabels != null ) && ( super.data.length != super.legendLabels.length ) )
		{
			throw new ChartDataException( "There is not an one to one mapping of 'legend labels' to 'data sets'." );
		}

		if( super.data.length != super.paints.length )
		{
			throw new ChartDataException( "There is not an one to one mapping of 'Paint' implementations to 'data sets'." );
		}

		( (AxisChartTypeProperties) super.getChartTypeProperties() ).validate( this );
	}


	/******************************************************************************************
	 * Returns the value in the data set at the specified position.
	 *
	 * @param dataset
	 * @param index
	 * @return double
	 * @throws ArrayIndexOutOfBoundsException
	 *******************************************************************************************/
	public final double getValue( int dataset, int index ) throws ArrayIndexOutOfBoundsException
	{
		return super.data[ dataset ][ index ];
	}


	/******************************************************************************************
	 * Returns the type constant that this data set should be plotted as.
	 *
	 * @return ChartType
	 * @see ChartType
	 *******************************************************************************************/
	public final ChartType getChartType()
	{
		return this.chartType;
	}


	/******************************************************************************************
	 * Returns the number of IAxisChartDataSet Objects in this series
	 *
	 * @return int
	 ******************************************************************************************/
	public final int getNumberOfDataSets()
	{
		return this.data.length;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		super.toHTML( htmlGenerator );

/*
		//String name= this.getClass().getSuperclass().getName() + "->";

		Field[] fields= this.getClass().getDeclaredFields();
		for( int i=0; i< fields.length; i++ )
		{
			htmlGenerator.addTableRow( fields[ i ].getName(), fields[ i ].get( this ) );
		}
*/
	}


}
