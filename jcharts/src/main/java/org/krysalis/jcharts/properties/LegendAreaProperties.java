/***********************************************************************************************
 * File Info: $Id: LegendAreaProperties.java,v 1.2 2003/06/01 05:23:05 staringindablu Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil
 * Contributor(s): Sandor Dornbush
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


import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;


abstract public class LegendAreaProperties extends AreaProperties implements HTMLTestable
{
	public static final int COLUMNS_AS_MANY_AS_NEEDED = 0;

	//---This will wrap the legend to fit the image size.
	public static final int COLUMNS_FIT_TO_IMAGE = -1;

	public static final int BOTTOM = 0;
	public static final int RIGHT = 1;
   public static final int LEFT = 2;
	public static final int TOP = 3;


	private int numColumns = COLUMNS_AS_MANY_AS_NEEDED;

	//---vertical padding between labels
	private int rowPadding = 5;

	//---horizontal padding between labels
	private int columnPadding = 10;

	//---padding between icon and Label
	private int iconPadding = 5;

	//---distance from edge of chart to Legend
	private int chartPadding = 5;
	
	//---for LineCharts, length of line around point shapes as part of the icon in the legend. 
	//---for example, ---o---. here iconLineStrokeLength is the length of --- on either sides of 'o'
	private int iconLineStrokeLength = 20;

	//---where Legend should be drawn in relation to the chart.
	private int placement = BOTTOM;


	/*********************************************************************************************
	 * Constructor for those desiring auto-calculation of the Legend width based on the number
	 *  of columns. All Labels are on a single row as default number of rows is: COLUMNS_AS_MANY_AS_NEEDED
	 *
	 **********************************************************************************************/
	public LegendAreaProperties()
	{
		super();
	}


	/*********************************************************************************************
	 * Returns where the Legend should be drawn in relation to the Chart.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getPlacement()
	{
		return this.placement;
	}


	/*********************************************************************************************
	 * Sets where the Legend should be drawn in relation to the Chart.
	 *
	 * @param placementConstant
	 **********************************************************************************************/
	public void setPlacement( int placementConstant )
	{
		this.placement = placementConstant;
	}


	/*********************************************************************************************
	 * Returns the padding between chart plot and the Legend.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getChartPadding()
	{
		return this.chartPadding;
	}


	/*********************************************************************************************
	 * Sets the padding between chart plot and the Legend.
	 *
	 * @param chartPadding
	 **********************************************************************************************/
	public void setChartPadding( int chartPadding )
	{
		this.chartPadding = chartPadding;
	}


	/*********************************************************************************************
	 * Returns the padding between label columns.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getColumnPadding()
	{
		return this.columnPadding;
	}


	/*********************************************************************************************
	 * Returns the padding between labels in each row.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getRowPadding()
	{
		return this.rowPadding;
	}


	/*********************************************************************************************
	 * Sets the number of text columns the legend should display.
	 *
	 * @param numColumns
	 **********************************************************************************************/
	public void setNumColumns( int numColumns )
	{
		this.numColumns = numColumns;
	}


	/*********************************************************************************************
	 * Gets the number of text columns the legend should display.
	 *
	 * @return int
	 **********************************************************************************************/
	public int getNumColumns()
	{
		return this.numColumns;
	}


	/*********************************************************************************************
	 * Returns the padding between the icon and the Label
	 *
	 * @return int
	 **********************************************************************************************/
	public int getIconPadding()
	{
		return this.iconPadding;
	}


	/*********************************************************************************************
	 * Sets the padding between labels in each row, in pixels.
	 *
	 * @param rowPadding
	 **********************************************************************************************/
	public void setRowPadding( int rowPadding )
	{
		this.rowPadding = rowPadding;
	}


	/*********************************************************************************************
	 * Sets the padding between label columns, in pixels.
	 *
	 * @param columnPadding
	 **********************************************************************************************/
	public void setColumnPadding( int columnPadding )
	{
		this.columnPadding = columnPadding;
	}


	/*********************************************************************************************
	 * Sets the padding between the icon and the Label, in pixels.
	 *
	 * @param iconPadding
	 **********************************************************************************************/
	public void setIconPadding( int iconPadding )
	{
		this.iconPadding = iconPadding;
	}


	/**
	 * Returns the iconLineStrokeLength.
	 * @return int
	 */
	public int getIconLineStrokeLength() {
		return iconLineStrokeLength;
	}

	/**
	 * Sets the iconLineStrokeLength.
	 * @param iconLineStrokeLength The iconLineStrokeLength to set
	 */
	public void setIconLineStrokeLength(int iconLineStrokeLength) {
		this.iconLineStrokeLength = iconLineStrokeLength;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		super.toHTML( htmlGenerator );
		htmlGenerator.addTableRow( "LegendAreaProperties->Num Columns", Integer.toString( this.getNumColumns() ) );
		htmlGenerator.addTableRow( "LegendAreaProperties->Row Padding", Integer.toString( this.getRowPadding() ) );
		htmlGenerator.addTableRow( "LegendAreaProperties->Icon Padding", Integer.toString( this.getIconPadding() ) );
		htmlGenerator.addTableRow( "LegendAreaProperties->Chart Padding", Integer.toString( this.getChartPadding() ) );
		htmlGenerator.addTableRow( "LegendAreaProperties->Column Padding", Integer.toString( this.getColumnPadding() ) );
		htmlGenerator.addTableRow( "LegendAreaProperties->Placement", Integer.toString( this.getPlacement() ) );
	}
}
