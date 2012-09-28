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

package org.krysalis.jcharts.properties.util;


import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.AffineTransform;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: ChartFont.java,v 1.4 2004/05/31 16:25:22 nathaniel_auvil Exp $
 ************************************************************************************/
public class ChartFont extends ChartItem
{
	public static final ChartFont DEFAULT_AXIS_TITLE = new ChartFont( new Font( "Serif", Font.BOLD, 12 ), Color.black );
	public static final ChartFont DEFAULT_AXIS_VALUE = new ChartFont( new Font( "Serif", Font.PLAIN, 12 ), Color.black );
	public static final ChartFont DEFAULT_AXIS_SCALE = new ChartFont( new Font( "TimesRoman", Font.PLAIN, 12 ), Color.black );

	public static final ChartFont DEFAULT_LEGEND = new ChartFont( new Font( "Serif", Font.PLAIN, 12 ), Color.black );

	public static final ChartFont DEFAULT_PIE_VALUE = new ChartFont( new Font( "Serif", Font.PLAIN, 10 ), Color.black );

	public static final ChartFont DEFAULT_CHART_TITLE= new ChartFont( new Font( "Serif", Font.BOLD, 12 ), Color.black );

	public static final AffineTransform VERTICAL_ROTATION = AffineTransform.getRotateInstance( -Math.PI / 2 );


   private Font font;
	//private boolean isAntiAliased= true;
   private AffineTransform affineTransform;

	/************************************************************************************
	 *
 	 * @param font
	 * @param paint
	 ************************************************************************************/
	public ChartFont( Font font, Paint paint )
	{
		super( paint );
		this.font= font;
	}


	/**********************************************************************************
	 * Sets the Paint and Stroke implementations on the Graphics2D Object
	 *
	 * @param graphics2D
	 **********************************************************************************/
	public void setupGraphics2D( Graphics2D graphics2D )
	{
		super.setupGraphics2D( graphics2D );
		graphics2D.setFont( this.font );
	}


   /**************************************************************************************
	 *
	 * @return font
	 * TODO this allows you to modify the Font
 	 **************************************************************************************/
	public Font getFont()
	{
		return this.font;
	}


	/**************************************************************************************
	 *
//	 * @return boolean
	 **************************************************************************************
	public boolean isAntiAliased()
	{
		return isAntiAliased;
	}


	/**************************************************************************************
	 *
	 * @return Font
	 **************************************************************************************/
	public Font deriveFont()
	{
		this.affineTransform= VERTICAL_ROTATION;
		return this.font.deriveFont( this.affineTransform );
	}


}
