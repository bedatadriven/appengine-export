/***********************************************************************************************
 * File: HTMLGenerator.java
 * Last Modified: $Id: HTMLGenerator.java,v 1.1 2003/05/17 17:01:11 nathaniel_auvil Exp $
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


import org.krysalis.jcharts.imageMap.ImageMapArea;
import org.krysalis.jcharts.imageMap.ImageMap;

import java.io.FileWriter;
import java.util.Iterator;


/*********************************************************************************************
 * Utility class for viewing a series of tests.
 *
 **********************************************************************************************/
final public class HTMLGenerator
{
	private String fileName;
	private StringBuffer stringBuffer;


	/*****************************************************************************************
	 *
	 * @param fileName the name of the file to write to.
	 ******************************************************************************************/
	public HTMLGenerator( String fileName )
	{
		this.fileName = fileName;
		this.stringBuffer = new StringBuffer( 1024 );
		this.stringBuffer.append( "<HTML><BODY>" );
	}


	/*****************************************************************************************
	 * Adds passed field to table.  Use reflection to get the fields.
	 *
	 * @param name
	 * @param object
	 ******************************************************************************************/
	public void addField( String name, Object object )
	{
		if( object instanceof boolean[] )
		{
			this.addTableRow( name, HTMLGenerator.arrayToString( ( boolean[] ) object ) );
		}
		else if( object instanceof int[] )
		{
			this.addTableRow( name, HTMLGenerator.arrayToString( ( int[] ) object ) );
		}
		else if( object instanceof double[] )
		{
			this.addTableRow( name, HTMLGenerator.arrayToString( ( double[] ) object ) );
		}
		else if( object instanceof float[] )
		{
			this.addTableRow( name, HTMLGenerator.arrayToString( ( float[] ) object ) );
		}
		else if( object instanceof Object[] )
		{
			this.addTableRow( name, HTMLGenerator.arrayToString( ( Object[] ) object ) );
		}
		else
		{
			this.addTableRow( name, object );
		}
	}


	/*****************************************************************************************
	 * Adds a String
	 *
	 ******************************************************************************************/
	public void addString( Object object )
	{
		this.stringBuffer.append( ( object != null ) ? object.toString() : "NULL" );
	}


