
package org.krysalis.jcharts.chartData.processors;

import org.krysalis.jcharts.chartData.interfaces.IRadarChartDataSet;




/**
 * Utility class to process the RadarChartDataSet
 *
 * @author Rami Hansenne
 */
final public class RadarChartDataProcessor
{
        private IRadarChartDataSet iRadarChartDataSet;

        private double minValue = Double.MAX_VALUE;
        private double maxValue = Double.MIN_VALUE;

        /******************************************************************************************
         * Constructor
         *
         * @param iRadarChartDataSet
         *******************************************************************************************/
        public RadarChartDataProcessor( IRadarChartDataSet iRadarChartDataSet )
        {
                this.iRadarChartDataSet = iRadarChartDataSet;
        }


        /*******************************************************************************************
         * This method should do a single pass through the data set and calculate all needed values,
         *  such as: min, max, sum, etc... so that we can do this in one pass through the data.
         *  Rather than once for each.
         *
         ********************************************************************************************/
        public void processData()
        {
                for( int i = 0; i < iRadarChartDataSet.getNumberOfDataSets(); i++ )
                {
                  for( int j = 0; j < iRadarChartDataSet.getDataSetSize(); j++ )
                  {
                    double value = iRadarChartDataSet.getValue(i,j);
                    if (value>maxValue) maxValue = value;
                    if (value<minValue) minValue = value;
                  }
                }
        }


        public double getMinValue()
        {
                return minValue;
        }

        public double getMaxValue()
        {
                return maxValue;
        }

}
