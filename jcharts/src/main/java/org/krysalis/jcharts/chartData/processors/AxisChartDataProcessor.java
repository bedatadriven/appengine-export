/***********************************************************************************************
 * File Info: $Id: AxisChartDataProcessor.java,v 1.2 2003/08/08 08:51:27 nicolaken Exp $
 * Copyright (C) 2000
 * Author: Nathaniel G. Auvil
 * Contributor(s):
 *
 * Copyright 2002 (C) Nathaniel G. Auvil. All Rights Reserved.
 *
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "jCharts" or "Nathaniel G. Auvil" must not be used to
 * 	  endorse or promote products derived from this Software without
 * 	  prior written permission of Nathaniel G. Auvil.  For written
 *    permission, please contact nathaniel_auvil@users.sourceforge.net
 *
 * 4. Products derived from this Software may not be called "jCharts"
 *    nor may "jCharts" appear in their names without prior written
 *    permission of Nathaniel G. Auvil. jCharts is a registered
 *    trademark of Nathaniel G. Auvil.
 *
 * 5. Due credit should be given to the jCharts Project (http://jcharts.sourceforge.net/).
 *
 * THIS SOFTWARE IS PROVIDED BY Nathaniel G. Auvil AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * jCharts OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 ************************************************************************************************/

package org.krysalis.jcharts.chartData.processors;


import com.google.code.appengine.awt.font.FontRenderContext;
import java.util.Iterator;

import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.interfaces.IAxisChartDataSet;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.chartData.interfaces.IDataSeries;
import org.krysalis.jcharts.chartData.interfaces.IStockChartDataSet;
import org.krysalis.jcharts.properties.DataAxisProperties;
import org.krysalis.jcharts.types.ChartType;


/*******************************************************************************************
 *
 ********************************************************************************************/
public class AxisChartDataProcessor
{
	private double max;
	private double min;

	//private TextLayout titleTextLayout;


	//---need this so know how many items are on the 'label' axis.
	private int numberOfElementsInADataSet;



	/******************************************************************************************
	 * Constructor
	 *
	 *******************************************************************************************/
	public AxisChartDataProcessor()
	{

	}



	/******************************************************************************************
	 * Method to perform all chart data processing.
	 *
	 * @param axisChart
	 ******************************************************************************************/
	public void processData( AxisChart axisChart, FontRenderContext fontRenderContext )
	{
//todo would it make sense to do this and do the axis titles?
		/*
	   if( axisChart.getIDataSeries().getChartTitle() != null )
		{
			this.titleTextLayout= new TextLayout(  axisChart.getIDataSeries().getChartTitle(),
																axisChart.getChartProperties().getTitleFont(),
																fontRenderContext );
		}
		*/

		DataAxisProperties dataAxisProperties;
		if( axisChart.getAxisProperties().isPlotHorizontal() )
		{
			dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getXAxisProperties();
		}
		else
		{
			dataAxisProperties= (DataAxisProperties) axisChart.getAxisProperties().getYAxisProperties();
		}


		//---if there is a user defined scale, there is no reason to process the data.
		if( ! dataAxisProperties.hasUserDefinedScale() )
		{
			this.processDataSet( (IDataSeries) axisChart.getIAxisDataSeries() );
		}


		//---need to set the number of items on the scale in case there are no labels displayed
		Iterator iterator = axisChart.getIAxisDataSeries().getIAxisPlotDataSetIterator();
		IAxisPlotDataSet iAxisPlotDataSet = ( IAxisPlotDataSet ) iterator.next();
		this.numberOfElementsInADataSet= iAxisPlotDataSet.getNumberOfDataItems();


//todo does it make sense to do the legend label processing here?
		/*
		if( axisChart.hasLegend() )
		{
			//this.lengendLabelProcessor= new TextProcessor();
		  // this.lengendLabelProcessor
		}
		*/
	}


	/******************************************************************************************
	 * Processes the numeric values in the chart data. If there is a user defined scale
	 *  there is no need to call this.
	 *
	 * @param iDataSeries
	 ******************************************************************************************/
	private void processDataSet( IDataSeries iDataSeries )
	{
		IAxisPlotDataSet iAxisPlotDataSet;
		Iterator iterator = iDataSeries.getIAxisPlotDataSetIterator();

		//LOOP
		while( iterator.hasNext() )
		{
			iAxisPlotDataSet = ( IAxisPlotDataSet ) iterator.next();

			if( iAxisPlotDataSet.getChartType().isStacked() )
			{
				//---StockChartDataSet is NEVER stacked!!!!
				StackedDataProcessor.processData( ( IAxisChartDataSet ) iAxisPlotDataSet, this );
			}
			else
			{
				//---stock charts dont fit well here as the data comes in structured.
				//---in this case only care about the high and low; no need to search close, open, volume
				if( iAxisPlotDataSet.getChartType().equals( ChartType.STOCK ) )
				{
					StockDataProcessor.processData( ( IStockChartDataSet ) iAxisPlotDataSet, this );
				}
				else
				{
					NonStackedDataProcessor.processData( ( IAxisChartDataSet ) iAxisPlotDataSet, this );
				}
			}
		}
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	void setMaxValue( double max )
	{
		this.max = max;
	}


	/******************************************************************************************
	 *
	 *
	 *
	 ******************************************************************************************/
	public double getMaxValue()
	{
		return this.max;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	void setMinValue( double min )
	{
		this.min = min;
	}


	/******************************************************************************************
	 *
	 *
	 *
	 ******************************************************************************************/
	public double getMinValue()
	{
		return this.min;
	}


	public int getNumberOfElementsInADataSet()
	{
		return numberOfElementsInADataSet;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************
	 public void toHTML( HTMLGenerator htmlGenerator )
	 {
	 super.toHTML( htmlGenerator );

	 String name= this.getClass().getSuperclass().getName() + "->";

	 //---calling on instance of YAxis or XAxis
	 Field[] fields= this.getClass().getSuperclass().getDeclaredFields();
	 for( int i=0; i< fields.length; i++ )
	 {
	 htmlGenerator.addField( name + fields[ i ].getName(), fields[ i ].get( this ) );
	 }
	 }
	 */
}