	/*****************************************************************************************
	 * Adds a String
	 *
	 ******************************************************************************************/
	public void addString( String label, Object object )
	{
		this.addString( "<B>" );
		this.stringBuffer.append( label );
		this.addString( "</B>" );
		this.stringBuffer.append( object.toString() );
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param object
	 ******************************************************************************************/
	public static String arrayToString( Object[] object )
	{
		if( object == null )
		{
			return null;
		}

		StringBuffer stringBuffer = new StringBuffer( 200 );
		for( int i = 0; i < object.length; i++ )
		{
			stringBuffer.append( object[ i ].toString() );
			if( i < object.length - 1 )
			{
				stringBuffer.append( ", " );
			}
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param array
	 ******************************************************************************************/
	public static String arrayToString( boolean[] array )
	{
		StringBuffer stringBuffer = new StringBuffer( 100 );
		for( int i = 0; i < array.length; i++ )
		{
			stringBuffer.append( new Boolean( array[ i ] ).toString() );
			if( i < array.length - 1 )
			{
				stringBuffer.append( ", " );
			}
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param values
	 ******************************************************************************************/
	public static String arrayToString( double[] values )
	{
		StringBuffer stringBuffer = new StringBuffer( 200 );
		for( int i = 0; i < values.length; i++ )
		{
			stringBuffer.append( Double.toString( values[ i ] ) );
			if( i < values.length - 1 )
			{
				stringBuffer.append( ", " );
			}
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param values
	 ******************************************************************************************/
	public static String arrayToString( double[][] values )
	{
		StringBuffer stringBuffer = new StringBuffer( 400 );
		for( int i = 0; i < values.length; i++ )
		{
			stringBuffer.append( " { " );

			for( int j = 0; j < values[ 0 ].length; j++ )
			{
				stringBuffer.append( values[ i ][ j ] );
				if( j < values[ 0 ].length - 1 )
				{
					stringBuffer.append( ", " );
				}
			}

			stringBuffer.append( " }<BR> " );
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param values
	 ******************************************************************************************/
	public static String arrayToString( float[] values )
	{
		StringBuffer stringBuffer = new StringBuffer( 200 );
		for( int i = 0; i < values.length; i++ )
		{
			stringBuffer.append( Float.toString( values[ i ] ) );
			if( i < values.length - 1 )
			{
				stringBuffer.append( ", " );
			}
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an Array
	 *
	 * @param values
	 ******************************************************************************************/
	public static String arrayToString( int[] values )
	{
		StringBuffer stringBuffer = new StringBuffer( 200 );
		for( int i = 0; i < values.length; i++ )
		{
			stringBuffer.append( Integer.toString( values[ i ] ) );
			if( i < values.length - 1 )
			{
				stringBuffer.append( ", " );
			}
		}
		return stringBuffer.toString();
	}


	/*****************************************************************************************
	 * Adds an image
	 *
	 ******************************************************************************************/
	public void addImage( String fileName, ImageMap imageMap )
	{
		this.stringBuffer.append( "<img src=\"" );
		this.stringBuffer.append( fileName );
		this.stringBuffer.append( "\"" );

		if( imageMap != null )
		{
			this.stringBuffer.append( " useMap=\"#" );
			this.stringBuffer.append( fileName );
			this.stringBuffer.append( "\"" );
		}

		this.stringBuffer.append( ">" );

		if( imageMap != null )
		{
			this.addImageMapData( imageMap, fileName );
		}
	}


	private void addImageMapData( ImageMap imageMap, String fileName )
	{
  		this.stringBuffer.append( "<map name=\"" );
		this.stringBuffer.append( fileName );
		this.stringBuffer.append( "\">" );

		Iterator iterator = imageMap.getIterator();
		while( iterator.hasNext() )
		{
			ImageMapArea imageMapArea = ( ImageMapArea ) iterator.next();

			StringBuffer html = new StringBuffer( 50 );
			html.append( "href=\"javascript:alert( 'value= " );
			html.append( imageMapArea.getValue() );
			html.append( ",  legend label= " );
			html.append( imageMapArea.getLengendLabel() );
			html.append( ", axis label= " );
			html.append( imageMapArea.getXAxisLabel() );
			html.append( "');\"" );

			this.stringBuffer.append( imageMapArea.toHTML( html.toString() ) );
		}

		this.stringBuffer.append( "</map>" );
	}


	/*****************************************************************************************
	 * Add line break
	 *
	 ******************************************************************************************/
	public void addLineBreak()
	{
		this.stringBuffer.append( "<BR>" );
	}


	/*****************************************************************************************
	 * Writes the file.
	 *
	 ******************************************************************************************/
	public void saveFile()
	{
		this.stringBuffer.append( "</BODY></HTML>" );

		try
		{
			FileWriter fileWriter = new FileWriter( this.fileName );
			fileWriter.write( this.stringBuffer.toString() );
			fileWriter.flush();
			fileWriter.close();
		}
		catch( Throwable throwable )
		{
			throwable.printStackTrace();
		}
	}


	/*****************************************************************************************
	 *
	 * @param label
	 * @param value
	 ******************************************************************************************/
	public void addTableRow( String label, Object value )
	{
		this.addString( "<TR><TD NOWRAP BGCOLOR=#FFFFFF>" );
		this.addString( label );
		this.addString( "</TD><TD NOWRAP BGCOLOR=#FFFFFF>" );
		this.addString( value );
		this.addString( "</TD></TR>" );
	}


	/*****************************************************************************************
	 *
	 * @param propertiesName
	 ******************************************************************************************/
	public void propertiesTableStart( String propertiesName )
	{
		this.addString( "<TABLE BGCOLOR=#000000 BORDER=0 CELLSPACING=1 CELLPADDING=3>" );
		this.addString( "<TR><TD BGCOLOR=#D0FBCE COLSPAN=2><B>" + propertiesName + "</B></TD></TR>" );
	}


	public void propertiesTableEnd()
	{
		this.addString( "</TABLE>" );
	}


	public void propertiesTableRowStart()
	{
		this.addString( "<TR><TD WIDTH=100% BGCOLOR=#AAAAAA>" );
	}


	public void propertiesTableRowEnd()
	{
		this.addString( "</TD></TR>" );
	}


	/*****************************************************************************************
	 *
	 * @param chartName
	 * @param imageFileName
	 * @param imageMap if this is NULL we are not creating image map data in html
	 ******************************************************************************************/
	public void chartTableStart( String chartName, String imageFileName, ImageMap imageMap )
	{
		this.addString( "<TABLE BGCOLOR=#000000 BORDER=0 CELLSPACING=1 CELLPADDING=3>" );
		this.addString( "<TR><TD BGCOLOR=#FDFEC2 COLSPAN=1><B>" + chartName + "</B></TD></TR>" );
		this.addString( "<TR><TD WIDTH=100% BGCOLOR=#AAAAAA>" );
		this.addImage( imageFileName, imageMap );
		this.addString( "</TD></TR>" );
	}


	public void chartTableEnd()
	{
		this.addString( "</TABLE>" );
	}


	public void chartTableRowStart()
	{
		this.addString( "<TR><TD WIDTH=100% BGCOLOR=#AAAAAA>" );
	}


	public void chartTableRowEnd()
	{
		this.addString( "</TD></TR>" );
	}


	/*****************************************************************************************
	 *
	 ******************************************************************************************/
	public void legendTableStart()
	{
		this.addString( "<TABLE BGCOLOR=#000000 BORDER=0 CELLSPACING=1 CELLPADDING=3>" );
		this.addString( "<TR><TD BGCOLOR=#FDFEC2 COLSPAN=2><B>Legend</B></TD></TR>" );
	}


	public void legendTableEnd()
	{
		this.addString( "</TABLE>" );
	}


	public void innerTableRowStart()
	{
		this.addString( "<TR><TD WIDTH=100% COLSPAN=2 BGCOLOR=#777777>" );
	}


	public void innerTableRowEnd()
	{
		this.addString( "</TD></TR>" );
	}


}