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


/*************************************************************************************
 * Immutable Class to simplify the use of Strokes in Charts
 *
 * @author Nathaniel Auvil
 * @version $Id: ChartStroke.java,v 1.2 2003/06/25 01:39:25 nathaniel_auvil Exp $
 ************************************************************************************/
public class ChartStroke extends ChartItem
{
	private static final Stroke DEFAULT_STROKE = new BasicStroke( 1.0f );
	private static final Stroke DEFAULT_STROKE_1_5 = new BasicStroke( 1.5f );

	public static final ChartStroke DEFAULT_AXIS = new ChartStroke( DEFAULT_STROKE_1_5, Color.black );
	public static final ChartStroke DEFAULT_GRIDLINES = new ChartStroke( DEFAULT_STROKE, Color.lightGray );
	public static final ChartStroke DEFAULT_TICKS = new ChartStroke( DEFAULT_STROKE, Color.black );
	public static final ChartStroke DEFAULT_ZERO_LINE = new ChartStroke( DEFAULT_STROKE, Color.darkGray );

	public static final ChartStroke DEFAULT_BAR_OUTLINE = new ChartStroke( DEFAULT_STROKE, Color.black );
	public static final ChartStroke DEFAULT_CHART_OUTLINE = new ChartStroke( DEFAULT_STROKE, Color.black );
	public static final ChartStroke DEFAULT_LEGEND_OUTLINE = new ChartStroke( DEFAULT_STROKE, Color.black );

	public static final ChartStroke DEFAULT_PIE_OUTLINE = new ChartStroke( DEFAULT_STROKE, Color.black );

	private Stroke stroke;


	/*********************************************************************************
	 *
	 * @param stroke
	 * @param paint
	 **********************************************************************************/
	public ChartStroke( Stroke stroke, Paint paint )
	{
		super( paint );

		this.stroke = stroke;
	}


	/**********************************************************************************
	 * Sets the Paint and Stroke implementations on the Graphics2D Object
	 *
	 * @param graphics2D
	 **********************************************************************************/
	public void setupGraphics2D( Graphics2D graphics2D )
	{
		super.setupGraphics2D( graphics2D );
		graphics2D.setStroke( this.stroke );
	}


	/*********************************************************************************
	 *
	 * @param graphics2D
	 * @param shape
	 ********************************************************************************/
	public void draw( Graphics2D graphics2D, Shape shape )
	{
		this.setupGraphics2D( graphics2D );
		graphics2D.draw( shape );
	}


	/*********************************************************************************
	 *
	 * @param graphics2D
	 * @param shape
	 ********************************************************************************/
	public void fill( Graphics2D graphics2D, Shape shape )
	{
		this.setupGraphics2D( graphics2D );
		graphics2D.fill( shape );
	}



}
