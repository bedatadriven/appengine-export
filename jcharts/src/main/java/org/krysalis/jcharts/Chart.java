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

package org.krysalis.jcharts;


import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.test.HTMLGenerator;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.font.LineBreakMeasurer;
import com.google.code.appengine.awt.font.TextAttribute;
import com.google.code.appengine.awt.font.TextLayout;
import com.google.code.appengine.awt.geom.Rectangle2D;
import com.google.code.appengine.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.AttributedString;


/*************************************************************************************
 * Base class of all charts.
 *
 * @author Nathaniel Auvil, Sandor Dornbush
 * @version $Id: Chart.java,v 1.3 2003/06/21 19:44:35 nathaniel_auvil Exp $
 ************************************************************************************/
public abstract class Chart implements Serializable
{
	//---Be aware that the calls BufferedImage.getGraphics() and BufferedImage.createGraphics(), actually
	//--- create a new Graphics2D Object on each invocation.  So keep a reference here.
	private Graphics2D graphics2D;

	private int width;
	private int height;

	private Legend legend;

	//---general properties of all chart types.
	private ChartProperties chartProperties;


	//---used to generate an image map for the image
	private boolean generateImageMap = false;
	private ImageMap imageMap = null;
	private BufferedImage bufferedImage = null;


	/******************************************************************************************
	 * Constructor
	 *
	 * @param legendProperties
	 * @param chartProperties
	 * @param pixelWidth
	 * @param pixelHeight
	 *******************************************************************************************/
	public Chart( LegendProperties legendProperties, ChartProperties chartProperties, int pixelWidth, int pixelHeight )
	{
		this.width = pixelWidth;
		this.height = pixelHeight;
		this.chartProperties = chartProperties;

		if( legendProperties != null )
		{
			this.legend = new Legend( this, legendProperties );
			legendProperties.setSize( new Dimension( width, height ) );
		}
	}


	/******************************************************************************************
	 * Returns flag indicating whether to generate an ImageMap
	 *
	 * @return boolean
	 *******************************************************************************************/
	public boolean getGenerateImageMapFlag()
	{
		return this.generateImageMap;
	}


	/******************************************************************************************
	 * Returns the BufferedImage used to generate the ImageMap.  Only should be called on
	 * 	binary format images, such as PNG and JPG, as it will not work on SVG.
	 *
	 * This is a HACK and the design of jCharts should do better than this!!!!!!
	 *
	 * @return BufferedImage
	 *******************************************************************************************/
	public BufferedImage getBufferedImage()
	{
		return this.bufferedImage;
	}


	/******************************************************************************************
	 * Renders the chart into a BufferedImage so that we can calculate all the Image Map
	 *	coordinates.
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 *******************************************************************************************/
	public void renderWithImageMap() throws ChartDataException, PropertyException
	{
		this.bufferedImage = new BufferedImage( this.getImageWidth(), this.getImageHeight(), BufferedImage.TYPE_INT_RGB );
		this.setGraphics2D( this.bufferedImage.createGraphics() );
		this.generateImageMap = true;

		this.render();
	}


	/******************************************************************************************
	 * Call this to kick off rendering of the chart
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 *******************************************************************************************/
	public void render() throws ChartDataException, PropertyException
	{
		if( this.chartProperties.useAntiAliasing() )
		{
			this.graphics2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		}
		else
		{
			this.graphics2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
		}


		Rectangle2D.Float rectangle = new Rectangle2D.Float( 0, 0, this.width, this.height );

		//---fill the background
		this.graphics2D.setPaint( this.chartProperties.getBackgroundPaint() );
		this.graphics2D.fill( rectangle );

		//---draw a border around the chart if desired
		if( this.chartProperties.getBorderStroke() != null )
		{
			//---there is a one pixel difference when you fill versus draw a rectangle
			rectangle.width -= 1;
			rectangle.height -= 1;
			this.chartProperties.getBorderStroke().draw( this.graphics2D, rectangle );
		}

		//---draw the chart
		this.renderChart();
	}


