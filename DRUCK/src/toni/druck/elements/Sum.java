package toni.druck.elements;

import toni.druck.core2.Action;
import toni.druck.core2.DataModel;
import toni.druck.core2.Operation;
import toni.druck.core2.Page;
import toni.druck.core2.PrintEvent;
import toni.druck.core2.PrintListener;

public class Sum extends Operation implements Action, PrintListener {

	private String sitem;
	private String ssum;

	private String clearAt = null;

	private int iitem = -1;
	private int isum = -1;

	public Sum(String sitem, String ssum, DataModel m) {
		super();
		setItem(sitem);
		setSum(ssum);
		setDataModel(m);
		connect(m);
	}

	public String getItem() {
		return sitem;
	}

	public String getSum() {
		return ssum;
	}

	public String getClearAt() {
		return clearAt;
	}

	public Sum() {
		super();
	}

	public void setClearAt(String clearAt) {
		this.clearAt = clearAt;
	}

	public void setItem(String sitem) {
		this.sitem = sitem;
	}

	public void setSum(String ssum) {
		this.ssum = ssum;
	}

	public void connect(DataModel source) {
		isum = source.getIndex(ssum);
		iitem = source.getIndex(sitem);
	}

	public void perform() {
		long i = readMoney(iitem);
		long sum = readMoney(isum);
		sum += i;
		writeMoney(isum, sum);
	}

	public void listenTo(PrintEvent ev) {
		writeMoney(isum, 0L);
	}

	public void setPage(Page page) {
		super.setPage(page);
		if (clearAt != null) {
			page.addPrintListener(clearAt, this);
		}
	}

}
