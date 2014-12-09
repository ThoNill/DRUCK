package toni.druck.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * 
 * @author Thomas Nill
 * 
 * Dient dazu, um eine Tortengrafik auszugeben
 * 
 */
public class PieFactory implements DruckChartFactory {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JFreeChart getChart(Chart chart, int thema) {

		String values[] = chart.getVariablen();
		DefaultPieDataset dataSet = new DefaultPieDataset();

		String daten[] = values[0].split("#");
		if (daten.length % 2 == 0 && daten.length > 0) {
			for (int i = 0; i < daten.length; i += 2) {
				dataSet.setValue(daten[i], Double.parseDouble(daten[i + 1]));

			}
			/*
			 * dataSet.setValue("China", 19.64); dataSet.setValue("India",
			 * 17.3); dataSet.setValue("United States", 4.54);
			 * dataSet.setValue("Indonesia", 3.4); dataSet.setValue("Brazil",
			 * 2.83); dataSet.setValue("Pakistan", 2.48);
			 * dataSet.setValue("Bangladesh", 2.38);
			 */
			return ChartFactory.createPieChart(getTitle(), dataSet, true, true,
					false);
		} else {
			return null;
		}

	}

}
