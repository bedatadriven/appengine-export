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

package org.krysalis.jcharts.chartText;


import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: TextTagGroup.java,v 1.1 2003/05/17 16:58:32 nathaniel_auvil Exp $
 ************************************************************************************/
public class TextTagGroup implements HTMLTestable, Serializable
{
	private float widestLabel = 0.0f;
	private float tallestLabel = 0.0f;
	private float totalLabelWidths = 0.0f;

	private ArrayList textTags;

	private ChartFont chartFont;
	private FontRenderContext fontRenderContext;


	//---Strangely, we don't need to store the text itself. (yet)

//	private ArrayList group;
//	private Font baseFont;
//	private Font derivedFont;
//	private Paint fontColor;

//	private float tallestTextTag = 0.0f;
//	private float widestTextTag = 0.0f;
//	private boolean computedTallestAndWidest = false;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param chartFont
	 * @param fontRenderContext
	 *******************************************************************************************/
	public TextTagGroup( ChartFont chartFont, FontRenderContext fontRenderContext )
	{
		this.textTags = new ArrayList( 30 );

		this.chartFont = chartFont;
		this.fontRenderContext = fontRenderContext;
	}


	/******************************************************************************************
	 *
	 * @param label
	 *******************************************************************************************/
	public void addLabel( String label )
	{
		TextTag textTag = new TextTag( label, this.chartFont.getFont(), this.fontRenderContext );
		this.textTags.add( textTag );

		this.widestLabel = Math.max( textTag.getWidth(), this.widestLabel );
		this.totalLabelWidths += textTag.getWidth();

		this.tallestLabel = Math.max( textTag.getHeight(), this.tallestLabel );
	}


	/******************************************************************************************
	 * Returns the number of labels
	 *
	 * @return int
	 ******************************************************************************************/
	public int size()
	{
		return this.textTags.size();
	}


	/*******************************************************************************************
	 * Horizontal plots render the data from top down so rendering alogorithm needs to get the
	 * 	labels in reverse order to make things easier.
	 *
	 *******************************************************************************************/
	public void reverse()
	{
		ArrayList reverse = new ArrayList( this.textTags.size() );
		for( int i = this.textTags.size() - 1; i >= 0; i-- )
		{
			reverse.add( this.textTags.get( i ) );
		}

		this.textTags = reverse;
	}


	/******************************************************************************************
	 * Calculates the width and height needed by the passed String when rendered
	 *
	 //	 * @param iDataSeries
	 /	 * @param font
	 //	 * @param fontRenderContext
	 *******************************************************************************************
	 public void processLabels( IDataSeries iDataSeries)	{
	 //LOOP
	 for( int i = 0; i < iDataSeries.getNumberOfXAxisLabels(); i++ )
	 {
	 this.axisLabelProcessor.processLabel( iDataSeries.getXAxisLabel( i ), axisLabelFont, fontRenderContext );
	 }


	 TextLayout textLayout = new TextLayout( label, font, fontRenderContext );

	 //---WIDTH
	 this.labelWidths[ counter ] = textLayout.getAdvance();
	 this.widestLabel = Math.max( this.labelWidths[ counter ], this.widestLabel );
	 this.totalLabelWidths += this.labelWidths[ counter ];

	 //---HEIGHT
	 this.labelHeights[ counter ] = textLayout.getAscent() + textLayout.getDescent();
	 this.tallestLabel = Math.max( this.labelHeights[ counter ], this.tallestLabel );

	 //---need this to offset font rendering, as rendering is at the baseline not bottom or top,
	 this.fontDescent = textLayout.getDescent();

	 this.counter++;
	 }


	 /******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	public float getWidestLabel()
	{
		return this.widestLabel;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	public float getTallestLabel()
	{
		return this.tallestLabel;
	}


	/******************************************************************************************
	 *
	 *
	 ******************************************************************************************/
	public float getTotalLabelWidths()
	{
		return this.totalLabelWidths;
	}


	/******************************************************************************************
	 *
	 * @param index
	 * @return TextTag
	 ******************************************************************************************/
	public TextTag getTextTag( int index )
	{
		return (TextTag) this.textTags.get( index );
	}


	/**********************************************************************************************
	 * Renders the text, at the position - renders from the top (instead of baseline)
	 *
	 * @param index
	 * @param g2d
	 * @param x
	 * @param y
	 **********************************************************************************************/
	public void render( int index, Graphics2D g2d, float x, float y )
	{
		this.chartFont.setupGraphics2D( g2d );
		this.getTextTag( index ).render( g2d, x, y );
	}


	/**********************************************************************************************
	 * Renders all the text in this group.
	 *
	 *
	 **********************************************************************************************
	 public void renderTag( int index, Graphics2D g2d )
	 {
	 TextTag tag;
	 //Paint p = this.fontColor;

	 g2d.setPaint( this. );

	 //g2d.setFont( (this.derivedFont == null) ? baseFont : derivedFont );

	 for( int i = 0; i < group.size(); i++ )
	 {
	 tag = ( TextTag ) group.get( i );
	 tag.render( g2d, p );
	 // Text tag sets the Paint every time if not null
	 // So, we might save a small bit of time by changing
	 // this to null after first time.
	 p = null;
	 }
	 }


	 /*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( this.getClass().getName() );

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
	}

}