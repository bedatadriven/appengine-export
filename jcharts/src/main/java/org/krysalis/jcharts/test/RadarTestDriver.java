
package org.krysalis.jcharts.test;

import com.google.code.appengine.awt.Color;
import com.google.code.appengine.awt.Paint;

import org.krysalis.jcharts.chartData.ChartDataException;
import org.krysalis.jcharts.chartData.RadarChartDataSet;
import org.krysalis.jcharts.nonAxisChart.RadarChart;
import org.krysalis.jcharts.properties.ChartProperties;
import org.krysalis.jcharts.properties.LegendAreaProperties;
import org.krysalis.jcharts.properties.LegendProperties;
import org.krysalis.jcharts.properties.PropertyException;
import org.krysalis.jcharts.properties.RadarChartProperties;

/**
 * Test driver for the radar chart class
 *
 * @author Rami Hansenne
 */
class RadarTestDriver
{

        /*****************************************************************************************
         *
         * @param args
         * @throws PropertyException
         * @throws ChartDataException
         *****************************************************************************************/
        public static void main( String[] args ) throws PropertyException, ChartDataException
        {
                RadarChartProperties radarChartProperties = new RadarChartProperties();
                radarChartProperties.setFillRadar(true);
                radarChartProperties.setShowGridLines(true);
                radarChartProperties.setScaleMaxValue(1.0);
                radarChartProperties.setScaleIncrement(0.25);

                double[][] data = {{0.53, 0.31, 0.38, 0.21, 0.17, 0.63, 0.38}, {0.24, 0.43, 0.65, 0.60, 0.31, 0.45, 0.38} };
                Paint[] paints = {Color.red , Color.blue};
                String[] legendLabels = {"Test Legend Label", "other data"};
                String[] axisLabels = {"label1", "label2", "label3", "label4","label5","label16", "label7"};
                RadarChartDataSet dataSet = new RadarChartDataSet( "sample title", data, legendLabels, paints, axisLabels, radarChartProperties );

                ChartProperties chartProperties = new ChartProperties();

                LegendProperties legendProperties = new LegendProperties();
                legendProperties.setPlacement( LegendAreaProperties.RIGHT );
                legendProperties.setNumColumns( 1 );

                RadarChart chart = new RadarChart( dataSet, legendProperties, chartProperties , 500, 400 );

                ChartTestDriver.exportImage( chart, "RadarChartTest.png" );
        }



}
