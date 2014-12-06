package toni.druck.chart;

import toni.druck.helper.ClassFactory;
import toni.druck.page.Page;
import toni.druck.standardElemente.Variable;

public class Chart extends Variable {
	private String classname;
	private DruckChartFactory cf = null;

	public Chart(String name, Page page) {
		super(name, page);
	}

	public Chart() {
		super();
	}

	public String getClassName() {
		return classname;
	}

	public void setClassName(String classname) {
		this.classname = classname;
		cf = (DruckChartFactory) ClassFactory.getInstance(classname,
				DruckChartFactory.class);
	}

	public DruckChartFactory getClassFactory() {
		return cf;
	}
}
