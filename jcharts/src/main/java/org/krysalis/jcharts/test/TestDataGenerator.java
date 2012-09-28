/***********************************************************************************************
 * File Info: $Id: TestDataGenerator.java,v 1.4 2004/05/26 01:58:43 nathaniel_auvil Exp $
 * Copyright (C) 2000
 * Author: Nathaniel G. Auvil
 * Contributor(s):
 *
 * Copyright 2002 (C) Nathaniel G. Auvil. All Rights Reserved.
 *
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "jCharts" or "Nathaniel G. Auvil" must not be used to
 * 	  endorse or promote products derived from this Software without
 * 	  prior written permission of Nathaniel G. Auvil.  For written
 *    permission, please contact nathaniel_auvil@users.sourceforge.net
 *
 * 4. Products derived from this Software may not be called "jCharts"
 *    nor may "jCharts" appear in their names without prior written
 *    permission of Nathaniel G. Auvil. jCharts is a registered
 *    trademark of Nathaniel G. Auvil.
 *
 * 5. Due credit should be given to the jCharts Project
 *    (http://jcharts.sourceforge.net/).
 *
 * THIS SOFTWARE IS PROVIDED BY Nathaniel G. Auvil AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * jCharts OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 ************************************************************************************************/


package org.krysalis.jcharts.test;


import org.krysalis.jcharts.properties.*;
import org.krysalis.jcharts.properties.util.ChartStroke;
import org.krysalis.jcharts.properties.util.ChartFont;
import org.krysalis.jcharts.chartData.ScatterPlotDataSet;

import com.google.code.appengine.awt.*;


final public class TestDataGenerator
{
	private final static Font[] ALL_FONTS=GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();


	/*****************************************************************************************
	 * Random font generator based on the available Fonts on this machine.
	 *
	 * @param minSize
	 * @param maxSize
	 * @return Font
	 ******************************************************************************************/
	public static Font getRandomFont( double minSize, double maxSize )
	{
		Font font=ALL_FONTS[ (int) TestDataGenerator.getRandomNumber( ALL_FONTS.length ) ];
		font=font.deriveFont( (float) TestDataGenerator.getRandomNumber( minSize, maxSize ) );
		return font;
	}


	/*****************************************************************************************
	 * Random number generator.
	 *
	 * @param maxValue
	 * @return double
	 ******************************************************************************************/
	public static double getRandomNumber( double maxValue )
	{
		return Math.random() * maxValue;
	}


	/*****************************************************************************************
	 * Random number generator in specified range.
	 *
	 * @param minValue
	 * @param maxValue
	 * @return double
	 ******************************************************************************************/
	protected static double getRandomNumber( double minValue, double maxValue )
	{
		return ( minValue + ( Math.random() * ( maxValue - minValue ) ) );
	}


	/*****************************************************************************************
	 * Random number generator in specified range. Will include zeros a small percentage of
	 * 	the time for testing.
	 *
	 * @param minValue
	 * @param maxValue
	 * @return double
	 ******************************************************************************************/
	protected static double getRandomNumberIncludingZero( double minValue, double maxValue )
	{
		if( ( TestDataGenerator.getRandomNumber( 1 ) > 0.8d ) )
		{
			return 0.0d;
		}
		else
		{
			return getRandomNumber( minValue, maxValue );
		}
	}

	
	/*****************************************************************************************
	 * Random numbers generator in specified range.
	 *
	 * @param numToGenerate the number of doubles to generate
	 * @param minValue
	 * @param maxValue
	 * @return double[]
	 ******************************************************************************************/
	public static double[] getRandomNumbers( int numToGenerate, double minValue, double maxValue )
	{
		double[] data=new double[ numToGenerate ];
		for( int i=0; i < numToGenerate; i++ )
		{
			data[ i ]=getRandomNumber( minValue, maxValue );
		}
		return data;
	}


	/*****************************************************************************************
	 * Random numbers generator in specified range.
	 *
	 * @param numberOfDataSets to generate
	 * @param numToGenerate the number of doubles to generate
	 * @param minValue
	 * @param maxValue
	 * @return double[]
	 ******************************************************************************************/
	public static double[][] getRandomNumbers( int numberOfDataSets, int numToGenerate, double minValue, double maxValue )
	{
		double[][] data=new double[ numberOfDataSets ][ numToGenerate ];
		for( int j=0; j < numberOfDataSets; j++ )
		{
			for( int i=0; i < numToGenerate; i++ )
			{
				data[ j ][ i ]=getRandomNumberIncludingZero( minValue, maxValue );
			}
		}
		return data;
	}



