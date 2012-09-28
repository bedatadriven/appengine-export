/***********************************************************************************************
 * File Info: $Id: BackgroundRenderer.java,v 1.1 2003/05/17 16:56:18 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.axisChart.customRenderers.axisValue.renderers;


import org.krysalis.jcharts.axisChart.customRenderers.axisValue.AxisValueRenderEvent;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.PreAxisValueRenderListener;

import com.google.code.appengine.awt.*;


public class BackgroundRenderer implements PreAxisValueRenderListener
{
	private Paint paint;
	private boolean draw= false;


   /********************************************************************************
	 *
	 * @param paint the paint to do the background in.  You might want to use an
	 * 	alpha less than 255 (as in 'new Color( 20, 20, 20, 50 )' ) as this gets
	 * 	plotted over top of the axis grid lines.
	 ********************************************************************************/
	public BackgroundRenderer( Paint paint )
	{
		this.paint= paint;
	}


	/*********************************************************************************
	 *
	 * @param axisValueRenderEvent
	 *********************************************************************************/
	public void preRender( AxisValueRenderEvent axisValueRenderEvent )
	{
		if( this.draw )
		{
			//---keep the current paint around
			Paint currentPaint= axisValueRenderEvent.getGraphics2D().getPaint();

			axisValueRenderEvent.getGraphics2D().setPaint( this.paint );
			axisValueRenderEvent.getGraphics2D().fill( axisValueRenderEvent.getTotalItemAxisArea() );

			//---reset the current Paint
			axisValueRenderEvent.getGraphics2D().setPaint( currentPaint );
		}
//todo this is not going to work for clustered bar, stacked bar, etc... as it is called each time
		//---fill the background of every other item
		this.draw= ! this.draw;
	}

}
