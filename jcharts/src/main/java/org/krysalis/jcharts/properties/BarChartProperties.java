/***********************************************************************************************
 * File Info: $Id: BarChartProperties.java,v 1.1 2003/05/17 17:00:33 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.test.HTMLGenerator;


public class BarChartProperties extends AxisChartTypeProperties {
	//---percentage of axis width bar should fill
	private float widthPercentage = 0.85f;

	private boolean showOutlines = true;
	private ChartStroke barOutlineStroke = ChartStroke.DEFAULT_BAR_OUTLINE;


	/**********************************************************************************************
	 * Constructor
	 *
	 ***********************************************************************************************/
	public BarChartProperties() {
		super();
	}


	public void setShowOutlinesFlag( boolean showOutlines ) {
		this.showOutlines = showOutlines;
	}


	public boolean getShowOutlinesFlag() {
		return this.showOutlines;
	}


	public void setWidthPercentage( float widthPercentage ) {
		this.widthPercentage = widthPercentage;
	}


	public float getPercentage() {
		return this.widthPercentage;
	}


	public ChartStroke getBarOutlineStroke() {
		return barOutlineStroke;
	}


	public void setBarOutlineStroke( ChartStroke barOutlineStroke ) {
		this.barOutlineStroke = barOutlineStroke;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator ) {
		htmlGenerator.addTableRow( "Width Percentage", Float.toString( this.widthPercentage ) );
		htmlGenerator.addTableRow( "Show Outlines", new Boolean( this.showOutlines ) );
		htmlGenerator.addTableRow( "Bar Outline", this.barOutlineStroke );
	}


	/******************************************************************************************
	 * Validates the properties.
	 *
	 * @param iAxisPlotDataSet
	 * @throws PropertyException
	 *****************************************************************************************/
	public void validate( IAxisPlotDataSet iAxisPlotDataSet ) throws PropertyException {
		//AxisChart axisChart= (AxisChart) chart;
		//IAxisPlotDataSet iAxisPlotDataSet= axisChart.getIDataSeries().getIAxisPlotDataSet( ChartType.LINE );

	}

}
