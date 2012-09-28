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


import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.properties.util.ChartFont;

import com.google.code.appengine.awt.*;


/*************************************************************************************
 *
 * @author Nathaniel Auvil, Sandor Dornbush
 * @version $Id: LegendProperties.java,v 1.3 2003/07/05 13:23:50 nathaniel_auvil Exp $
 ************************************************************************************/
public class LegendProperties extends LegendAreaProperties implements HTMLTestable
{
	public static final Font DEFAULT_FONT = new Font( "Serif", Font.PLAIN, 12 );
	public static final Paint DEFAULT_FONT_PAINT = Color.black;
	public static final Stroke DEFAULT_ICON_BORDER_STROKE = new BasicStroke( 1.0f );
	public static final Paint DEFAULT_ICON_BORDER_PAINT = Color.black;

	private ChartFont font = ChartFont.DEFAULT_LEGEND;

	private Paint iconBorderPaint = DEFAULT_ICON_BORDER_PAINT;
	private Stroke iconBorderStroke = DEFAULT_ICON_BORDER_STROKE;
	private Dimension size = null;


	/*********************************************************************************************
	 *
	 **********************************************************************************************/
	public LegendProperties()
	{
		super();

		//---Legend background should be transparent by default.
		super.setBackgroundPaint( null );

		super.setBorderStroke( ChartStroke.DEFAULT_LEGEND_OUTLINE );
	}


	/*********************************************************************************************
	 * Sets the Font used by the Legend.
	 *
	 * @param legendFont
	 **********************************************************************************************/
	public void setChartFont( ChartFont legendFont )
	{
		this.font = legendFont;
	}


	/*********************************************************************************************
	 * Returns the Font used by the Legend.
	 *
	 * @return ChartFont
	 **********************************************************************************************/
	public ChartFont getChartFont()
	{
		return this.font;
	}


	/*********************************************************************************************
	 * Sets the Icon border color used by the Legend. If NULL is passed, there will be no border.
	 *
	 * @param iconBorderPaint
	 **********************************************************************************************/
	public void setIconBorderPaint( Paint iconBorderPaint )
	{
		this.iconBorderPaint = iconBorderPaint;
	}


	/*********************************************************************************************
	 * Returns the Icon border Color used by the Legend.
	 *
	 * @return Paint
	 **********************************************************************************************/
	public Paint getIconBorderPaint()
	{
		return this.iconBorderPaint;
	}


	/*********************************************************************************************
	 * Sets the icon border Stroke. If NULL is passed, there will be no border.
	 *
	 * @param stroke
	 **********************************************************************************************/
	public void setIconBorderStroke( Stroke stroke )
	{
		this.iconBorderStroke = stroke;
	}


	/*********************************************************************************************
	 * Returns the icon border Stroke
	 *
	 * @return Stroke
	 **********************************************************************************************/
	public Stroke getIconBorderStroke()
	{
		return this.iconBorderStroke;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "LegendProperties" );
		super.toHTML( htmlGenerator );
		htmlGenerator.addTableRow( "Icon Border Paint", this.getIconBorderPaint() );
		htmlGenerator.addTableRow( "Icon Border Stroke", this.getIconBorderStroke() );
		htmlGenerator.addTableRow( "Chart Font", this.getChartFont() );
		htmlGenerator.propertiesTableEnd();
	}


	/** Getter for property size.
	 * @return Value of property size.
	 *
	 */
	public com.google.code.appengine.awt.Dimension getSize()
	{
		return this.size;
	}


	/** Setter for property size.
	 * @param size New value of property size.
	 *
	 */
	public void setSize( com.google.code.appengine.awt.Dimension size )
	{
		this.size = size;
	}

}
