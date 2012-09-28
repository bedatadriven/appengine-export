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
import org.krysalis.jcharts.properties.PieChart3DProperties;
import org.krysalis.jcharts.test.HTMLChartTestable;
import org.krysalis.jcharts.test.HTMLGenerator;
import org.krysalis.jcharts.types.PieLabelType;

import com.google.code.appengine.awt.*;
import com.google.code.appengine.awt.font.FontRenderContext;
import com.google.code.appengine.awt.geom.Arc2D;
import com.google.code.appengine.awt.geom.Line2D;


/*************************************************************************************
 * PieChart3D was originally in jCharts 0.2.0 but mysteriously left until 1.0.0
 *
 * @author Nathaniel Auvil
 * @version $Id: PieChart3D.java,v 1.9 2003/09/03 02:39:09 nathaniel_auvil Exp $
 * @since 1.0.0
 ************************************************************************************/
public class PieChart3D extends Chart implements HTMLChartTestable
{
	private float pieX;
	private float pieY;

	private float pie3dWidth;
	private float pie3dHeight;
	private static final float RATIO = 0.5f;

	private float diameter = 0;


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
	public PieChart3D( IPieChartDataSet iPieChartDataSet,
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
		PieChart3DProperties properties = (PieChart3DProperties) this.iPieChartDataSet.getChartTypeProperties();
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
		heightAvailable -= properties.getDepth();


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

				this.pie3dWidth = this.diameter;
				this.pie3dHeight = this.pie3dWidth * RATIO;


/*
this.pie3dWidth= 200;
this.pie3dHeight= 200;
*/


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
				this.pieY = halfImageHeight - ( ( this.pie3dHeight + properties.getDepth() ) / 2 );
			}
			//---else the legend is either under or on top of the pie
			else
			{
				heightAvailable -= this.getLegend().getHeight();
				heightAvailable -= this.getLegend().getLegendProperties().getChartPadding();

				//---diameter of pie will be at least one pixel, even if the legend takes up the whole image.
				//---this will keep the renderer from blowing up.
				this.diameter = Math.max( heightAvailable, 1.0f );

				//---make sure we do not make the pie diameter wider than the image
				this.diameter = Math.min( this.diameter, widthAvailable );

				this.pie3dWidth = this.diameter;
				this.pie3dHeight = this.pie3dWidth * RATIO;


				if( this.getLegend().getLegendProperties().getPlacement() == LegendAreaProperties.BOTTOM )
				{
					this.pieY = super.getChartProperties().getEdgePadding();
					this.pieY += chartTitleHeightPlusPadding;

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
					legendY += this.pie3dHeight;
					legendY += this.getLegend().getLegendProperties().getChartPadding();
					legendY += properties.getDepth();
				}
				else
				{
					legendY = super.getChartProperties().getEdgePadding();
					legendY += chartTitleHeightPlusPadding;

					this.pieY = legendY;
					this.pieY += this.getLegend().getHeight();
					this.pieY += this.getLegend().getLegendProperties().getChartPadding();

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
			//---diameter of pie will be at least one pixel, even if the legend takes up the whole image.
			//---this will keep the renderer from blowing up.
			this.diameter = Math.max( widthAvailable, 1.0f );

			//---make sure we do not make the pie diameter taller than the image
			this.diameter = Math.min( this.diameter, heightAvailable );

			this.pie3dWidth = this.diameter;
			this.pie3dHeight = this.pie3dWidth * RATIO;


			//---if there is no legend, fill the image with the pie
			//this.diameter = Math.min( heightAvailable, widthAvailable );

			float halfDiameter = ( this.diameter + properties.getDepth() ) / 2;

			//---center the pie horizontally
			this.pieX = halfImageWidth - halfDiameter;

			//---center the pie vertically
			//this.pieY = halfImageHeight - halfDiameter;

			this.pieY = halfImageHeight - ( ( this.pie3dHeight + properties.getDepth() ) / 2 );
		}


		//---IMAGE MAP setup
		//---if we are saving all the coordinates for an ImageMap, create the ImageMap Object as we
		//---	know how many area elements there are.
		if( super.getGenerateImageMapFlag() )
		{
			ImageMap imageMap = new ImageMap( iPieChartDataSet.getNumberOfDataItems() );
			super.setImageMap( imageMap );
		}


		PieChart3D.render( this );
	}


