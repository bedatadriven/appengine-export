/***********************************************************************************************
 * File Info: $Id: ChartProperties.java,v 1.2 2003/05/18 18:06:04 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import java.lang.reflect.Field;


public class ChartProperties extends AreaProperties implements HTMLTestable
{
	public ChartFont titleChartFont = ChartFont.DEFAULT_CHART_TITLE;

	//---number of pixels between the chart title and the plotted chart
	private float titlePadding = 5f;

	//---flag allows you to toggle validations to spare extra CPU cycles after development is over.
	private boolean validate = true;

	private boolean useAntiAliasing= true;


	/******************************************************************************************
	 *
	 * @return ChartFont
	 ******************************************************************************************/
	public ChartFont getTitleFont()
	{
		return this.titleChartFont;
	}


	public void setTitleFont( ChartFont titleFont )
	{
		this.titleChartFont = titleFont;
	}


	/******************************************************************************************
	 * Returns the number of pixels between the Chart Title and the axis plot area
	 *
	 * @return float
	 ******************************************************************************************/
	public float getTitlePadding()
	{
		return this.titlePadding;
	}


	public void setTitlePadding( float pixels )
	{
		this.titlePadding = pixels;
	}


	/******************************************************************************************
	 *
	 * @return boolean
	 * @since 0.7.0
	 ******************************************************************************************/
	public boolean validate()
	{
		return validate;
	}


	/******************************************************************************************
	 * Toggles the validation of data and properties for the charts.  This should be false for
	 * 	production systems as it will run slightly faster.  Anything for speed, right? ;)
	 *
	 * @param validate
	 * @since 0.7.0
	 ******************************************************************************************/
	public void setValidate( boolean validate )
	{
		this.validate = validate;
	}


	/*****************************************************************************************
	 *
	 * @return boolean
	 * @since 1.0.0
	 ****************************************************************************************/
	public boolean useAntiAliasing()
	{
		return useAntiAliasing;
	}


	/*****************************************************************************************
	 * Sets flag on whether the charts will render with Anti-Aliasing enabled.  If you are
	 * 	embeding a chart image in a PDF file, you should set this to 'false' to get a
	 * 	'cleaner' image.
	 *
	 * @param useAntiAliasing
	 * @since 1.0.0
	 ****************************************************************************************/
	public void setUseAntiAliasing( boolean useAntiAliasing )
	{
		this.useAntiAliasing = useAntiAliasing;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( this.getClass().getName() );
		super.toHTML( htmlGenerator );

		Field[] fields = this.getClass().getDeclaredFields();
		for( int i = 0; i < fields.length; i++ )
		{
			try
			{
				htmlGenerator.addField( fields[ i ].getName(), fields[ i ].get( this ) );
			}
			catch( IllegalAccessException illegalAccessException )
			{
				illegalAccessException.printStackTrace();
			}
		}

		htmlGenerator.propertiesTableEnd();


/*

		super.toHTML( htmlGenerator );
		htmlGenerator.addTableRow( "ChartProperties-Title Padding", Float.toString( this.getTitlePadding() ) );
		htmlGenerator.addTableRow( "ChartProperties-Title Font", this.getTitleFont() );
		htmlGenerator.addTableRow( "ChartProperties-Title Paint", this.getTitlePaint() );
		htmlGenerator.addTableRow( "ChartProperties-Title Paint", this.getTitlePaint() );
*/
	}

}
