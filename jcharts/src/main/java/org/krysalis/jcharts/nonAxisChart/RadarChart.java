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

package org.krysalis.jcharts.nonAxisChart;


import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Graphics2D;
import com.google.code.appengine.awt.Paint;
import com.google.code.appengine.awt.Polygon;
import com.google.code.appengine.awt.Rectangle;
import com.google.code.appengine.awt.RenderingHints;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.geom.Point2D;
import com.google.code.appengine.awt.geom.Rectangle2D;

import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.chartData.interfaces.IRadarChartDataSet;
import org.krysalis.jcharts.chartData.processors.RadarChartDataProcessor;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.RadarChartProperties;
import org.krysalis.jcharts.properties.util.ChartFont;


/*************************************************************************************
 * Represents a radar chart a.k.a. spider chart
 *
 * @author Rami Hansenne
 * @version $Id: RadarChart.java,v 1.3 2003/08/28 14:36:28 nicolaken Exp $
 * @since 1.0.0
 ************************************************************************************/

public class RadarChart
	extends Chart
{

	private IRadarChartDataSet iRadarChartDataSet;
	private RadarChartDataProcessor radarChartDataProcessor;
	private RadarChartProperties props;
	private double radius, step;
	private Point2D center;

	// scale max value and increment
	private double scaleMax, scaleIncrement;

	// default number of scale increments if no user defined increment
	private static final int DEFAULT_NR_OF_INCREMENTS = 10;


	/************************************************************************************************
	 * Constructor
	 *
	 * @param iRadarChartDataSet
	 * @param legendProperties
	 * @param chartProperties general chart properties
	 * @param pixelWidth
	 * @param pixelHeight
	 ************************************************************************************************/
	public RadarChart( IRadarChartDataSet iRadarChartDataSet,
							 LegendProperties legendProperties,
							 ChartProperties chartProperties,
							 int pixelWidth,
							 int pixelHeight )
	{
		super( legendProperties, chartProperties, pixelWidth, pixelHeight );
		this.iRadarChartDataSet = iRadarChartDataSet;
		props = (RadarChartProperties) iRadarChartDataSet.getChartTypeProperties();
		if( props == null )
		{
			props = new RadarChartProperties();
		}
	}


	/************************************************************************************************
	 * Draws the chart
	 *
	 ************************************************************************************************/
	protected void renderChart()
	{
		Graphics2D g2d = getGraphics2D();

		// turn anti-aliasing on
		g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );


//todo : render legend, use scale processor classes,...

		FontRenderContext fontRenderContext = super.getGraphics2D().
			getFontRenderContext();

		//---render the TITLE. If no title, this will return zero.
		float chartTitleHeight = super.renderChartTitle( this.iRadarChartDataSet.
																		 getChartTitle(), fontRenderContext );

		this.radarChartDataProcessor = new RadarChartDataProcessor( this.iRadarChartDataSet );
		this.radarChartDataProcessor.processData();

		// set scale max value
		if( Double.isNaN( props.getScaleMaxValue() ) )
			this.scaleMax = radarChartDataProcessor.getMaxValue();
		else
			scaleMax = props.getScaleMaxValue();

		// set scale increment
		if( Double.isNaN( props.getScaleIncrement() ) )
			this.scaleIncrement = scaleMax / DEFAULT_NR_OF_INCREMENTS;
		else
			scaleIncrement = props.getScaleIncrement();

		//todo : adjust chart space in order to correctly render title & legend
		Rectangle chartSpace = new Rectangle( getImageWidth(), getImageHeight() );

		center = new Point2D.Double(
			chartSpace.getWidth() / 2 - chartSpace.getX(),
			chartSpace.getHeight() / 2 - chartSpace.getY() );
		radius = Math.min( chartSpace.getWidth(), chartSpace.getHeight() );
		radius = ( radius - ( radius * 10 ) / 100 ) / 2;
		step = 2 * Math.PI / iRadarChartDataSet.getNumberOfDataItems();

		if( props.getShowGridLines() )
		{
			drawGridLines( g2d );
		}
		drawAxis( g2d );
		drawRadar( g2d );
	}


	private void drawRadar(
		Graphics2D g )
	{

		for( int dataset = 0; dataset < iRadarChartDataSet.getNumberOfDataSets();
			  dataset++ )
		{

			double currentValue;
			double previousValue = scaleValue( iRadarChartDataSet.getValue( dataset,
																								 iRadarChartDataSet.getNumberOfDataItems() -
																								 1 ) );

			Paint paint = iRadarChartDataSet.getPaint( dataset );

			for( int di = 0; di < iRadarChartDataSet.getNumberOfDataItems(); di++ )
			{
				currentValue = previousValue;
				previousValue = scaleValue( iRadarChartDataSet.getValue( dataset, di ) );
				g.setPaint( paint );

				int c0 = (int) Math.round( center.getX() );
				int c1 = (int) Math.round( center.getY() );
				int x0 = getX( di + 1, previousValue );
				int y0 = getY( di + 1, previousValue );
				int x1 = getX( di, currentValue );
				int y1 = getY( di, currentValue );

				if( props.getFillRadar() )
				{
					Polygon p = new Polygon();
					p.addPoint( c0, c1 );
					p.addPoint( x0, y0 );
					p.addPoint( x1, y1 );
					// make color translucent
					if( paint instanceof Color )
					{
						Color color = (Color) paint;
						g.setPaint( new Color( color.getRed(), color.getGreen(),
													  color.getBlue(), 100 ) );
					}
					g.fillPolygon( p );
					g.setPaint( paint );
				}
				g.drawLine( x0, y0, x1, y1 );
			}
		}
	}


	private void drawGridLines( Graphics2D g )
	{
		g.setColor( Color.lightGray );
		for( int di = 0; di < iRadarChartDataSet.getNumberOfDataItems();
			  di++ )
		{
			for( double i = scaleIncrement; i <= scaleMax; i += scaleIncrement )
			{
				double pos = scaleValue( i );
				g.drawLine(
					getX( di + 1, pos ),
					getY( di + 1, pos ),
					getX( di, pos ),
					getY( di, pos ) );
			}
		}
	}


	private void drawAxis( Graphics2D g )
	{
		g.setColor( Color.darkGray );
		for( int index = 0; index < iRadarChartDataSet.getNumberOfDataItems();
			  index++ )
		{
			String label = iRadarChartDataSet.getAxisLabel( index );
			g.drawLine(
				(int) center.getX(),
				(int) center.getY(),
				getX( index, 1 ),
				getY( index, 1 ) );

			Rectangle2D bounds = g.getFontMetrics().getStringBounds( label, g );

			// draw axis label
			ChartFont cfont = props.getTitleChartFont();
			if( cfont != null )
			{
				g.setFont( cfont.getFont() );

			}
			g.drawString(
				label,
				(int) ( getX( index + 1, 1 ) - bounds.getWidth() / 2 ),
				getY( index + 1, 1 ) );
		}

		// draw gridline labels
		g.setColor( Color.darkGray );
		g.setFont( ChartFont.DEFAULT_AXIS_VALUE.getFont() );
		int selectedLine = (int) iRadarChartDataSet.getNumberOfDataItems() / 2;
		for( double i = scaleIncrement; i <= scaleMax; i += scaleIncrement )
		{
			double pos = scaleValue( i );
			Rectangle2D bounds = g.getFont().getStringBounds( "1",
																			  g.getFontRenderContext() );
			g.drawString( props.getGridLabelFormat().format( i ),
							  getX( selectedLine, pos ),
							  (int) Math.round( getY( selectedLine, pos ) + bounds.getHeight() ) );
		}

	}


	private int getX( int dataset, double factor )
	{
		return (int) Math.round( center.getX() + radius * factor * Math.sin( step * dataset ) );
	}


	private int getY( int dataset, double factor )
	{
		return (int) Math.round( center.getY() + radius * factor * Math.cos( step * dataset ) );
	}


	private double scaleValue( double value )
	{
		if( value > scaleMax )
			value = scaleMax;
		if( scaleMax == 0 )
			return 0;
		return value / scaleMax;
	}

}