	/*************************************************************************************************
	 * Displays the chart title and returns the height of the title PLUS title padding.
	 *
	 * @param chartTitle
	 * @param fontRenderContext
	 * @return float the height required by the title. If no title is displayed, zero is returned.
	 **************************************************************************************************/
	protected float renderChartTitle( String chartTitle, FontRenderContext fontRenderContext )
	{
		float height = 0;

		if( chartTitle != null )
		{
			//---the y value of where to write the current line
			float currentLine = this.getChartProperties().getEdgePadding();

			// change title into an AttributedString with the font as an attribute
			AttributedString s = new AttributedString( chartTitle );
			s.addAttribute( TextAttribute.FONT, this.getChartProperties().getTitleFont().getFont() );

			//---get LineBreakMeasurer on the attributed string...it will break the text for us
			LineBreakMeasurer measurer = new LineBreakMeasurer( s.getIterator(), fontRenderContext );

			//---set the text color
			this.getGraphics2D().setPaint( this.getChartProperties().getTitleFont().getPaint() );

			//---draw each title line.  Subtract padding from each side for printable width.
			float wrappingWidth= this.getImageWidth() - ( this.getChartProperties().getEdgePadding() * 2 );

			TextLayout titleTextLayout= null;
			while( ( titleTextLayout = measurer.nextLayout( wrappingWidth ) ) != null )
			{
				//---set the current line to where the title will be written
				currentLine += titleTextLayout.getAscent();

				titleTextLayout.draw( this.getGraphics2D(),
											 ( ( this.getImageWidth() - titleTextLayout.getAdvance() ) / 2 ),
											 currentLine );

				//---keep track of total height of all the title lines
				height +=  titleTextLayout.getAscent() + titleTextLayout.getDescent();
			}

			//---add in the padding between the title and the top of the chart.
			height += this.getChartProperties().getTitlePadding();
		}

		return height;
	}


	/******************************************************************************************
	 *
	 * @throws ChartDataException
	 * @throws PropertyException
	 *******************************************************************************************/
	abstract protected void renderChart() throws ChartDataException, PropertyException;


	/******************************************************************************************
	 * Returns the BufferedImage width
	 *
	 * @return int
	 *******************************************************************************************/
	public final int getImageWidth()
	{
		return this.width;
	}


	/******************************************************************************************
	 * Returns the BufferedImage height
	 *
	 * @return int
	 *******************************************************************************************/
	public final int getImageHeight()
	{
		return this.height;
	}


	/******************************************************************************************
	 * Returns the general properties Object.
	 *
	 * @return ChartProperties
	 *******************************************************************************************/
	public final ChartProperties getChartProperties()
	{
		return this.chartProperties;
	}


	/******************************************************************************************
	 * Returns the Legend.  Will be NULL if no Legend is desired.
	 *
	 * @return Legend
	 *******************************************************************************************/
	protected final Legend getLegend()
	{
		return this.legend;
	}


	/******************************************************************************************
	 * Returns flag indicating if there is a Legend.
	 *
	 * @return boolean
	 *******************************************************************************************/
	public final boolean hasLegend()
	{
		return this.legend != null;
	}


	/******************************************************************************************
	 * Sets the graphics object to render the chart on by the encoder.
	 *
	 * @param graphics2D
	 *******************************************************************************************/
	public final void setGraphics2D( Graphics2D graphics2D )
	{
		this.graphics2D = graphics2D;
	}


	/******************************************************************************************
	 * Shortcut method to get Graphics2D. Be aware that the call BufferedImage.getGraphics()
	 *  and BufferedImage.createGraphics(), actually create a new Grpahics2D Object on each
	 *  invocation. This returns the member reference so calls to this are not creating a new
	 *  Object each time.
	 *
	 * @return Graphics2D
	 *******************************************************************************************/
	public final Graphics2D getGraphics2D()
	{
		return this.graphics2D;
	}


	/**********************************************************************************************
	 * To optimze performance of the ImageMap Object, we create it once we know how many data
	 *  elements are in the chart which is dependent on the AxisChart or PieChart2D
	 *
	 * @param imageMap
	 **********************************************************************************************/
	public final void setImageMap( ImageMap imageMap )
	{
		this.imageMap = imageMap;
	}


	/**********************************************************************************************
	 *
	 * @return imageMap
	 **********************************************************************************************/
	public final ImageMap getImageMap()
	{
		return this.imageMap;
	}


	/**********************************************************************************************
	 * Enables the testing routines to display the contents of this Object.
	 *
	 * @param htmlGenerator
	 * @param imageFileName
	 * @param imageMap if this is NULL we are not creating image map data in html
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator, String imageFileName, ImageMap imageMap )
	{
		htmlGenerator.chartTableStart( this.getClass().getName(), imageFileName, imageMap );

		htmlGenerator.chartTableRowStart();
		this.chartProperties.toHTML( htmlGenerator );
		htmlGenerator.chartTableRowEnd();

		if( this.legend != null )
		{
			htmlGenerator.chartTableRowStart();
			this.getLegend().toHTML( htmlGenerator );
			htmlGenerator.chartTableRowEnd();
		}

		htmlGenerator.chartTableEnd();
	}


}