	/************************************************************************************************
	 * Implement the method to render the Chart.
	 *
	 * @param pieChart3D
	 ************************************************************************************************/
	static void render( PieChart3D pieChart3D )
	{
		Graphics2D g2d = pieChart3D.getGraphics2D();
		PieChart3DProperties properties = (PieChart3DProperties) pieChart3D.iPieChartDataSet.getChartTypeProperties();


		//---the following only for Image Map-----------------------------
		//---IMAGE MAP
		//---number of subdivisions to break each slice into to 'fill' slice
		int subdivisions = 3;
		float halfDiameter = 0;
		float xPieMiddle = 0;
		float yPieMiddle = 0;
		float imageMapPoints[][] = null;
		if( pieChart3D.getImageMap() != null )
		{
			halfDiameter = (float) ( pieChart3D.diameter / 2.0 );
			xPieMiddle = halfDiameter + pieChart3D.pieX;
			yPieMiddle = halfDiameter + pieChart3D.pieY;
			imageMapPoints = new float[pieChart3D.iPieChartDataSet.getNumberOfDataItems() * ( subdivisions + 1 )][2];
		}


		//---get the starting degree
		float currentDegrees = properties.getZeroDegreeOffset();

		double percentageOfPie = 0;



		//---if only one item in chart, just draw border around outside.
		//---if do a draw of the arc, will get a line in the pie as arc has a start and end.
		if( pieChart3D.iPieChartDataSet.getNumberOfDataItems() == 1 )
		{
			Arc2D.Double arc = new Arc2D.Double( pieChart3D.pieX,
															 pieChart3D.pieY + properties.getDepth(),
															 pieChart3D.diameter,
															 pieChart3D.diameter,
															 currentDegrees,
															 360,
															 Arc2D.OPEN );


			//---fake 3d loop will slide pie up the image to give illusion of 3D.
			for( int i = properties.getDepth(); i >= 0; i-- )
			{
				g2d.setPaint( pieChart3D.iPieChartDataSet.getPaint( 0 ) );
				g2d.fill( arc );

				if( i == properties.getDepth() || i == 0 )
				{
					properties.getBorderChartStroke().draw( g2d, arc );
				}

				arc.y -= 1;
			}


			//---if only a single value use a circle map
			//---IMAGE MAP
			if( pieChart3D.getImageMap() != null )
			{
				CircleMapArea circleMapArea = new CircleMapArea( xPieMiddle, yPieMiddle, pieChart3D.iPieChartDataSet.getValue( 0 ), null, pieChart3D.iPieChartDataSet.getLegendLabel( 0 ) );
				circleMapArea.setRadius( (int) pieChart3D.diameter );
				pieChart3D.getImageMap().addImageMapArea( circleMapArea );
			}

//			System.out.println( pieChart3D.pieLabels.getTextTag( 0 ).getText() );

			float x = pieChart3D.pieX + ( pieChart3D.diameter / 2 ) - ( pieChart3D.pieLabels.getTextTag( 0 ).getWidth() / 2 );
			float y = pieChart3D.pieY - properties.getTickLength();
//			System.out.println( "x=" + x );
//			System.out.println( "y=" + y );

			properties.getValueLabelFont().setupGraphics2D( g2d );
			g2d.drawString( pieChart3D.pieLabels.getTextTag( 0 ).getText(), x, y );
		}
		else
		{
			Arc2D.Double arc = new Arc2D.Double( pieChart3D.pieX,
															 pieChart3D.pieY + properties.getDepth(),
															 pieChart3D.pie3dWidth,
															 pieChart3D.pie3dHeight,
															 currentDegrees,
															 360,
															 Arc2D.PIE );


			//---draw the center line in case people are using transparent paint
			Line2D.Float line = new Line2D.Float();
			line.x1 = pieChart3D.pieX + ( pieChart3D.pie3dWidth / 2 );
			line.y1 = pieChart3D.pieY + ( pieChart3D.pie3dHeight / 2 );
			line.x2 = line.x1;
			line.y2 = line.y1 + properties.getDepth();
			properties.getBorderChartStroke().draw( g2d, line );


			//---fake 3d loop will slide pie up the image to give illusion of 3D.
			for( int slider = properties.getDepth(); slider >= 0; slider-- )
			{
				currentDegrees = properties.getZeroDegreeOffset();

				//---IMAGE MAP
				int mapCounter = 0;

				for( int i = 0; i < pieChart3D.iPieChartDataSet.getNumberOfDataItems(); i++ )
				{
					percentageOfPie = pieChart3D.pieChartDataProcessor.getPercentageOfPie( i );

					arc.setAngleStart( currentDegrees );
					arc.setAngleExtent( percentageOfPie );

					//---set the color, and fill the pie piece.
					g2d.setPaint( pieChart3D.iPieChartDataSet.getPaint( i ) );
					g2d.fill( arc );


					//---renders borders
					drawFraming( slider, properties, g2d, arc, currentDegrees );


					//---if we are going to display labels
					if( pieChart3D.pieLabels != null && slider == 0 )
					{
						//---get the angle the center of slice
						double sliceCenterDegrees = ( currentDegrees ) + percentageOfPie / 2;

						if( sliceCenterDegrees > 360 )
						{
							sliceCenterDegrees -= 360;
						}



//						double sliceCenterRadians = Math.toRadians( sliceCenterDegrees );


/*

>a2 := a * a;
>b2 := b * b;
>sin2 := Sqr (Sin(theta));
>cos2 := Sqr(Cos(theta));
>r = Sqrt( a2 * b2 / (a2 * sin2 + b2 * cos2) );
*/
/*
						double a2 = pieChart3D.pie3dWidth * pieChart3D.pie3dWidth;
						double b2 = pieChart3D.pie3dHeight * pieChart3D.pie3dHeight;
						double sin2 = Math.pow( Math.sin( sliceCenterRadians ), 2 );
						double cos2 = Math.pow( Math.cos( sliceCenterRadians ), 2 );
						double radius = pieChart3D.pie3dWidth * pieChart3D.pie3dHeight / Math.sqrt( ( a2 * sin2 + b2 * cos2 ) );
						radius /= 2;


						//r= (ab) / ( sqrt( a^2 sin^2 theta + b^2 cos^2 theta ) )

						//---compute the cos and sin of the label angle.
						double cosOfLabel = Math.cos( sliceCenterRadians );
						double sinOfLabel = Math.sin( sliceCenterRadians );

						//halfDiameter = (float) ( pieChart3D.diameter / 2.0 );


						//---start point of the label border line.
						float borderXstart = (float) ( cosOfLabel * radius );
						float borderYstart = (float) -( sinOfLabel * radius );

						//---end point of the label border line.
						float borderXend = (float) ( cosOfLabel * ( radius + properties.getTickLength() ) );
						float borderYend = (float) -( sinOfLabel * ( radius + properties.getTickLength() ) );
*/


						xPieMiddle = (float) arc.x + pieChart3D.pie3dWidth / 2;
						yPieMiddle = (float) arc.y + pieChart3D.pie3dHeight / 2;


						Arc2D.Double circle = new Arc2D.Double( pieChart3D.pieX,
																			 pieChart3D.pieY,
																			 pieChart3D.pie3dWidth,
																			 pieChart3D.pie3dHeight,
																			 currentDegrees,
																			 percentageOfPie / 2,
																			 Arc2D.PIE );
						Arc2D.Double circle2 = new Arc2D.Double( pieChart3D.pieX - properties.getTickLength(),
																			  pieChart3D.pieY - properties.getTickLength(),
																			  pieChart3D.pie3dWidth + properties.getTickLength() * 2,
																			  pieChart3D.pie3dHeight + properties.getTickLength() * 2,
																			  currentDegrees,
																			  percentageOfPie / 2,
																			  Arc2D.PIE );

						//---draw the line out from middle of slice
						properties.getBorderChartStroke().draw( g2d, new Line2D.Float( circle.getEndPoint(), circle2.getEndPoint() ) );

						float borderXend = (float) circle2.getEndPoint().getX();
						float borderYend = (float) circle2.getEndPoint().getY();

						properties.getValueLabelFont().setupGraphics2D( g2d );


						float labelY = borderYend;
						if( sliceCenterDegrees > 60 && sliceCenterDegrees < 120 )
						{
							labelY -= pieChart3D.pieLabels.getTextTag( i ).getFontDescent();
						}
						else if( sliceCenterDegrees > 240 && sliceCenterDegrees < 300 )
						{
							labelY += pieChart3D.pieLabels.getTextTag( i ).getFontAscent();
						}


						if( sliceCenterDegrees > 180 && sliceCenterDegrees < 360 )
						{
							labelY+= properties.getDepth();
						}

						if( sliceCenterDegrees > 90 && sliceCenterDegrees < 270 )
						{
							g2d.drawString( pieChart3D.pieLabels.getTextTag( i ).getText(),
												 borderXend - pieChart3D.pieLabels.getTextTag( i ).getWidth() - properties.getTickLength(),
												 labelY );
						}
						else
						{
							g2d.drawString( pieChart3D.pieLabels.getTextTag( i ).getText(),
												 borderXend + properties.getTickLength(),
												 labelY );
						}
					}


					//---if we are generating an image map...
					//---IMAGE MAP
					if( pieChart3D.getImageMap() != null )
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
				if( pieChart3D.getImageMap() != null )
				{
					//---each slice has 3 + subdivision slices...
					//int counter= pieChart3D.iPieChartDataSet.getNumberOfDataItems() * ( 3 + subdivisions );
					int counter = 0;

					//---for each data item
					for( int i = 0; i < pieChart3D.iPieChartDataSet.getNumberOfDataItems(); i++ )
					{
						int coordinateCounter = 0;

						//---there are three points plus some number of subdivisions...
						PolyMapArea polyMapArea = new PolyMapArea( 3 + subdivisions, pieChart3D.iPieChartDataSet.getValue( i ), null, pieChart3D.iPieChartDataSet.getLegendLabel( i ) );
						polyMapArea.addCoordinate( coordinateCounter++, xPieMiddle, yPieMiddle );

						//---include the first border point, plus the subdivisions
						for( int h = 0; h <= subdivisions; h++ )
						{
							polyMapArea.addCoordinate( coordinateCounter++, imageMapPoints[counter][0], imageMapPoints[counter][1] );
							counter++;
						}

						//---if this is the last slice, add the first calculated map point
						if( ( i + 1 ) == pieChart3D.iPieChartDataSet.getNumberOfDataItems() )
						{
							polyMapArea.addCoordinate( coordinateCounter, imageMapPoints[0][0], imageMapPoints[0][1] );
						}
						//---else add the next calculated point
						else
						{
							polyMapArea.addCoordinate( coordinateCounter, imageMapPoints[counter][0], imageMapPoints[counter][1] );
						}

						pieChart3D.getImageMap().addImageMapArea( polyMapArea );
					}
				}

				//---slide the pie up one pixel
				arc.y -= 1;
			}

/*
			//---draw the edges of the pie
			line.x1= pieChart3D.pieX - 1;
			line.x2= line.x1;
			g2d.draw( line );
			line.x1= pieChart3D.pieX + pieChart3D.pie3dWidth + 1;
			line.x2= line.x1;
			g2d.draw( line );
*/

		}
	}


	/****************************************************************************************
	 *
	 * @param slider
	 * @param properties
	 * @param g2d
	 * @param arc
	 * @param currentDegrees
	 ****************************************************************************************/
	private static void drawFraming( int slider,
												PieChart3DProperties properties,
												Graphics2D g2d,
												Arc2D.Double arc,
												float currentDegrees )
	{
		//---draw the top borders of the pie, and draw the bottom in case there are transparent Paints
		if( slider == properties.getDepth() || slider == 0 )
		{
			properties.getBorderChartStroke().draw( g2d, arc );

			//---draw the vertical borders
			//---in case there are translucent Paints, need to draw the backside borders before the chart.
			if( slider == properties.getDepth() )
			{
				if( currentDegrees > 0 && currentDegrees < 180 )
				{
					properties.getBorderChartStroke().draw( g2d, new Line2D.Double( arc.getStartPoint().getX(),
																										 arc.getStartPoint().getY(),
																										 arc.getStartPoint().getX(),
																										 arc.getStartPoint().getY() - properties.getDepth() ) );
				}
			}
			else if( slider == 0 )
			{
				if( currentDegrees == 0 || ( currentDegrees >= 180 && currentDegrees < 360 ) )
				{
					properties.getBorderChartStroke().draw( g2d, new Line2D.Double( arc.getStartPoint().getX(),
																										 arc.getStartPoint().getY(),
																										 arc.getStartPoint().getX(),
																										 arc.getStartPoint().getY() + properties.getDepth() ) );
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
