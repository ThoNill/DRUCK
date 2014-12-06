/*
 * Created on 20.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package toni.druck.page;

import org.apache.log4j.Logger;




import toni.druck.model.DataModel;

/**
 * @author Nill
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Verteiler extends EventAction {
	static Logger logger = Logger.getLogger("DataPlace");

	private int place[] = null;

	private String name;

	private String placeNames;

	private String newHeaderWhen = null;
	private DataModel model;

	public Verteiler(String name, String placeNames) {
		super();
		setName(name);
		setFields(placeNames);
	}

	public Verteiler() {
		super();
	}

	public String getNewHeaderWhen() {
		return newHeaderWhen;
	}

	public void setNewHeaderWhen(String newHeaderWhen) {
		this.newHeaderWhen = newHeaderWhen;
	}

	public String getFields() {
		return placeNames;
	}

	public void setDataModel(DataModel model) {
		this.model = model;
		if (placeNames != null) {
			place = model.getMultiIndex(placeNames);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFields(String placeNames) {
		this.placeNames = placeNames;
	}

	public void fillValues(DataItem data) {
		if (data.getCommand().equals(name)) {
			if (place != null && place.length == data.getSize() - 1) {
				for (int i = 1; i <= place.length; i++) {
					model.put(place[i - 1], data.getData(i));
				}
			}
		}
	}

	public String[] getActualValues() {
		if (place != null) {
			String v[] = new String[place.length];
			for (int i = 0; i < place.length; i++) {
				v[i] = model.get(place[i]);
			}
			return v;
		}
		return null;
	}

	public void restoreValues(String v[]) {
		if (v == null) return;
		
		if (place != null) {
			for (int i = 0; i < place.length; i++) {
				model.put(place[i], v[i]);
			}
		}
	}

	public void perform(DataItem data, PageRenderer out) {
		fireEvent(data, out);
		perform();
	}

	public void fireEvent(DataItem item, PageRenderer out) {
		fireEvent(new DataItemEvent(item, out));
	}

	public String getName() {
		return name;
	}
}
