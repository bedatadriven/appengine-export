/***********************************************************************************************
 * File Info: $Id: StockChartDataSet.java,v 1.1 2003/05/17 16:58:11 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IStockChartDataSet;
import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.ChartType;
import org.krysalis.jcharts.types.StockChartDataType;

import com.google.code.appengine.awt.*;


public class StockChartDataSet implements IStockChartDataSet, HTMLTestable
{
	private ChartType chartType=ChartType.STOCK;

	private double[] high;
	private double[] low;
	private double[] open;
	private double[] close;

	//---keep this value for reference sake. Start with high and low
	private int numberOfDataSets=2;

	private String[] legendLabels;
	private Paint[] paints;
	private StockChartProperties stockChartProperties;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param high
	 * @param highLegendLabel
	 * @param low
	 * @param lowLegendLabel
	 * @param stockChartProperties properties Object specific to the type of chart you are rendering.
	 * @throws ChartDataException performs a limited validation of the data
	 *******************************************************************************************/
	public StockChartDataSet( double[] high,
									  String highLegendLabel,
									  double[] low,
									  String lowLegendLabel,
									  Paint highLowPaint,
									  StockChartProperties stockChartProperties ) throws ChartDataException
	{
		this.high=high;
		this.low=low;

		this.legendLabels=new String[ 5 ];
		this.legendLabels[ StockChartDataType.HIGH.getInt() ]=highLegendLabel;
		this.legendLabels[ StockChartDataType.LOW.getInt() ]=lowLegendLabel;

		this.paints=new Paint[ 5 ];
		this.paints[ StockChartDataType.HIGH.getInt() ]=highLowPaint;
		this.paints[ StockChartDataType.LOW.getInt() ]=highLowPaint;

		this.stockChartProperties=stockChartProperties;
	}


	/************************************************************************************************
	 * Performs a limited validation of data passed to Constructor.
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 *************************************************************************************************/
	public void validate() throws ChartDataException, PropertyException
	{
		if( high == null || low == null )
		{
			throw new ChartDataException( "The Hi/Low values can not be NULL." );
		}

		if( high.length != low.length )
		{
			throw new ChartDataException( "The Hi/Low Arrays must have equal length." );
		}

		if( this.paints[ StockChartDataType.HIGH.getInt() ] == null )
		{
			throw new ChartDataException( "The Hi/Low Paint implementation can not be NULL." );
		}

		this.stockChartProperties.validate( this );
	}


	/******************************************************************************************
	 * Returns the legend label for the passed index. This index corresponds to the DataSet
	 *     for which label you want.
	 *
	 * @param index
	 * @return String
	 *******************************************************************************************/
	public String getLegendLabel( int index )
	{
		return this.legendLabels[ index ];
	}


	/*********************************************************************************************
	 * Returns the number of Legend Labels to display.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getNumberOfLegendLabels()
	{
		return this.legendLabels.length;
	}


	/******************************************************************************************
	 * Returns the number of elements in the data set. All data sets must be of the same length
	 *  so just look at the first one.
	 *
	 * @return int
	 *******************************************************************************************/
	public int getNumberOfDataItems()
	{
		//---always have a high and a low
		return this.high.length;
	}


	/***************************************************************************************************
	 * Sets the 'Close' values
	 *
	 * @param data
	 * @param legendLabel
	 * @param paint
	 **************************************************************************************************/
	public void setCloseValues( double[] data, String legendLabel, Paint paint )
	{
		this.numberOfDataSets++;
		this.close=data;
		this.legendLabels[ StockChartDataType.CLOSE.getInt() ]=legendLabel;
		this.paints[ StockChartDataType.CLOSE.getInt() ]=paint;
	}


	/***************************************************************************************************
	 * Sets the 'Open' values
	 *
	 * @param data
	 * @param legendLabel
	 * @param paint
	 **************************************************************************************************/
	public void setOpenValues( double[] data, String legendLabel, Paint paint )
	{
		this.numberOfDataSets++;
		this.open=data;
		this.legendLabels[ StockChartDataType.OPEN.getInt() ]=legendLabel;
		this.paints[ StockChartDataType.OPEN.getInt() ]=paint;
	}


	/*********************************************************************************************
	 * Sets the 'Volume' values
	 *
	 * @param data[]
	 * @param legendLabel
	 * @param paint
	 *********************************************************************************************
	 public void setVolumeValues( double[] data, String legendLabel, Paint paint )
	 {
	 this.numberOfDataSets++;
	 this.volume= data;
	 this.legendLabels[ StockChartDataType.VOLUME.getInt() ]= legendLabel;
	 this.paints[ StockChartDataType.VOLUME.getInt() ]= paint;
	 }
	 */

	/******************************************************************************************
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************/
	public double getHighValue( int index )
	{
		return this.high[ index ];
	}


	/******************************************************************************************
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************/
	public double getLowValue( int index )
	{
		return this.low[ index ];
	}


	/******************************************************************************************
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************/
	public double getCloseValue( int index )
	{
		return this.close[ index ];
	}


	/******************************************************************************************
	 *
	 * @return boolean
	 *******************************************************************************************/
	public boolean hasCloseValues()
	{
		return ( this.close != null );
	}


	/******************************************************************************************
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************/
	public double getOpenValue( int index )
	{
		return this.open[ index ];
	}


	/******************************************************************************************
	 *
	 * @return boolean
	 *******************************************************************************************/
	public boolean hasOpenValues()
	{
		return ( this.open != null );
	}


	/******************************************************************************************
	 *
	 * @param index
	 * @return double
	 *******************************************************************************************
	 public double getVolumeValue( int index )
	 {
	 return this.volume[ index ];
	 }
	 */

	/******************************************************************************************
	 *
	 * @return boolean
	 *******************************************************************************************
	 public boolean hasVolumeValues()
	 {
	 return ( this.volume != null );
	 }
	 */

	/******************************************************************************************
	 * Returns the type constant that this data set should be plotted as.
	 *
	 * @return ChartType
	 * @see ChartType
	 *******************************************************************************************/
	public ChartType getChartType()
	{
		return this.chartType;
	}


	/******************************************************************************************
	 * Returns the chart specific properties
	 *
	 * @return ChartTypeProperties
	 *******************************************************************************************/
	public ChartTypeProperties getChartTypeProperties()
	{
		return this.stockChartProperties;
	}


	/******************************************************************************************
	 * Returns the number of IAxisChartDataSet Objects in this series
	 *
	 * @return int
	 ******************************************************************************************/
	public int getNumberOfDataSets()
	{
		return this.numberOfDataSets;
	}


	/******************************************************************************************
	 * Returns the number of IAxisChartDataSet Objects in this series
	 *
	 * @return int
	 ******************************************************************************************/
	public Paint getPaint( int index )
	{
		return this.paints[ index ];
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		//super.toHTML( htmlGenerator );

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
