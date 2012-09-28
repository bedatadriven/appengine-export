/***********************************************************************************************
 * File Info: $Id: StockChartProperties.java,v 1.2 2003/08/08 08:51:27 nicolaken Exp $
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

package org.krysalis.jcharts.properties;


import com.google.code.appengine.awt.BasicStroke;
import com.google.code.appengine.awt.Stroke;

import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.test.HTMLGenerator;


public final class StockChartProperties extends AxisChartTypeProperties
{
	public static final Stroke DEFAULT_STROKE= new BasicStroke( 1.5f );

	private Stroke hiLowStroke;

	private Stroke openStroke;
	private int openLength;

	private Stroke closeStroke;
	private int closeLength;


	/**********************************************************************************************
	 *
	 *
	 ***********************************************************************************************/
	public StockChartProperties()
	{
		this.hiLowStroke = DEFAULT_STROKE;
		this.openStroke = DEFAULT_STROKE;
		this.openLength = 5;
		this.closeStroke = DEFAULT_STROKE;
		this.closeLength = 5;
	}


	/**********************************************************************************************
	 *
	 * @param hiLowStroke
	 * @param openStroke
	 * @param openPixelLength
	 * @param closeStroke
	 * @param closePixelLength
	 ***********************************************************************************************/
	public StockChartProperties( Stroke hiLowStroke,
	                             Stroke openStroke,
	                             int openPixelLength,
	                             Stroke closeStroke,
	                             int closePixelLength )
	{
		this.hiLowStroke = hiLowStroke;
		this.openStroke = openStroke;
		this.openLength = openPixelLength;
		this.closeStroke = closeStroke;
		this.closeLength = closePixelLength;
	}


	/**********************************************************************************************
	 *
	 * @return Stroke
	 ***********************************************************************************************/
	public Stroke getHiLowStroke()
	{
		return this.hiLowStroke;
	}


	/**********************************************************************************************
	 *
	 * @param stroke
	 ***********************************************************************************************/
	public void setHiLowStroke( Stroke stroke )
	{
		this.hiLowStroke = stroke;
	}


	/**********************************************************************************************
	 *
	 * @return Stroke
	 ***********************************************************************************************/
	public Stroke getOpenStroke()
	{
		return this.openStroke;
	}


	/**********************************************************************************************
	 *
	 * @param stroke
	 ***********************************************************************************************/
	public void setOpenStroke( Stroke stroke )
	{
		this.openStroke = stroke;
	}


	/**********************************************************************************************
	 *
	 * @return int
	 ***********************************************************************************************/
	public int getOpenPixelLength()
	{
		return this.openLength;
	}


	/**********************************************************************************************
	 *
	 * @param pixelLength
	 ***********************************************************************************************/
	public void setOpenPixelLength( int pixelLength )
	{
		this.openLength = pixelLength;
	}


	/**********************************************************************************************
	 *
	 * @return Stroke
	 ***********************************************************************************************/
	public Stroke getCloseStroke()
	{
		return this.closeStroke;
	}


	/**********************************************************************************************
	 *
	 * @param stroke
	 ***********************************************************************************************/
	public void setCloseStroke( Stroke stroke )
	{
		this.closeStroke = stroke;
	}


	/**********************************************************************************************
	 *
	 * @return int
	 ***********************************************************************************************/
	public int getClosePixelLength()
	{
		return this.closeLength;
	}


	/**********************************************************************************************
	 *
	 * @param pixelLength
	 ***********************************************************************************************/
	public void setClosePixelLength( int pixelLength )
	{
		this.closeLength = pixelLength;
	}


	/******************************************************************************************
	 * Validates the properties.
	 *
	 * @param iAxisPlotDataSet
	 * @throws PropertyException
	 *****************************************************************************************/
	public void validate( IAxisPlotDataSet iAxisPlotDataSet ) throws PropertyException
	{
		//AxisChart axisChart= (AxisChart) chart;
      //IAxisPlotDataSet iAxisPlotDataSet= axisChart.getIDataSeries().getIAxisPlotDataSet( ChartType.LINE );

	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "StockChartProperties" );
		htmlGenerator.addTableRow( "HiLow Stroke", this.hiLowStroke );
		htmlGenerator.addTableRow( "Open Stroke", this.openStroke );
		htmlGenerator.addTableRow( "Open Length", Integer.toString( this.openLength ) );
		htmlGenerator.addTableRow( "Close Stroke", this.closeStroke );
		htmlGenerator.addTableRow( "Close Length", Integer.toString( this.closeLength ) );
		htmlGenerator.propertiesTableEnd();
	}


}
