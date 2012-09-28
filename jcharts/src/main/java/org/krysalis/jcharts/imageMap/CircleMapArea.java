/***********************************************************************************************
 * File Info: $Id: CircleMapArea.java,v 1.1 2003/05/17 16:59:18 nathaniel_auvil Exp $
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
public final class CircleMapArea extends ImageMapArea implements Serializable
{
	//---only applies to circles
	private int radius=5;


	/***************************************************************************************
	 *
	 * @param x
	 * @param y
	 * @param value
	 * @param xAxisLabel
	 * @param legendLabel
	 ****************************************************************************************/
	public CircleMapArea( float x, float y, double value, String xAxisLabel, String legendLabel )
	{
		super( 1, value, xAxisLabel, legendLabel );

		super.x[ 0 ]= (int) x;
		super.y[ 0 ]= (int) y;
	}


	/***************************************************************************************
	 *
	 * @param x
	 * @param y
	 * @param value
	 * @param legendLabel
	 ****************************************************************************************/
	public CircleMapArea( float x, float y, Point2D.Double value, String legendLabel )
	{
		super( 1, value, legendLabel );

		super.x[ 0 ]= (int) x;
		super.y[ 0 ]= (int) y;
	}


	/***************************************************************************************
	 *
	 * @return AreaShape
	 ****************************************************************************************/
	public AreaShape getAreaShape()
	{
		return AreaShape.CIRCLE;
	}


	/***************************************************************************************
	 * Allows user to specify the radius for each circle
	 *
	 * @param radius
	 ****************************************************************************************/
	public void setRadius( int radius )
	{
		this.radius=radius;
	}


	protected void getCoordinates( StringBuffer html )
	{
		html.append( this.x[ 0 ] + "," + this.y[ 0 ] + "," + this.radius );
	}


}
