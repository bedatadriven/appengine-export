package org.krysalis.jcharts.test;


import org.krysalis.jcharts.axisChart.customRenderers.axisValue.PreAxisValueRenderListener;
import org.krysalis.jcharts.axisChart.customRenderers.axisValue.AxisValueRenderEvent;


public class MultiPaintRenderer  implements PreAxisValueRenderListener
{

	/*********************************************************************************
	 *
	 * @param axisValueRenderEvent
	 *********************************************************************************/
	public void preRender( AxisValueRenderEvent axisValueRenderEvent )
	{
      axisValueRenderEvent.getGraphics2D().setPaint( TestDataGenerator.getRandomPaint() );
	}

}
