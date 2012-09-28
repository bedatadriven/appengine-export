/***********************************************************************************************
 * File Info: $Id: ImageMapArea.java,v 1.1 2003/05/17 16:59:18 nathaniel_auvil Exp $
 * Copyright (C) 2001
 * Author: Nathaniel G. Auvil
 * Contributor(s):
 *
 * Copyright 2002 (C) Nathaniel G. Auvil. All Rights Reserved.
 *
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "jCharts" or "Nathaniel G. Auvil" must not be used to
 * 	  endorse or promote products derived from this Software without
 * 	  prior written permission of Nathaniel G. Auvil.  For written
 *    permission, please contact nathaniel_auvil@users.sourceforge.net
 *
 * 4. Products derived from this Software may not be called "jCharts"
 *    nor may "jCharts" appear in their names without prior written
 *    permission of Nathaniel G. Auvil. jCharts is a registered
 *    trademark of Nathaniel G. Auvil.
 *
 * 5. Due credit should be given to the jCharts Project
 *    (http://jcharts.sourceforge.net/).
 *
 * THIS SOFTWARE IS PROVIDED BY Nathaniel G. Auvil AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * jCharts OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 ************************************************************************************************/

package org.krysalis.jcharts.imageMap;


import java.io.Serializable;
import com.google.code.appengine.awt.geom.Point2D;


/*****************************************************************************************
 *
 *
 ******************************************************************************************/
public abstract class ImageMapArea implements Serializable
{
	//---less overhead than creating Point Objects
	int[] x;
	int[] y;

	private double value;
	private String xAxisLabel;
	private String legendLabel;


	/***************************************************************************************
	 *
	 * @param numberOfPoints
	 * @param value
	 * @param xAxisLabel
	 * @param legendLabel
	 ****************************************************************************************/
	ImageMapArea( int numberOfPoints, double value, String xAxisLabel, String legendLabel )
	{
		this.x=new int[ numberOfPoints ];
		this.y=new int[ numberOfPoints ];

		this.value=value;
		this.xAxisLabel=xAxisLabel;
		this.legendLabel=legendLabel;
	}


	/***************************************************************************************
	 *
	 * @param numberOfPoints
	 * @param point
	 * @param legendLabel
	 ****************************************************************************************/
	ImageMapArea( int numberOfPoints, Point2D.Double point, String legendLabel )
	{
		this.x=new int[ numberOfPoints ];
		this.y=new int[ numberOfPoints ];

		this.value= point.getY();
		this.xAxisLabel= Double.toString( point.getX() );
		this.legendLabel=legendLabel;
	}


	/************************************************************************************
	 *
	 * @return AreaShape
	 ***********************************************************************************/
	abstract AreaShape getAreaShape();


	/***************************************************************************************
	 * Returns the number of x,y coordinate pairs stored for the area
	 *
	 * @return int
	 ****************************************************************************************/
	public final int getNumberOfCoordinates()
	{
		return this.x.length;
	}


	/***************************************************************************************
	 * Returns the x coordinate at the specified index. Not returned as a Point Object so we
	 *	can avoid uneeded Object creation/destruction overhead.
	 *
	 * @return int
	 ****************************************************************************************/
	public final int getXCoordinate( int index )
	{
		return this.x[ index ];
	}


	/***************************************************************************************
	 * Returns the y coordinate at the specified index. Not returned as a Point Object so we
	 *	can avoid uneeded Object creation/destruction overhead.
	 *
	 * @return int
	 ****************************************************************************************/
	public final int getYCoordinate( int index )
	{
		return this.y[ index ];
	}


	/***************************************************************************************
	 * Returns the data set value represented by this map.
	 *
	 * @return double
	 ****************************************************************************************/
	public final double getValue()
	{
		return this.value;
	}


	/***************************************************************************************
	 * Rather than create an AxisChart specifc map area class just for this field, i put it
	 *	here. This is not applicable for PieCharts.
	 *
	 * @return String  will return NULL for PieCharts
	 ****************************************************************************************/
	public final String getXAxisLabel()
	{
		return this.xAxisLabel;
	}


	/***************************************************************************************
	 * Returns the legend label represented by this map area. Will be NULL if you did not
	 *	pass a name to the data set constructor.
	 *
	 * @return String
	 ****************************************************************************************/
	public final String getLengendLabel()
	{
		return this.legendLabel;
	}


	/***************************************************************************************
	 * Appends the coordinates to the passed html buffer.  This is needed to facilitate the
	 *	'circle' map areas 'radius' value.
	 *
	 * @param html pass a reference to the StringBuffer so I can minimize Object creation
	 ****************************************************************************************/
	protected void getCoordinates( StringBuffer html )
	{
		for( int i=0; i < this.x.length; i++ )
		{
			html.append( this.x[ i ] + "," + this.y[ i ] );
			if( i + 1 != this.x.length )
			{
				html.append( "," );
			}
		}
	}


	/**************************************************************************************************
	 * Returns a <pre><area shape="..." coords="....."  + mapElementAttributes ></pre> HTML element.
	 * 	The mapElementAttributes frees this method from having to declare all attributes of the HTML map
	 *	element.
	 *
	 * @param mapElementAttributes allows you to place any map attributes you want:
	 * 				href, alt, onClick, onMouseOver, etc...
	 * @return String the HTML
	 ***************************************************************************************************/
	public final String toHTML( String mapElementAttributes )
	{
		StringBuffer html=new StringBuffer( 250 );

		html.append( "<area" );
		html.append( " shape=\"" + this.getAreaShape().getValue() + "\"" );

		html.append( " coords=\"" );
		this.getCoordinates( html );
		html.append( "\" " );

		html.append( mapElementAttributes );
		html.append( ">\n" );

		return html.toString();
	}
}
