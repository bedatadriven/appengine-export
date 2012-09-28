/***********************************************************************************************
 * File Info: $Id: ScatterPlotProperties.java,v 1.1 2003/05/17 17:00:39 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.test.HTMLGenerator;

import com.google.code.appengine.awt.*;


final public class ScatterPlotProperties extends AxisChartTypeProperties
{
	public static final Stroke DEFAULT_LINE_STROKE=new BasicStroke( 1.5f );

	private Stroke[] lineStrokes;
	private Shape[] shapes;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param lineStrokes
	 * @param shapes if any of the shapes are NULL, they will not be drawn. If the passed
	 * 	Array is NULL, no shpaes will be drawn. There are some Shapes defined in
	 * 	PointChartProperties Object.
	 *****************************************************************************************/
	public ScatterPlotProperties( Stroke[] lineStrokes, Shape[] shapes )
	{
		this.lineStrokes=lineStrokes;
		this.shapes=shapes;
	}


	public Stroke[] getLineStrokes()
	{
		return this.lineStrokes;
	}


	public Shape[] getShapes()
	{
		return this.shapes;
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "ScatterPlotProperties" );
		//htmlGenerator.addTableRow( "Zero Degree Offset", Double.toString( this.getZeroDegreeOffset() ) );
		htmlGenerator.propertiesTableEnd();
	}


	/******************************************************************************************
	 * Validates the properties.
	 *
	 * @param iAxisPlotDataSet
	 * @throws PropertyException
	 *****************************************************************************************/
	public void validate( IAxisPlotDataSet iAxisPlotDataSet ) throws PropertyException
	{
      if( this.lineStrokes == null )
      {
	      throw new PropertyException( "You must define Stroke Objects for the LineChart in the LineChartProperties Object." );
      }
		else
      {
	      if( iAxisPlotDataSet.getNumberOfDataSets() != this.lineStrokes.length )
	      {
		      throw new PropertyException( "You must define a Stroke Object for each Line in the LineChart." );
	      }

			if( this.shapes != null )
			{
				if( this.shapes.length != this.lineStrokes.length )
				{
					throw new PropertyException( "The number of Shapes defined in the LineChartProperties Object must equal the number of Lines." );
				}
			}
      }
	}

}
