package toni.druck.elements;

import toni.druck.core2.Action;
import toni.druck.core2.DataModel;
import toni.druck.core2.Operation;
import toni.druck.core2.Page;
import toni.druck.core2.PrintEvent;
import toni.druck.core2.PrintListener;

public class Counter extends Operation implements Action, PrintListener {

	private String count;

	private int icount;

	private String clearAt;

	public Counter(String scount, DataModel m) {
		super();
		setCount(scount);
		setDataModel(m);
		connect(m);
	}

	public void setClearAt(String clearAt) {
		this.clearAt = clearAt;
	}

	public Counter() {
		super();
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void connect(DataModel source) {
		icount = source.getIndex(count);
	}


	protected void inc() {
		writeLong(icount,readLong(icount)+1);
	}


	protected void set0() {
		writeLong(icount,0L);
	}

	public void listenTo(PrintEvent ev) {
		set0();
	}

	public void perform() {
		inc();
	}

	public void setPage(Page page) {
		super.setPage(page);
		if (clearAt != null) {
			page.addPrintListener(clearAt, this);
		}
	}

}
