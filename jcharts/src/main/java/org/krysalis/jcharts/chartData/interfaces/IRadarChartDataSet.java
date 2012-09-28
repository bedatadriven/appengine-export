package org.krysalis.jcharts.chartData.interfaces;

/**
 * Base interface of datasets to be used with radar charts.
 *
 * @author Rami Hansenne
 */
public interface IRadarChartDataSet
    extends IDataSet {

  /******************************************************************************************
   * Returns the value in the data set at the specified position.
   *
   * @param dataset
   * @param index
   * @return double
   *******************************************************************************************/
  public double getValue(int dataset, int index);

  /******************************************************************************************
   * Returns the chart title.
   *
   * @return String the chart title. If this returns NULL, no title will be displayed.
   ******************************************************************************************/
  public String getChartTitle();

  /******************************************************************************************
   * Returns the number of labels on the axis
   *
   * @return int
   ******************************************************************************************/
  public int getNumberOfAxisLabels();

  /******************************************************************************************
   * Returns the axis label at the specified index.
   *
   * @param index
   * @return String the axis label
   ******************************************************************************************/
  public String getAxisLabel( int index );

  /******************************************************************************************
   * Returns the number of data sets
   *
   * @return int
   ******************************************************************************************/
  public int getNumberOfDataSets();

  /******************************************************************************************
   * Returns the number of values per data set
   *
   * @return int
   ******************************************************************************************/
  public int getDataSetSize();

}