	/*****************************************************************************************
	 * Random numbers generator in specified range.
	 *
	 * @param numToGenerate the number of doubles to generate
	 * @param xMinValue
	 * @param xMaxValue
	 * @param yMinValue
	 * @param yMaxValue
	 * @return Point.Double[]
	 ******************************************************************************************/
	public static Point.Double[] getRandomPoints( int numToGenerate,
																 double xMinValue,
																 double xMaxValue,
																 double yMinValue,
																 double yMaxValue )
	{
		Point.Double[] points= new Point.Double[ numToGenerate ];
		for( int j=0; j < numToGenerate; j++ )
		{
			points[ j ]= ScatterPlotDataSet.createPoint2DDouble();
			points[ j ].setLocation( getRandomNumber( xMinValue, xMaxValue ), getRandomNumber( yMinValue, yMaxValue ) );
		}
		return points;
	}


	/*****************************************************************************************
	 * Random Paint generator.
	 *
	 * @return Paint
	 ******************************************************************************************/
	protected static Paint getRandomPaint()
	{
		if( getRandomNumber( 1 ) > 0.5 )
		{
			return getRandomColor();
		}
		else
		{
			float width=(float) TestDataGenerator.getRandomNumber( 10, 800 );
			float height=(float) TestDataGenerator.getRandomNumber( 10, 600 );
			float x=(float) TestDataGenerator.getRandomNumber( 0, 800 );
			float y=(float) TestDataGenerator.getRandomNumber( 0, 600 );
			return new GradientPaint( x, y, getRandomColor(), width, height, getRandomColor() );
		}
	}


	/*****************************************************************************************
	 * Random Color generator.
	 *
	 * @return Paint[]
	 ******************************************************************************************/
	public static Paint[] getRandomPaints( int numToCreate )
	{
		Paint paints[]=new Paint[ numToCreate ];
		for( int i=0; i < numToCreate; i++ )
		{
			paints[ i ]=getRandomPaint();
		}
		return paints;
	}


	/*****************************************************************************************
	 * Random Color generator.
	 *
	 * @return Color
	 ******************************************************************************************/
	protected static Color getRandomColor()
	{
		int transparency=(int) getRandomNumber( 100, 375 );
		if( transparency > 255 )
		{
			transparency=255;
		}

		return new Color( (int) getRandomNumber( 255 ), (int) getRandomNumber( 255 ), (int) getRandomNumber( 255 ), transparency );
	}


