package toni.druck.renderer;

import org.jfree.chart.JFreeChart;

import toni.druck.chart.Chart;
import toni.druck.chart.DruckChartFactory;
import toni.druck.core2.PageRenderer;

public abstract class DefaultPageRenderer implements PageRenderer {

	protected void printChart(Chart elem, int thema) {
		DruckChartFactory cf = elem.getClassFactory();
		if (cf != null) {
			printChart(elem, cf.getChart(elem, thema));
		}
	}

	protected abstract void printChart(Chart elem, JFreeChart chart);

}
