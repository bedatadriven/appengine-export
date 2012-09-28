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


import org.krysalis.jcharts.Chart;
import org.krysalis.jcharts.chartData.interfaces.IPieChartDataSet;
import org.krysalis.jcharts.chartData.processors.PieChartDataProcessor;
import org.krysalis.jcharts.imageMap.CircleMapArea;
import org.krysalis.jcharts.imageMap.ImageMap;
import org.krysalis.jcharts.imageMap.PolyMapArea;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendAreaProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PieChart2DProperties;
import org.krysalis.jcharts.test.HTMLChartTestable;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.types.PieLabelType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.geom.Arc2D;
import com.google.code.appengine.awt.geom.Line2D;


/*************************************************************************************
 *
 * @author Nathaniel Auvil
 * @version $Id: PieChart2D.java,v 1.7 2003/05/31 18:17:32 nathaniel_auvil Exp $
 ************************************************************************************/
public class PieChart2D extends Chart implements HTMLChartTestable
{
	private float pieX;
	private float pieY;
	private float diameter;

	private IPieChartDataSet iPieChartDataSet;
	private PieChartDataProcessor pieChartDataProcessor;

	private PieLabels pieLabels;


	/************************************************************************************************
	 * Constructor
	 *
	 * @param iPieChartDataSet
	 * @param legendProperties
	 * @param chartProperties general chart properties
	 * @param pixelWidth
	 * @param pixelHeight
	 ************************************************************************************************/
	public PieChart2D( IPieChartDataSet iPieChartDataSet,
							 LegendProperties legendProperties,
							 ChartProperties chartProperties,
							 int pixelWidth,
							 int pixelHeight )
	{
		super( legendProperties, chartProperties, pixelWidth, pixelHeight );
		this.iPieChartDataSet = iPieChartDataSet;
	}


