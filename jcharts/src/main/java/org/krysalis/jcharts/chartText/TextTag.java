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


import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.font.TextLayout;
import com.google.code.appengine.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Hashtable;


/*************************************************************************************
 *
 * @author John Thomsen, Nathaniel Auvil
 * @version $Id: TextTag.java,v 1.2 2004/05/31 16:27:21 nathaniel_auvil Exp $
 ************************************************************************************/
public class TextTag implements HTMLTestable, Serializable
{
	//---Strangely, we don't need to store the text itself. (yet)

	private float xPosition = 0;
	private float yPosition = 0;

	private TextLayout textLayout = null;
	private Hashtable attributes = null;

	private boolean isHidden = false;

	//---derived values

	private float width = 0;
	private float height = 0;
	private float fontAscent = 0;
	private float fontDescent = 0;

	private boolean isDerived = false;
	private Font derivedFont = null;

	private String text;   //for toString();
	private Font font;   //for toString();


	/*********************************************************************************************
	 * Default constructor - for untransformed fonts.
	 *
	 **********************************************************************************************/
	public TextTag( String text, Font baseFont, FontRenderContext fontRenderContext )
	{
		this( text, baseFont, null, fontRenderContext );
	}


	/*********************************************************************************************
	 * Constructor when using transformed (derived) fonts
	 *
	 * The need for this arises because the java metrics classes return either 0 or very strange
	 * values for the width and height of a string (TextLayout, LineMetrics, etc..) when
	 * the font is derived
	 *
	 * @param text
	 * @param baseFont is the original (untransformed) font.
	 * @param derivedFont is the transformed font
	 * @param fontRenderContext
	 **********************************************************************************************/
	public TextTag( String text, Font baseFont, Font derivedFont, FontRenderContext fontRenderContext )
	{
		this.textLayout = new TextLayout( text, baseFont, fontRenderContext );

		this.isDerived = (derivedFont != null);
		this.derivedFont = derivedFont;

		//---Dimensions
		this.width = this.textLayout.getAdvance();
		this.height = this.textLayout.getAscent() + this.textLayout.getDescent();

		//---need this to offset font rendering, as rendering is at the baseline not bottom or top,
		this.fontAscent = this.textLayout.getAscent();
		this.fontDescent = this.textLayout.getDescent();

		this.font = (this.isDerived) ? derivedFont : baseFont;
		this.text = text;

//		System.out.println(">"+text+" Bounds:"+textLayout.getBounds() );
//		System.out.println("   >Ascent "+textLayout.getAscent()+" Descent:"+textLayout.getDescent() );
//		System.out.println("   >isVertical "+textLayout.isVertical());
//
//		LineMetrics lm = font.getLineMetrics(text, fontRenderContext);

//		System.out.println("    Metrics:" + lm.getHeight() + " " + lm.getAscent() + lm.getDescent());

	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getWidth()
	{
		return this.width;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getHeight()
	{
		return this.height;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getFontAscent()
	{
		return this.fontAscent;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getFontDescent()
	{
		return this.fontDescent;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public void setPosition( float x, float y )
	{
		this.xPosition = x;
		this.yPosition = y;

		//System.out.println("TEXT:==>"+text+"< at "+x+","+y);
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public void setXPosition( float x )
	{
		this.xPosition = x;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public void setYPosition( float y )
	{
		this.yPosition = y;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getXPosition()
	{
		return this.xPosition;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getYPosition()
	{
		return this.yPosition;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getRightSide()
	{
		return this.xPosition + this.width;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public float getBottomSide()
	{
		return this.yPosition + this.height;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public Rectangle2D.Float getRectangle()
	{
		return new Rectangle2D.Float( this.xPosition, this.yPosition, this.width, this.height );
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public void setHidden( boolean b )
	{
		this.isHidden = b;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public boolean getHidden()
	{
		return this.isHidden;
	}


	public String getText()
	{
		return text;
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public void addAttribute( String name, Object o )
	{
		if( attributes == null )
		{
			attributes = new Hashtable();
		}

		attributes.put( name, o );
	}


	/********************************************************************************************
	 *
	 ********************************************************************************************/
	public Object getAttribute( String name )
	{
		if( attributes == null )
		{
			return null;
		}

		return attributes.get( name );
	}


	/**********************************************************************************************
	 * Renders the text, at the position - renders from the top (instead of baseline)
	 *
	 * @param g2d
	 * @param fontColor
	 **********************************************************************************************/
	public void render( Graphics2D g2d, Paint fontColor )
	{
		if( fontColor != null )
		{
			g2d.setPaint( fontColor );
		}

		if( this.isHidden == false )
		{
			if( isDerived )
			{
				g2d.setFont( this.derivedFont );
				g2d.drawString( this.text, this.xPosition, this.yPosition );
			}
			else
			{
				textLayout.draw( g2d, this.xPosition, this.yPosition );
			}

			//---Debug comparison code.
			//g2d.setFont( this.font );
			//g2d.drawString(this.text, this.xPosition, this.yPosition);

			/* Interesting...
			System.out.println("Ascent:"+this.fontAscent);
			System.out.println("Descent:"+this.fontDescent);
			System.out.println("Height:"+this.heightNeeded);
			System.out.println("Width:"+this.widthNeeded);
			*/
		}
	}


	/**********************************************************************************************
	 * Renders the text, at the position - renders from the top (instead of baseline)
	 *
	 * @param g2d

	 **********************************************************************************************/
	public void render( Graphics2D g2d, float x, float y )
	{
		textLayout.draw( g2d, x, y );

/*

		if( fontColor != null )
		{
			g2d.setPaint( fontColor );
		}

		if( this.isHidden == false )
		{
			if( isDerived )
			{
				g2d.drawString( this.text, x, y );
			}
			else
			{
            textLayout.draw( g2d, x, y );
			}
		}
*/
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


	/********************************************************************************************
	 *
	 * @return String
	 ********************************************************************************************/
	public String toString()
	{
		String str = "TextTag: '" + this.text + "',x=" + this.xPosition + ",y=" + this.yPosition;

		str += "width=" + this.width + ",height=" + this.height;
		str += ",font=" + this.font.getName() + "," + this.font.getSize();

		if( attributes != null && attributes.size() > 0 )
		{
			java.util.Enumeration enumeration = attributes.keys();
			str += "\nAttributes:";
			while( enumeration.hasMoreElements() )
			{
				Object ob = enumeration.nextElement();
				str += "    [" + ob.toString() + "]=[" + attributes.get( ob ).toString() + "]\n";
			}
		}

		return str;
	}
}
