/***********************************************************************************************
 * File Info: $Id: TextProcessor.java,v 1.1 2003/05/17 16:57:50 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.chartData.processors;


import org.krysalis.jcharts.chartText.TextTag;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import java.util.ArrayList;


/*******************************************************************************************
 *
 *
 *******************************************************************************************/
public class TextProcessor
{
	private float widestLabel = Float.MIN_VALUE;
	private float tallestLabel = Float.MIN_VALUE;
	private float totalLabelWidths = 0.0f;

	private ArrayList textTags;


	/******************************************************************************************
	 * Constructor
	 *
	 *******************************************************************************************/
	public TextProcessor()
	{
		this.textTags = new ArrayList( 30 );
	}


	/******************************************************************************************
	 *
	 * @param label
	 * @param font
	 * @param fontRenderContext
	 *******************************************************************************************/
	public void addLabel( String label, Font font, FontRenderContext fontRenderContext )
	{
		TextTag textTag= new TextTag( label, font, fontRenderContext );
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
}