	/************************************************************************************************
	 * Draws the chart
	 *
	 ************************************************************************************************/
	protected void renderChart()
	{
		PieChart2DProperties properties = (PieChart2DProperties) this.iPieChartDataSet.getChartTypeProperties();
		FontRenderContext fontRenderContext = super.getGraphics2D().getFontRenderContext();

		this.pieChartDataProcessor = new PieChartDataProcessor( this.iPieChartDataSet );
		this.pieChartDataProcessor.processData();


		//---cache calcs used more than once
		float edgePaddingTimesTwo = super.getChartProperties().getEdgePadding() * 2;
		float halfImageWidth = super.getImageWidth() / 2;
		float halfImageHeight = super.getImageHeight() / 2;


		//---render the TITLE. If no title, this will return zero.
		float chartTitleHeightPlusPadding = super.renderChartTitle( this.iPieChartDataSet.getChartTitle(), fontRenderContext );


		//---figure out what size is needed to hold all the components of the pie chart, then size the pie accordingly
		float widthAvailable = super.getImageWidth() - edgePaddingTimesTwo;
		float heightAvailable = super.getImageHeight() - edgePaddingTimesTwo;
		heightAvailable -= chartTitleHeightPlusPadding;


		//---take labels sizing into consideration if needed.
		if( !properties.getPieLabelType().equals( PieLabelType.NO_LABELS ) )
		{
			this.pieLabels = new PieLabels( properties, this.iPieChartDataSet, fontRenderContext );

			//---if there is only one item in pie, label will be below plot so width is not a concern
			if( iPieChartDataSet.getNumberOfDataItems() != 1 )
			{
				widthAvailable -= this.pieLabels.getWidestLabelTimesTwo();
				widthAvailable -= ( properties.getTickLength() * 2 );
			}

			heightAvailable -= this.pieLabels.getTallestLabelTimesTwo();
			heightAvailable -= ( properties.getTickLength() * 2 );
		}


		//---if there is a legend...
		if( this.getLegend() != null )
		{
			float legendX = 0f;
			float legendY = 0f;

			//---calculate all the legend rendering coordinates and positions.
			this.getLegend().calculateDrawingValues( iPieChartDataSet );


			//---adjust width and height based on the Legend size.
			if( ( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.RIGHT )
				|| ( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.LEFT ) )
			{
				widthAvailable -= this.getLegend().getWidth();
				widthAvailable -= this.getLegend().getLegendProperties().getChartPadding();


				//---diameter of pie will be at least one pixel, even if the legend takes up the whole image.
				//---this will keep the renderer from blowing up.
				this.diameter = Math.max( widthAvailable, 1.0f );

				//---make sure we do not make the pie diameter taller than the image
				this.diameter = Math.min( this.diameter, heightAvailable );


				//---calculate the entire width of everything to be drawn so can center everything
				float plotWidth = this.diameter;
				plotWidth += this.getLegend().getWidth();
				plotWidth += this.getLegend().getLegendProperties().getChartPadding();
				if( this.pieLabels != null )
				{
					plotWidth += ( this.pieLabels.getWidestLabel() * 2 );
					plotWidth += ( properties.getTickLength() * 2 );
				}

				if( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.RIGHT )
				{
					//---pie's diameter may not fill image width as may be image height constrained.
					this.pieX = halfImageWidth - ( plotWidth / 2 );

					if( this.pieLabels != null )
					{
						this.pieX += this.pieLabels.getWidestLabel();
						this.pieX += properties.getTickLength();
						legendX += this.pieLabels.getWidestLabel();
						legendX += properties.getTickLength();
					}

					//---position legend based on the pie position
					legendX += this.pieX + this.diameter;
					legendX += this.getLegend().getLegendProperties().getChartPadding();
				}
				else
				{
					legendX = halfImageWidth - ( plotWidth / 2 );

					if( this.pieLabels != null )
					{
						this.pieX = legendX;
						this.pieX += this.getLegend().getWidth();
						this.pieX += this.getLegend().getLegendProperties().getChartPadding();
						this.pieX += this.pieLabels.getWidestLabel();
						this.pieX += properties.getTickLength();
					}
				}

				//---center the legend vertically
				legendY = halfImageHeight - ( this.getLegend().getHeight() / 2 );

				//---center the pie vertically
				this.pieY = halfImageHeight - ( this.diameter / 2 );
			}
			//---else the legend is either under or on top of the pie
			else
			{
				heightAvailable-= this.getLegend().getHeight();
				heightAvailable-= this.getLegend().getLegendProperties().getChartPadding();

				//---diameter of pie will be at least one pixel, even if the legend takes up the whole image.
				//---this will keep the renderer from blowing up.
				this.diameter = Math.max( heightAvailable, 1.0f );

				//---make sure we do not make the pie diameter wider than the image
				this.diameter = Math.min( this.diameter, widthAvailable );


				if( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.BOTTOM )
				{
					this.pieY = super.getChartProperties().getEdgePadding();
					this.pieY += chartTitleHeightPlusPadding;

					legendY+= this.diameter;

					if( this.pieLabels != null )
					{
						//---adds label height from top of pie
						this.pieY += this.pieLabels.getTallestLabel();
						this.pieY += properties.getTickLength();

						//---add label hight from bottom of pie
						legendY += this.pieLabels.getTallestLabel();
						legendY += properties.getTickLength();
					}

					legendY += this.pieY;
					legendY+= this.getLegend().getLegendProperties().getChartPadding();
				}
				else
				{
					legendY = super.getChartProperties().getEdgePadding();
					legendY += chartTitleHeightPlusPadding;

					this.pieY= legendY;
					this.pieY+= this.getLegend().getHeight();
					this.pieY+= this.getLegend().getLegendProperties().getChartPadding();

					if( this.pieLabels != null )
					{
						//---adds label height from top of pie
						this.pieY += this.pieLabels.getTallestLabel();
						this.pieY += properties.getTickLength();
					}
				}

				//---center the legend horizontally
				legendX = halfImageWidth - ( this.getLegend().getWidth() / 2 );

				//---center the pie horizontally
				this.pieX = halfImageWidth - ( this.diameter / 2 );
			}

			super.getLegend().setX( legendX );
			super.getLegend().setY( legendY );
			super.getLegend().render();

		}
		//---else, the Legend is NULL
		else
		{
			//---if there is no legend, fill the image with the pie
			this.diameter = Math.min( heightAvailable, widthAvailable );

			float halfDiameter = this.diameter / 2;

			//---center the pie horizontally
			this.pieX = halfImageWidth - halfDiameter;

			//---center the pie vertically
			this.pieY = halfImageHeight - halfDiameter;
		}


		//---IMAGE MAP setup
		//---if we are saving all the coordinates for an ImageMap, create the ImageMap Object as we
		//---	know how many area elements there are.
		if( super.getGenerateImageMapFlag() )
		{
			ImageMap imageMap = new ImageMap( iPieChartDataSet.getNumberOfDataItems() );
			super.setImageMap( imageMap );
		}


		PieChart2D.render( this );
	}


