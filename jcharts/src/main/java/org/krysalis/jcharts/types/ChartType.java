/***********************************************************************************************
 * File Info: $Id: ChartType.java,v 1.2 2004/05/31 15:38:56 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.types;


/*****************************************************************************************
 * Chart IntType constants to be used on data sets to specify how they should be plotted.
 *
 ******************************************************************************************/
public class ChartType extends IntType
{
	public static final ChartType BAR=new ChartType( 0, false );
	public static final ChartType BAR_CLUSTERED=new ChartType( 1, false );
	public static final ChartType BAR_STACKED=new ChartType( 2, true );
	public static final ChartType LINE=new ChartType( 3, false );
	public static final ChartType POINT=new ChartType( 4, false );
	public static final ChartType AREA=new ChartType( 5, false );
	public static final ChartType AREA_STACKED=new ChartType( 6, true );
	public static final ChartType STOCK=new ChartType( 7, false );
   public static final ChartType SCATTER_PLOT=new ChartType( 8, false );


	private boolean stackedData;


	/***************************************************************************************
	 *
	 * @param typeCode
	 * @param stackedData
	 ****************************************************************************************/
	private ChartType( int typeCode, boolean stackedData )
	{
		super( typeCode );

		this.stackedData=stackedData;
	}


	/***************************************************************************************
	 * Returns flag indicating whether chart type has stacked data
	 *
	 * @return boolean
	 ****************************************************************************************/
	public boolean isStacked()
	{
		return this.stackedData;
	}


	/***************************************************************************************
	 * Returns flag indicating whether passed ChartType is equal to this one
	 *
	 * @param chartType
	 * @return boolean
	 ****************************************************************************************/
	public boolean equals( ChartType chartType )
	{
		return ( this.getInt() == chartType.getInt() );
	}

}
