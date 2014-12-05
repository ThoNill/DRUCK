package toni.druck.chart;

import org.jfree.chart.JFreeChart;

public interface DruckChartFactory {
	int PDF_THEMA = 1;
	int PS_THEMA = 2;

	JFreeChart getChart(Chart elem, int theme);
}