	/************************************************************************************************
	 * Implement the method to render the Chart.
	 *
	 * @param pieChart2D
	 ************************************************************************************************/
	static void render( PieChart2D pieChart2D )
	{
		Graphics2D g2d = pieChart2D.getGraphics2D();
		PieChart2DProperties properties = (PieChart2DProperties) pieChart2D.iPieChartDataSet.getChartTypeProperties();

		//---set the border Stroke
		properties.getBorderChartStroke().setupGraphics2D( g2d );


		//---the following only for Image Map-----------------------------
		//---IMAGE MAP
		//---number of subdivisions to break each slice into to 'fill' slice
		int subdivisions = 3;
		float halfDiameter = 0;
		float xPieMiddle = 0;
		float yPieMiddle = 0;
		float imageMapPoints[][] = null;
		if( pieChart2D.getImageMap() != null )
		{
			halfDiameter = (float) ( pieChart2D.diameter / 2.0 );
			xPieMiddle = halfDiameter + pieChart2D.pieX;
			yPieMiddle = halfDiameter + pieChart2D.pieY;
			imageMapPoints = new float[pieChart2D.iPieChartDataSet.getNumberOfDataItems() * ( subdivisions + 1 )][2];
		}


		//---get the starting degree
		float currentDegrees = properties.getZeroDegreeOffset();

		double percentageOfPie = 0;



		//---if only one item in chart, just draw border around outside.
		//---if do a draw of the arc, will get a line in the pie as arc has a start and end.
		if( pieChart2D.iPieChartDataSet.getNumberOfDataItems() == 1 )
		{
			Arc2D.Double arc = new Arc2D.Double( pieChart2D.pieX,
															 pieChart2D.pieY,
															 pieChart2D.diameter,
															 pieChart2D.diameter,
															 currentDegrees,
															 360,
															 Arc2D.OPEN );

			g2d.setPaint( pieChart2D.iPieChartDataSet.getPaint( 0 ) );
			g2d.fill( arc );

			properties.getBorderChartStroke().draw( g2d, arc );

			//---if only a single value use a circle map
			//---IMAGE MAP
			if( pieChart2D.getImageMap() != null )
			{
				CircleMapArea circleMapArea = new CircleMapArea( xPieMiddle, yPieMiddle, pieChart2D.iPieChartDataSet.getValue( 0 ), null, pieChart2D.iPieChartDataSet.getLegendLabel( 0 ) );
				circleMapArea.setRadius( (int) pieChart2D.diameter );
				pieChart2D.getImageMap().addImageMapArea( circleMapArea );
			}

//			System.out.println( pieChart2D.pieLabels.getTextTag( 0 ).getText() );

			if( pieChart2D.pieLabels != null )
			{
				float x = pieChart2D.pieX + ( pieChart2D.diameter / 2 ) - ( pieChart2D.pieLabels.getTextTag( 0 ).getWidth() / 2 );
				float y = pieChart2D.pieY - properties.getTickLength();
	//			System.out.println( "x=" + x );
	//			System.out.println( "y=" + y );

				properties.getValueLabelFont().setupGraphics2D( g2d );
				g2d.drawString( pieChart2D.pieLabels.getTextTag( 0 ).getText(), x, y );
			}
		}
		else
		{
			Arc2D.Double arc = new Arc2D.Double( pieChart2D.pieX,
															 pieChart2D.pieY,
															 pieChart2D.diameter,
															 pieChart2D.diameter,
															 currentDegrees,
															 360,
															 Arc2D.PIE );
			//---IMAGE MAP
			int mapCounter = 0;

			for( int i = 0; i < pieChart2D.iPieChartDataSet.getNumberOfDataItems(); i++ )
			{
				percentageOfPie = pieChart2D.pieChartDataProcessor.getPercentageOfPie( i );

				arc.setAngleStart( currentDegrees );
				arc.setAngleExtent( percentageOfPie );

				//---set the color, and fill the pie piece.
				g2d.setPaint( pieChart2D.iPieChartDataSet.getPaint( i ) );
				g2d.fill( arc );

				properties.getBorderChartStroke().draw( g2d, arc );


				//---if we are going to display labels
				if( pieChart2D.pieLabels != null )
				{
					//---get the angle the center of slice
					double sliceCenterDegrees = ( currentDegrees ) + percentageOfPie / 2;

					if( sliceCenterDegrees > 360 )
					{
						sliceCenterDegrees -= 360;
					}


					double sliceCenterRadians = Math.toRadians( sliceCenterDegrees );

					//---compute the cos and sin of the label angle.
					double cosOfLabel = Math.cos( sliceCenterRadians );
					double sinOfLabel = Math.sin( sliceCenterRadians );

					halfDiameter = (float) ( pieChart2D.diameter / 2.0 );


					//---end point of the label border line.
					float borderXstart = (float) ( cosOfLabel * halfDiameter );
					float borderYstart = (float) -( sinOfLabel * halfDiameter );

					//---end point of the label border line.
					float borderXend = (float) ( cosOfLabel * ( halfDiameter + properties.getTickLength() ) );
					float borderYend = (float) -( sinOfLabel * ( halfDiameter + properties.getTickLength() ) );


					xPieMiddle = halfDiameter + pieChart2D.pieX;
					yPieMiddle = halfDiameter + pieChart2D.pieY;

					properties.getValueLabelFont().setupGraphics2D( g2d );

					g2d.draw( new Line2D.Double( xPieMiddle + borderXstart,
														  yPieMiddle + borderYstart,
														  xPieMiddle + borderXend,
														  yPieMiddle + borderYend ) );

//System.out.println( pieChart2D.textTagGroup.getTextTag( i ).getText() + "  sliceCenterDegrees= " + sliceCenterDegrees );

					float labelY = yPieMiddle + borderYend;
					if( sliceCenterDegrees > 60 && sliceCenterDegrees < 120 )
					{
						labelY -= pieChart2D.pieLabels.getTextTag( i ).getFontDescent();
					}
					else if( sliceCenterDegrees > 240 && sliceCenterDegrees < 300 )
					{
						labelY += pieChart2D.pieLabels.getTextTag( i ).getFontAscent();
					}


					if( sliceCenterDegrees > 90 && sliceCenterDegrees < 270 )
					{
						g2d.drawString( pieChart2D.pieLabels.getTextTag( i ).getText(),
											 xPieMiddle + borderXend - pieChart2D.pieLabels.getTextTag( i ).getWidth() - properties.getTickLength(),
											 labelY );
					}
					else
					{
						g2d.drawString( pieChart2D.pieLabels.getTextTag( i ).getText(),
											 xPieMiddle + borderXend + properties.getTickLength(),
											 labelY );
					}
				}


				//---if we are generating an image map...
				//---IMAGE MAP
				if( pieChart2D.getImageMap() != null )
				{
					//---increment a separate amount to minimize rounding errors.
					double workDegrees = currentDegrees;

					//---compute the cos and sin of the bodrder angle.
					double cosOfBorder;
					double sinOfBorder;
					double splitDegree = percentageOfPie / subdivisions;

					for( int j = 0; j <= subdivisions; j++ )
					{
						cosOfBorder = Math.cos( Math.toRadians( workDegrees ) );
						sinOfBorder = Math.sin( Math.toRadians( workDegrees ) );

						//---end point of the slice border line.
						imageMapPoints[mapCounter][0] = xPieMiddle + (float) ( cosOfBorder * halfDiameter );
						imageMapPoints[mapCounter][1] = yPieMiddle + (float) -( sinOfBorder * halfDiameter );

						//DEBUG to make sure calculating points correctly
						//g2d.setPaint( Color.red );
						//g2d.fillRect( (int) imageMapPoints[ mapCounter ][ 0 ], (int) imageMapPoints[ mapCounter ][ 1 ], 6, 6 );

						mapCounter++;
						workDegrees += splitDegree;
					}
				}

				currentDegrees += percentageOfPie;
			}


			//---if we are generating an image map...
			//---IMAGE MAP
			if( pieChart2D.getImageMap() != null )
			{
				//---each slice has 3 + subdivision slices...
				//int counter= pieChart2D.iPieChartDataSet.getNumberOfDataItems() * ( 3 + subdivisions );
				int counter = 0;

				//---for each data item
				for( int i = 0; i < pieChart2D.iPieChartDataSet.getNumberOfDataItems(); i++ )
				{
					int coordinateCounter = 0;

					//---there are three points plus some number of subdivisions...
					PolyMapArea polyMapArea = new PolyMapArea( 3 + subdivisions, pieChart2D.iPieChartDataSet.getValue( i ), null, pieChart2D.iPieChartDataSet.getLegendLabel( i ) );
					polyMapArea.addCoordinate( coordinateCounter++, xPieMiddle, yPieMiddle );

					//---include the first border point, plus the subdivisions
					for( int h = 0; h <= subdivisions; h++ )
					{
						polyMapArea.addCoordinate( coordinateCounter++, imageMapPoints[counter][0], imageMapPoints[counter][1] );
						counter++;
					}

					//---if this is the last slice, add the first calculated map point
					if( ( i + 1 ) == pieChart2D.iPieChartDataSet.getNumberOfDataItems() )
					{
						polyMapArea.addCoordinate( coordinateCounter, imageMapPoints[0][0], imageMapPoints[0][1] );
					}
					//---else add the next calculated point
					else
					{
						polyMapArea.addCoordinate( coordinateCounter, imageMapPoints[counter][0], imageMapPoints[counter][1] );
					}

					pieChart2D.getImageMap().addImageMapArea( polyMapArea );
				}
			}
		}
	}


	/**********************************************************************************************
	 * Enables the testing routines to display the contents of this Object. Override Chart
	 *  implementation as PieCharts use AreaProperties directly rather than a child.
	 *
	 * @param htmlGenerator
	 * @param imageFileName
	 **********************************************************************************************/
	public void toHTML( HTMLGenerator htmlGenerator, String imageFileName )
	{
		if( this.getLegend() != null )
		{
			htmlGenerator.chartTableRowStart();
			this.getLegend().toHTML( htmlGenerator );
			htmlGenerator.chartTableRowEnd();
		}

		htmlGenerator.chartTableEnd();
	}

}
