
package org.krysalis.jcharts.properties;


/***********************************************************************************************
 * File Info: $Id: AreaProperties.java,v 1.2 2003/08/08 08:51:27 nicolaken Exp $
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


import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.test.HTMLTestable;


public class AreaProperties extends Properties implements HTMLTestable
{
	//---padding on all edges of image
	private float edgePadding = 5;

	private ChartStroke borderStroke = null;


	/*********************************************************************************************
	 *
	 *
	 **********************************************************************************************/
	public AreaProperties()
	{
		super();
	}


	/*********************************************************************************************
	 * Returns the number of pixels to pad the edges of the image
	 *
	 * @return float
	 **********************************************************************************************/
	public float getEdgePadding()
	{
		return this.edgePadding;
	}


	/*********************************************************************************************
	 * Sets the number of pixels to pad the edges of the image
	 *
	 * @param edgePadding
	 **********************************************************************************************/
	public void setEdgePadding( float edgePadding )
	{
		this.edgePadding = edgePadding;
	}


	/*********************************************************************************************
	 * Sets the border Stroke. If NULL is passed, there will be no border.
	 *
	 * @param chartStroke
	 **********************************************************************************************/
	public void setBorderStroke( ChartStroke chartStroke )
	{
		this.borderStroke = chartStroke;
	}


	/*********************************************************************************************
	 * Returns the border Stroke
	 *
	 * @return ChartStroke
	 **********************************************************************************************/
	public ChartStroke getBorderStroke()
	{
		return this.borderStroke;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		super.toHTML( htmlGenerator );
		htmlGenerator.addTableRow( "AreaProperties->Edge Padding", Float.toString( this.getEdgePadding() ) );
		htmlGenerator.addTableRow( "AreaProperties->Border Stroke", this.borderStroke );
	}

}
