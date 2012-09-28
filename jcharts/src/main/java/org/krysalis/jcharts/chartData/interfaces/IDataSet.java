/***********************************************************************************************
 * File Info: $Id: IDataSet.java,v 1.1 2003/05/17 16:57:26 nathaniel_auvil Exp $
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

package org.krysalis.jcharts.chartData.interfaces;


import org.krysalis.jcharts.properties.ChartTypeProperties;

import com.google.code.appengine.awt.*;


public interface IDataSet extends IData
{

	/******************************************************************************************
	 * Returns the Paint Object for the passed index. This index corresponds to the DataSet
	 *     for which Paint you want.
	 *
	 * @param index
	 * @return Paint
	 *******************************************************************************************/
	public Paint getPaint( int index );


	/******************************************************************************************
	 * Returns ChartTypeProperties Object for the data set which will be typed into the specific
	 *  chart type class.
	 *
	 * @return ChartTypeProperties
	 *******************************************************************************************/
	public ChartTypeProperties getChartTypeProperties();


	/******************************************************************************************
	 * Returns the Legend Label for the passed index. This index corresponds to the DataSet
	 *     for which Label you want.
	 *
	 * @param index
	 * @return String should return NULL of no labels specified
	 *******************************************************************************************/
	public String getLegendLabel( int index );


	/******************************************************************************************
	 * Returns the number of elements in the data set.
	 *
	 * @return int
	 *******************************************************************************************/
	public int getNumberOfDataItems();


	/******************************************************************************************
	 * Returns the number of legend labels in the data set.
	 *
	 * @return int should return 0 if no labels specified
	 *******************************************************************************************/
	public int getNumberOfLegendLabels();


}
