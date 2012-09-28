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


import org.krysalis.jcharts.types.PieLabelType;
import org.krysalis.jcharts.chartText.NumericTagGroup;
import org.krysalis.jcharts.chartText.TextTagGroup;
import org.krysalis.jcharts.chartText.TextTag;
import org.krysalis.jcharts.properties.PieChart2DProperties;
import org.krysalis.jcharts.chartData.interfaces.IPieChartDataSet;

import com.google.code.appengine.awt.font.FontRenderContext;


/*************************************************************************************
 * Package private Class used to hold values for the Pie Chart Labels
 *
 * @author Nathaniel Auvil
 * @version $Id: PieLabels.java,v 1.1 2003/05/18 20:28:35 nathaniel_auvil Exp $
 ************************************************************************************/
class PieLabels
{
   private TextTagGroup textTagGroup;

	private float widestLabel;
	private float widestLabelTimesTwo;
	private float tallestLabel;
	private float tallestLabelTimesTwo;


	/***********************************************************************************
	 *
	 * @param properties
	 * @param iPieChartDataSet
	 * @param fontRenderContext
	 **********************************************************************************/
	PieLabels( PieChart2DProperties properties,
				  IPieChartDataSet iPieChartDataSet,
				  FontRenderContext fontRenderContext )
	{
		//---if we want to display value labels on the chart need to determine the width this will take
		if( !properties.getPieLabelType().equals( PieLabelType.NO_LABELS ) )
		{
			if( properties.getPieLabelType().equals( PieLabelType.VALUE_LABELS ) )
			{
				this.textTagGroup = new NumericTagGroup( properties.getValueLabelFont(),
																	  fontRenderContext,
																	  properties.showValueLabelCurrency(),
																	  false,
																	  properties.showValueLabelGrouping(),
																	  properties.getValueLabelRoundingPowerOfTen() );

				for( int i = 0; i < iPieChartDataSet.getNumberOfDataItems(); i++ )
				{
					((NumericTagGroup) this.textTagGroup).addLabel( iPieChartDataSet.getValue( i ) );
				}
			}
			//---PieLabelType.LEGEND_LABELS
			else
			{
				this.textTagGroup = new TextTagGroup( properties.getValueLabelFont(), fontRenderContext );
				for( int i = 0; i < iPieChartDataSet.getNumberOfLegendLabels(); i++ )
				{
					this.textTagGroup.addLabel( iPieChartDataSet.getLegendLabel( i ) );
				}
			}

			widestLabel = this.textTagGroup.getWidestLabel();
			widestLabelTimesTwo = widestLabel * 2;
			tallestLabel = this.textTagGroup.getTallestLabel();
			tallestLabelTimesTwo = tallestLabel * 2;
		}

	}


	public TextTag getTextTag( int index )
	{
		return this.textTagGroup.getTextTag( index );
	}


	public float getWidestLabelTimesTwo()
	{
		return widestLabelTimesTwo;
	}


	public float getWidestLabel()
	{
		return widestLabel;
	}


	public float getTallestLabel()
	{
		return tallestLabel;
	}


	public float getTallestLabelTimesTwo()
	{
		return tallestLabelTimesTwo;
	}

}