	/*****************************************************************************************
	 * Random String generator.
	 *
	 * @param maxStringLength
	 * @param canBeNull
	 * @return String
	 ******************************************************************************************/
	protected static String getRandomString( int maxStringLength, boolean canBeNull )
	{
		if( canBeNull )
		{
			if( TestDataGenerator.getRandomNumber( 10 ) <= 1 )
			{
				return null;
			}
		}


		int tempVal;

		int stringLength=1 + (int) getRandomNumber( maxStringLength );
		StringBuffer stringBuffer=new StringBuffer( stringLength );

		while( stringLength-- > 0 )
		{
			tempVal=65 + (int) getRandomNumber( 58 );
			while( tempVal > 90 && tempVal < 97 )
			{
				tempVal=65 + (int) getRandomNumber( 58 );
			}

			stringBuffer.append( (char) tempVal );
		}

		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Random String generator.
	 *
	 * @return String[]
	 ******************************************************************************************/
	protected static String[] getRandomStrings( int numToCreate, int maxStringLength, boolean canBeNull )
	{
		if( canBeNull )
		{
			if( (int) TestDataGenerator.getRandomNumber( 10 ) <= 1 )
			{
				return null;
			}
		}

		String strings[]=new String[ numToCreate ];

		for( int i=0; i < numToCreate; i++ )
		{
			strings[ i ]=getRandomString( maxStringLength, false );
		}

		return strings;
	}


	/******************************************************************************************
	 * Takes the passed AxisProperties and randomizes it.
	 *
	 * @param axisProperties
	 ******************************************************************************************/
	protected static void randomizeAxisProperties( AxisProperties axisProperties )
	{
		DataAxisProperties dataAxisProperties;
		LabelAxisProperties labelAxisProperties;
		if( axisProperties.isPlotHorizontal() )
		{
			dataAxisProperties= (DataAxisProperties) axisProperties.getXAxisProperties();
			labelAxisProperties= (LabelAxisProperties) axisProperties.getYAxisProperties();
		}
		else
		{
			dataAxisProperties= (DataAxisProperties) axisProperties.getYAxisProperties();
			labelAxisProperties= (LabelAxisProperties) axisProperties.getXAxisProperties();
		}

		dataAxisProperties.setNumItems( (int) TestDataGenerator.getRandomNumber( 2, 15 ) );
		dataAxisProperties.setRoundToNearest( (int) TestDataGenerator.getRandomNumber( -5, 3 ) );

		dataAxisProperties.setUseDollarSigns( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );
		dataAxisProperties.setUseCommas( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );

		//axisProperties.setShowAxisTitle( AxisProperties.X_AXIS, TestDataGenerator.getRandomNumber( 1 ) > 0.5d );
		//axisProperties.setShowAxisTitle( AxisProperties.Y_AXIS, TestDataGenerator.getRandomNumber( 1 ) > 0.5d );

		//axisProperties.setShowGridLine( AxisProperties.X_AXIS, (int) TestDataGenerator.getRandomNumber( 3 ) );
		//axisProperties.setShowGridLine( AxisProperties.X_AXIS, AxisProperties.GRID_LINES_ONLY_WITH_LABELS );
		//axisProperties.setShowGridLine( AxisProperties.Y_AXIS, (int) TestDataGenerator.getRandomNumber( 3 ) );

		dataAxisProperties.setShowEndBorder( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );
		labelAxisProperties.setShowEndBorder( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );

//		axisProperties.setShowTicks( AxisProperties.X_AXIS, (int) TestDataGenerator.getRandomNumber( 3 ) );
		//axisProperties.setShowTicks( AxisProperties.X_AXIS, AxisProperties.TICKS_ONLY_WITH_LABELS );
		//axisProperties.setShowTicks( AxisProperties.Y_AXIS, (int) TestDataGenerator.getRandomNumber( 3 ) );

		//axisProperties.setShowZeroLine( TestDataGenerator.getRandomNumber( 1 ) > 0.5d );
		//axisProperties.setZeroLinePaint( TestDataGenerator.getRandomPaint() );


//		axisProperties.setScaleFont( TestDataGenerator.getRandomFont( 12.0, 15.0 ) );
		//axisProperties.setScaleFontColor( TestDataGenerator.getRandomPaint() );

		//axisProperties.setAxisTitleFont( TestDataGenerator.getRandomFont( 6.0, 20.0 ) );

		axisProperties.getXAxisProperties().setAxisStroke( new ChartStroke( new BasicStroke( 1.5f ), TestDataGenerator.getRandomPaint() ) );
		axisProperties.getYAxisProperties().setAxisStroke( new ChartStroke( new BasicStroke( 1.5f ), TestDataGenerator.getRandomPaint() ) );


		//axisProperties.setBackgroundPaint( TestDataGenerator.getRandomPaint() );
	}


	/******************************************************************************************
	 * Takes the passed Legend and randomizes it.
	 *
	 * @param legendProperties
	 ******************************************************************************************/
	protected static void randomizeLegend( LegendProperties legendProperties )
	{
		Font font;
		int fontSize;

		int numColumns=(int) TestDataGenerator.getRandomNumber( 1, 6 );
		if( numColumns == 6 )
		{
			numColumns=LegendAreaProperties.COLUMNS_AS_MANY_AS_NEEDED;
		}

		legendProperties.setNumColumns( numColumns );
		legendProperties.setPlacement( (int) TestDataGenerator.getRandomNumber( 4 ) );

		fontSize=(int) TestDataGenerator.getRandomNumber( 6, 20 );
		font=ALL_FONTS[ (int) TestDataGenerator.getRandomNumber( ALL_FONTS.length ) ];
		font=font.deriveFont( (float) fontSize );
		legendProperties.setChartFont( new ChartFont( font, TestDataGenerator.getRandomPaint() ) );


		//---random between null and having a color.
		if( (int) TestDataGenerator.getRandomNumber( 2 ) == 0 )
		{
			legendProperties.setBorderStroke( null );
		}
		else
		{
			legendProperties.setBorderStroke( ChartStroke.DEFAULT_LEGEND_OUTLINE );
		}

		//---random between null and having a color.
		if( (int) TestDataGenerator.getRandomNumber( 2 ) == 0 )
		{
			legendProperties.setBackgroundPaint( null );
		}
		else
		{
			legendProperties.setBackgroundPaint( TestDataGenerator.getRandomPaint() );
		}
	}
}