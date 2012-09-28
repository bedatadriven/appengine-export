/***********************************************************************************************
 * File Info: $Id: AxisValueRenderEvent.java,v 1.2 2004/05/31 16:28:44 nathaniel_auvil Exp $
 * Copyright (C) 2002
 * Author: Nathaniel G. Auvil
 * Contributor(s): John Thomsen
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

package org.krysalis.jcharts.axisChart.customRenderers.axisValue;


import org.krysalis.jcharts.axisChart.AxisChart;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;

import java.util.EventObject;
import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.geom.Rectangle2D;
import com.google.code.appengine.awt.font.FontRenderContext;


public class AxisValueRenderEvent extends EventObject {

	private Graphics2D graphics2D;
	private FontRenderContext fontRenderContext;


	private IAxisPlotDataSet iAxisPlotDataSet;

	//---the total axis area the scale item occupies
	private Rectangle2D.Float totalItemAxisArea;

	private float zeroLineCoordinate;
	private float valueX;
	private float valueY;

	private int dataSetIndex;
	private int valueIndex;


	/************************************************************************************
	 *
	 * @param axisChart
	 * @param graphics2D
	 * @param totalItemAxisArea
	 ***********************************************************************************/
	public AxisValueRenderEvent( AxisChart axisChart,
										  IAxisPlotDataSet iAxisPlotDataSet,
										  Graphics2D graphics2D,
										  Rectangle2D.Float totalItemAxisArea,
										  float zeroLineCoordinate ) {
		super( axisChart );

      this.iAxisPlotDataSet= iAxisPlotDataSet;
		this.graphics2D = graphics2D;
		this.fontRenderContext = graphics2D.getFontRenderContext();
		this.totalItemAxisArea = totalItemAxisArea;
		this.zeroLineCoordinate= zeroLineCoordinate;
	}


	/***********************************************************************************
	 *
	 * @return Graphics2D
	 **********************************************************************************/
	public Graphics2D getGraphics2D() {
		return graphics2D;
	}


	/***********************************************************************************
	 *
	 * @return FontRenderContext
	 **********************************************************************************/
	public FontRenderContext getFontRenderContext() {
		return fontRenderContext;
	}


	/*************************************************************************************
	 * Returns the bounding box of the total axis plot area alotted to the current scale
	 * 	item.
	 *
	 * @return Rectangle2D.Float
	 *************************************************************************************/
	public Rectangle2D.Float getTotalItemAxisArea() {
		return totalItemAxisArea;
	}



	public IAxisPlotDataSet getiAxisPlotDataSet() {
		return iAxisPlotDataSet;
	}


	/**************************************************************************************
	 *
	 * @return float
	 *************************************************************************************/
	public float getValueX() {
		return valueX;
	}


	public void setValueX( float valueX ) {
		this.valueX = valueX;
	}


	public float getValueY() {
		return valueY;
	}


	public void setValueY( float valueY ) {
		this.valueY = valueY;
	}


	public int getDataSetIndex() {
		return dataSetIndex;
	}


	public void setDataSetIndex( int dataSetIndex ) {
		this.dataSetIndex = dataSetIndex;
	}


	public int getValueIndex() {
		return valueIndex;
	}


	public void setValueIndex( int valueIndex ) {
		this.valueIndex = valueIndex;
	}


	public float getZeroLineCoordinate() {
		return zeroLineCoordinate;
	}
}
