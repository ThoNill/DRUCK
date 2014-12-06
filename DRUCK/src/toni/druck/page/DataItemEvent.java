package toni.druck.page;


public class DataItemEvent extends PrintEvent {
	public DataItem item;

	public DataItemEvent(DataItem item, PageRenderer out) {
		super(DATEN, out);
		this.item = item;
	}

}
