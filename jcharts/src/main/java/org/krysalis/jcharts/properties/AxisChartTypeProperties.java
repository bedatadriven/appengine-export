/***********************************************************************************************
 * File Info: $Id: AxisChartTypeProperties.java,v 1.1 2003/05/17 17:00:31 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.test.HTMLTestable;
import org.krysalis.jcharts.chartData.interfaces.IAxisPlotDataSet;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.*;


import java.util.ArrayList;


/********************************************************************************************
 * Common parent for all Chart type specific properties Objects.
 *
 *
 *******************************************************************************************/
public abstract class AxisChartTypeProperties extends ChartTypeProperties implements HTMLTestable {
	private ArrayList preRenderEventListeners;
	private ArrayList postRenderEventListeners;


	public abstract void validate( IAxisPlotDataSet iAxisPlotDataSet ) throws PropertyException;


	/**************************************************************************************
	 * Allows you to get callbacks for custom implementations to render on the axis and to
	 * 	control the renderering on the axis before a value is drawn.
	 *
	 * @param preRenderListener
	 *************************************************************************************/
	public void addPreRenderEventListener( PreAxisValueRenderListener preRenderListener ) {
		if( this.preRenderEventListeners == null ) {
			this.preRenderEventListeners = new ArrayList();
		}

		this.preRenderEventListeners.add( preRenderListener );
	}


	/**************************************************************************************
	 *
	 * @param axisValueRenderEvent
	 *************************************************************************************/
	public void firePreRender( AxisValueRenderEvent axisValueRenderEvent ) {
		if( this.preRenderEventListeners != null ) {
			PreAxisValueRenderListener preRenderListener;
			for( int i = 0; i < this.preRenderEventListeners.size(); i++ ) {
				preRenderListener = (PreAxisValueRenderListener) this.preRenderEventListeners.get( i );
				preRenderListener.preRender( axisValueRenderEvent );
			}
		}
	}


	/**************************************************************************************
	 * Allows you to get callbacks for custom implementations to render on the axis and to
	 * 	control the renderering on the axis after a value is drawn.
	 *
	 * @param postRenderListener
	 *************************************************************************************/
	public void addPostRenderEventListener( PostAxisValueRenderListener postRenderListener ) {
		if( this.postRenderEventListeners == null ) {
			this.postRenderEventListeners = new ArrayList();
		}

		this.postRenderEventListeners.add( postRenderListener );
	}


	/**************************************************************************************
	 *
	 * @param axisValueRenderEvent
	 *************************************************************************************/
	public void firePostRender( AxisValueRenderEvent axisValueRenderEvent ) {
		if( this.postRenderEventListeners != null ) {
			PostAxisValueRenderListener postRenderListener;
			for( int i = 0; i < this.postRenderEventListeners.size(); i++ ) {
				postRenderListener = (PostAxisValueRenderListener) this.postRenderEventListeners.get( i );
				postRenderListener.postRender( axisValueRenderEvent );
			}
		}
	}

}
