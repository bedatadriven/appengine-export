/***********************************************************************************************
 * File Info: $Id: PointChartProperties.java,v 1.1 2003/05/17 17:00:36 nathaniel_auvil Exp $
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
import com.google.code.appengine.awt.geom.Ellipse2D;
import com.google.code.appengine.awt.geom.Rectangle2D;


final public class PointChartProperties extends AxisChartTypeProperties
{
	public final static Stroke DEFAULT_POINT_BORDER_STROKE = new BasicStroke( 1.0f );

	public final static Shape SHAPE_SQUARE = new Rectangle2D.Double( 0, 0, 10, 10 );
	public final static Shape SHAPE_TRIANGLE = new Polygon( new int[]{0, 5, 10}, new int[]{10, 0, 10}, 3 );
	public final static Shape SHAPE_CIRCLE = new Ellipse2D.Double( 0, 0, 10, 10 );
	public final static Shape SHAPE_DIAMOND = new Polygon( new int[]{0, 5, 10, 5}, new int[]{5, 0, 5, 10}, 4 );


	private Shape[] shapes;
	private boolean[] fillPointFlags;
	private Paint[] outlinePaints;


	/***************************************************************************************************
	 * Constructor
	 *
	 * @param shapes the Shapes to use for each DataSet drawn in this chart.  There must
	 *     be an one to one mapping of Shape objects and DataSets in the chart.
	 * @param fillPointFlags flags indicating whether to fill the point Shapes or to only outline them
	 *     using the Paint specified on the DataSet object.  If this is set to TRUE, the 'outlinePaint'
	 *     attribute can be used to outline the Shape.
	 * @param outlinePaints Sets the outline Paint to use for each Shape in the chart.  This Paint is
	 *     only used if the 'setFillPointsFlag' is set to TRUE for the Shape.
	 **************************************************************************************************/
	public PointChartProperties( Shape[] shapes, boolean[] fillPointFlags, Paint[] outlinePaints )
	{
		this.shapes = shapes;
		this.fillPointFlags = fillPointFlags;
		this.outlinePaints = outlinePaints;
	}


	/**********************************************************************************************
	 *
	 *
	 ***********************************************************************************************/
	public boolean getFillPointsFlag( int index )
	{
		return this.fillPointFlags[ index ];
	}


	/**********************************************************************************************
	 *
	 *
	 * @param index
	 * @return Paint
	 ***********************************************************************************************/
	public Paint getPointOutlinePaints( int index )
	{
		return this.outlinePaints[ index ];
	}


	/**********************************************************************************************
	 *
	 * @param index
	 * @return Shape
	 ***********************************************************************************************/
	public Shape getShape( int index )
	{
		return this.shapes[ index ];
	}


	/*********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator )
	{
		htmlGenerator.propertiesTableStart( "PointChartProperties" );
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
		if( iAxisPlotDataSet.getNumberOfDataSets() != this.shapes.length )
		{
			throw new PropertyException( "<PointChartProperties> There must be a Shape implementation for each data set." );
		}

		if( this.shapes.length != fillPointFlags.length )
		{
			throw new PropertyException( "<PointChartProperties> There is NOT an one to one mapping between 'fillPointsFlags' and Shapes" );
		}


		if( this.shapes.length != outlinePaints.length )
		{
			throw new PropertyException( "<PointChartProperties> There is NOT an one to one mapping between 'outlinePaints' and Shapes" );
		}
	}

}
