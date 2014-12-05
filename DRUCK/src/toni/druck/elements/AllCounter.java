package toni.druck.elements;

import java.util.HashMap;

import toni.druck.core2.Action;
import toni.druck.core2.DataModel;
import toni.druck.core2.Operation;
import toni.druck.core2.PageRenderer;
import toni.druck.core2.PrintEvent;
import toni.druck.core2.PrintListener;

public class AllCounter extends Operation implements Action, PrintListener {

	private String count;
	private String allCount;
	private String key;

	private int icount;
	private int iAllcount;
	private int iKey;

	HashMap<String, Long> map = new HashMap<String, Long>();

	public AllCounter(String scount, String sallCount, String skey, DataModel m) {
		super();
		setCount(scount);
		setAllCount(sallCount);
		setKey(skey);
		setDataModel(m);
		connect(m);
	}

	public AllCounter() {
		super();
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void connect(DataModel source) {

		icount = source.getIndex(count);
		iAllcount = source.getIndex(allCount);
		iKey = source.getIndex(key);
	}

	public void listenTo(PrintEvent ev) {
		if (ev.getStatus() == PageRenderer.ACCUMULATION) {
			long l = readLong(icount);
			String k = readString(iKey);
			map.put(k, new Long(l));
		}
	}

	public void perform() {
		String k = readString(iKey);
		Long l = map.get(k);
		writeLong(iAllcount, l.longValue());
	}

}
