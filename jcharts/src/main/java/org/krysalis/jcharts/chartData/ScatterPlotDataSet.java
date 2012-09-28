/***********************************************************************************************
 * File Info: $Id: ScatterPlotDataSet.java,v 1.2 2004/05/31 16:24:34 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IScatterPlotDataSet;
import org.krysalis.jcharts.properties.ChartTypeProperties;
import org.krysalis.jcharts.properties.ScatterPlotProperties;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.types.ChartType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Point2D;
import java.util.ArrayList;


public class ScatterPlotDataSet implements IScatterPlotDataSet, HTMLTestable
{
	//---use this to clone for better performance than creating new
	private static Point2D.Double POINT= new Point2D.Double();

	private ArrayList data;
	private ArrayList legendLabels;
	private ArrayList paints;
	private ScatterPlotProperties scatterPlotProperties;

	private int numDataItems= -1;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param scatterPlotProperties
	 *******************************************************************************************/
	public ScatterPlotDataSet( ScatterPlotProperties scatterPlotProperties )
	{
		this.data = new ArrayList();
		this.legendLabels = new ArrayList();
		this.paints = new ArrayList();
		this.scatterPlotProperties = scatterPlotProperties;
	}


	/******************************************************************************************
	 * Returns the type constant that this data set should be plotted as.
	 *
	 * @return ChartType
	 *******************************************************************************************/
	public final ChartType getChartType()
	{
		return ChartType.SCATTER_PLOT;
	}


   /****************************************************************************************
	 *
	 * @param points
	 * @param paint
	 * @param legendLabel
	 ***************************************************************************************/
	public void addDataPoints( Point2D.Double[] points, Paint paint, String legendLabel )
	{
      this.data.add( points );
		this.paints.add( paint );
		this.legendLabels.add( legendLabel );

		this.numDataItems= points.length;
	}


	/************************************************************************************************
	 * Performs a limited validation of data.
	 *
	 * @throws ChartDataException
	 *************************************************************************************************/
	public void validate() throws ChartDataException
	{
		Point2D.Double[] points;
		for( int i=0; i < this.data.size(); i++ )
		{
			points= (Point2D.Double[]) this.data.get( i );
			if( points.length != this.numDataItems )
			{
				throw new ChartDataException( "All Arrays of Point Objects must have the same length." );
			}

			if( this.paints.get( i ) == null )
			{
				throw new ChartDataException( "The 'Paint' implementation can not be NULL." );
			}
		}

		if( this.scatterPlotProperties == null )
		{
			throw new ChartDataException( "ScatterPlotProperties can not be NULL." );
		}
	}


	/******************************************************************************************
	 * Returns the value in the data set at the specified position.
	 *
	 * @param dataset
	 * @param index
	 * @return Point.Double
	 *******************************************************************************************/
	public Point2D.Double getValue( int dataset, int index )
	{
		return ( (Point2D.Double[]) this.data.get( dataset ) )[ index ];
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
			return (String) this.legendLabels.get( index );
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
			return this.legendLabels.size();
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
		return (Paint) this.paints.get( index );
	}


	/******************************************************************************************
	 *
	 * @return ChartTypeProperties
	 *******************************************************************************************/
	public ChartTypeProperties getChartTypeProperties()
	{
		return this.scatterPlotProperties;
	}


	/******************************************************************************************
	 * Returns the number of elements in the data set.
	 *
	 * @return int
	 *******************************************************************************************/
	public int getNumberOfDataSets()
	{
		return this.data.size();
	}


	/******************************************************************************************
	 * Returns the number of elements in the data set.
	 *
	 * @return int
	 *******************************************************************************************/
	public int getNumberOfDataItems()
	{
		return this.numDataItems;
	}


	/*****************************************************************************************
	 * Take advantage of the face Cloning performs better than creating new for highly used
	 * 	Objects.
	 *
	 * @return Point2D.Double
	 ****************************************************************************************/
	public static final Point2D.Double createPoint2DDouble()
	{
		return (Point2D.Double) POINT.clone();
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( this.getClass().getName() );
		htmlGenerator.addTableRow( "data", HTMLGenerator.arrayToString( this.data.toArray( new Point2D.Double[ this.legendLabels.size() ] ) ) );

		if( this.legendLabels != null )
		{
			htmlGenerator.addTableRow( "legendLabels", HTMLGenerator.arrayToString( this.legendLabels.toArray( new String[ this.legendLabels.size() ] ) ) );
		}
		htmlGenerator.addTableRow( "paints", HTMLGenerator.arrayToString( this.paints.toArray( new Paint[ this.paints.size() ] ) ) );
		htmlGenerator.propertiesTableEnd();

		htmlGenerator.chartTableRowStart();
		this.scatterPlotProperties.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();
	}

}
