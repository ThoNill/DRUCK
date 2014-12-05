package toni.druck.chart;

import toni.druck.core.Variable;
import toni.druck.core2.Page;
import toni.druck.helper.ClassFactory;

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
