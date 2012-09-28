package org.krysalis.jcharts.chartData;


import com.google.code.appengine.awt.Paint;

import org.krysalis.jcharts.chartData.interfaces.IRadarChartDataSet;
import org.krysalis.jcharts.properties.RadarChartProperties;
import org.krysalis.jcharts.test.HTMLGenerator;


/**
 * The chart dataset of a radar chart
 *
 * @author Rami Hansenne
 */
final public class RadarChartDataSet extends DataSet implements IRadarChartDataSet
{
        private String chartTitle;
        private String[] axisLabels;

        /******************************************************************************************
         * Constructor
         *
         * @param chartTitle if the title is NULL, no title will be drawn
         * @param data the data sets to be displayed in the chart.
         * @param legendLabels if this is: NULL there will be no Legend. Otherwise, there must be an
         *           one to one mapping of labels to data sets.
         * @param paints paints to use for the data sets. There must be an one to one mapping of
         *           labels to data sets.
         * @param axisLabels
         * @param chartTypeProperties properties Object specific to the type of chart you are rendering.
         * @throws ChartDataException if data is not in correct form.
         *******************************************************************************************/
        public RadarChartDataSet( String chartTitle,
                                  double[][] data,
                                  String[] legendLabels,
                                  Paint[] paints,
                                  String[] axisLabels,
                                  RadarChartProperties chartTypeProperties ) throws ChartDataException
        {
                super( data, legendLabels, paints, chartTypeProperties );
                validateData(data,legendLabels,axisLabels,paints);
                this.chartTitle = chartTitle;
                this.axisLabels = axisLabels;
        }



        /*******************************************************************************************
         * Perform some limited validation of the structure of the passed data.  This is useful for
         *  development.
         *
         * @param data
         * @param legendLabels
         * @param axisLabels
         * @param paints
         * @throws ChartDataException
         *******************************************************************************************/
        private void validateData( double[][] data, String[] legendLabels, String[] axisLabels, Paint[] paints ) throws ChartDataException
        {
                if( legendLabels != null && ( data.length != legendLabels.length ) )
                {
                        throw new ChartDataException( "There is not an one to one mapping of 'legend labels' to 'data items'." );
                }

                if( data.length != paints.length )
                {
                        throw new ChartDataException( "There is not an one to one mapping of 'Paint' Implementations to 'data items'." );
                }

                for (int i=1;i<data.length;i++)
                    if (data[i].length!=data[0].length)
                      throw new ChartDataException( "All data items should contain an equal number of values." );

                if (data.length>0 && axisLabels.length!=data[0].length)
                  throw new ChartDataException( "There is not a one to one mapping of axis labels to values per 'data item'." );

        }


        /******************************************************************************************
         * Returns the chart title.
         *
         * @return String the chart title. If this returns NULL, no title will be displayed.
         ******************************************************************************************/
        public String getChartTitle()
        {
                return this.chartTitle;
        }



        /******************************************************************************************
         * Returns the value in the data set at the specified position.
         *
         * @param dataset
         * @param index
         * @return double
         * @throws ArrayIndexOutOfBoundsException
         *******************************************************************************************/
        public final double getValue( int dataset, int index ) throws ArrayIndexOutOfBoundsException
        {
                return super.data[ dataset ][ index ];
        }


        /******************************************************************************************
         * Returns the number of data sets
         *
         * @return int
         ******************************************************************************************/
        public final int getNumberOfDataSets()
        {
                return this.data.length;
        }

        /******************************************************************************************
         * Returns the number of values per data set
         *
         * @return int
         ******************************************************************************************/
        public final int getDataSetSize()
        {
               if (data.length==0)
                 return 0;
               else return data[0].length;
        }

        /******************************************************************************************
         * Returns the x-axis label corresponding to the passed index
         *
         * @param index
         * @return String
         *******************************************************************************************/
        public String getAxisLabel( int index )
        {
                return this.axisLabels[ index ];
        }


        /******************************************************************************************
         * Returns the number of labels on the x-axis
         *
         * @return int
         ******************************************************************************************/
        public int getNumberOfAxisLabels()
        {
                if( this.axisLabels != null )
                {
                        return this.axisLabels.length;
                }
                else
                {
                        return 0;
                }
        }

        /*********************************************************************************************
         * Enables the testing routines to display the contents of this Object.
         *
         * @param htmlGenerator
         **********************************************************************************************/
        public void toHTML( HTMLGenerator htmlGenerator )
        {
                super.toHTML( htmlGenerator );
        }

}
