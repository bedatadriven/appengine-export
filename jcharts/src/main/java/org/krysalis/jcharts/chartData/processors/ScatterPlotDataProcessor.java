/***********************************************************************************************
 * File Info: $Id: ScatterPlotDataProcessor.java,v 1.1 2003/05/17 16:57:50 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.interfaces.IScatterPlotDataSeries;
import org.krysalis.jcharts.chartData.interfaces.IScatterPlotDataSet;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.geom.Point2D;


/*******************************************************************************************
 *
 ********************************************************************************************/
public final class ScatterPlotDataProcessor extends AxisChartDataProcessor
{
	private double yMax;
	private double yMin;


	/******************************************************************************************
	 * Constructor
	 *
	 *******************************************************************************************/
	public ScatterPlotDataProcessor()
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

		IScatterPlotDataSeries iScatterPlotDataSeries = (IScatterPlotDataSeries) axisChart.getIAxisDataSeries();
		this.processDataSet( iScatterPlotDataSeries );

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
	 * @param iScatterPlotDataSeries
	 ******************************************************************************************/
	private void processDataSet( IScatterPlotDataSeries iScatterPlotDataSeries )
	{
		IScatterPlotDataSet iScatterPlotDataSet = (IScatterPlotDataSet) iScatterPlotDataSeries.getIAxisPlotDataSet( ChartType.SCATTER_PLOT );

		Point2D.Double point;

		for( int dataSet = 0; dataSet < iScatterPlotDataSet.getNumberOfDataSets(); dataSet++ )
		{
			for( int index = 0; index < iScatterPlotDataSet.getNumberOfDataItems(); index++ )
			{
				point = iScatterPlotDataSet.getValue( dataSet, index );

				if( point == null )
				{
					continue;
				}

				if( point.getX() > super.getMaxValue() )
				{
					super.setMaxValue( point.getX() );
				}

				if( point.getX() < super.getMinValue() )
				{
					super.setMinValue( point.getX() );
				}

				if( point.getY() > this.getyMax() )
				{
					this.setyMax( point.getY() );
				}

				if( point.getY() < this.getyMin() )
				{
					this.setyMin( point.getY() );
				}
			}
		}

		//System.out.println( this.toString() );
	}


	/******************************************************************************************
	 *
	 ******************************************************************************************/
	public double getyMax()
	{
		return yMax;
	}


	/******************************************************************************************
	 *
	 ******************************************************************************************/
	public void setyMax( double yMax )
	{
		this.yMax = yMax;
	}


	/******************************************************************************************
	 *
	 ******************************************************************************************/
	public double getyMin()
	{
		return yMin;
	}


	/******************************************************************************************
	 *
	 ******************************************************************************************/
	public void setyMin( double yMin )
	{
		this.yMin = yMin;
	}


	/******************************************************************************************
	 *
	 ******************************************************************************************/
	public String toString()
	{
		StringBuffer s= new StringBuffer( 60 );
		s.append( this.getClass().getName() );
		s.append( ":  xMin= " + super.getMinValue() );
		s.append( " xMax= " + super.getMaxValue() );
		s.append( "  yMin= " + this.yMin );
		s.append( " yMax= " + this.yMax );
		return s.toString();
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
