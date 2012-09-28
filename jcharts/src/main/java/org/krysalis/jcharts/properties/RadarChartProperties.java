/***********************************************************************************************
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


import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;


/*************************************************************************************
 * Properties of a radar chart.
 *
 * @author Rami Hansenne
 * @version $Id: RadarChartProperties.java,v 1.2 2003/08/08 02:40:54 nathaniel_auvil Exp $
 * @since 1.0.0
 ************************************************************************************/
final public class RadarChartProperties extends ChartTypeProperties implements HTMLTestable
{

	private boolean showGridLines = true;
	private boolean fillRadar = true;
	private double max = Double.NaN;
	private double increment = Double.NaN;
	private ChartFont axisLabelChartFont = ChartFont.DEFAULT_AXIS_TITLE;
	private ChartFont titleChartFont = ChartFont.DEFAULT_CHART_TITLE;
	private NumberFormat numberFormat = new DecimalFormat();


	public RadarChartProperties()
	{
		super();
		numberFormat.setMaximumFractionDigits( 2 );
	}


	public boolean getShowGridLines()
	{
		return showGridLines;
	}


	public void setShowGridLines( boolean showGridLines )
	{
		this.showGridLines = showGridLines;
	}


	public boolean getFillRadar()
	{
		return fillRadar;
	}


	public void setFillRadar( boolean fillRadar )
	{
		this.fillRadar = fillRadar;
	}


	public ChartFont getTitleChartFont()
	{
		return titleChartFont;
	}


	public void setTitleChartFont( ChartFont titleChartFont )
	{
		this.titleChartFont = titleChartFont;
	}


	public ChartFont getAxisLabelChartFont()
	{
		return axisLabelChartFont;
	}


	public void setAxisLabelChartFont( ChartFont axisLabelChartFont )
	{
		this.axisLabelChartFont = axisLabelChartFont;
	}


	public void setGridLabelFormat( NumberFormat format )
	{
		if( format != null )
			this.numberFormat = format;
	}


	public NumberFormat getGridLabelFormat()
	{
		return this.numberFormat;
	}


	public double getScaleMaxValue()
	{
		return this.max;
	}


	public void setScaleMaxValue( double max )
	{
		this.max = max;
	}


	public double getScaleIncrement()
	{
		return this.increment;
	}


	public void setScaleIncrement( double increment )
	{
		this.increment = increment;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "RadarChartProperties" );
		htmlGenerator.addTableRow( "Show gridlines", new Boolean( this.showGridLines ) );
		htmlGenerator.addTableRow( "Fill radar", new Boolean( this.fillRadar ) );
		htmlGenerator.addTableRow( "Scale max value", new Double( this.max ) );
		htmlGenerator.addTableRow( "Scale increment", new Double( this.increment ) );
		htmlGenerator.propertiesTableEnd();
	}


	/******************************************************************************************
	 * Validates the properties.
	 *
	 * @param chart
	 * @throws PropertyException
	 *****************************************************************************************/
	public void validate( Chart chart ) throws PropertyException
	{

	}

}
