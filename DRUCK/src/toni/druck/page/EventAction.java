package toni.druck.page;

import java.util.Vector;

/*****
 * 
 * @author Thomas Nill
 * 
 * Standard Implementierung einer Action
 *  
 * Datentyp
 * 
 */
public class EventAction implements Action {

	private Vector<PrintListener> listener = null;
	private Vector<Action> actions = null;

	public EventAction() {
		super();
	}

	public void fireEvent(PrintEvent ev) {
		if (listener != null) {
			for (PrintListener l : listener) {
				l.listenTo(ev);
			}
		}
	}

	public void addListener(PrintListener arg0) {
		if (listener == null) {
			listener = new Vector<PrintListener>();
		}
		listener.addElement(arg0);
	}

	public boolean removeListener(Object arg0) {
		if (listener != null) {
			return listener.removeElement(arg0);
		}
		return false;
	}

	public void perform() {
		if (actions != null) {
			for (Action a : actions) {
				a.perform();
			}
		}
	}

	public void addAction(Action arg0) {
		if (actions == null) {
			actions = new Vector<Action>();
		}
		actions.addElement(arg0);
	}

	public boolean removeAction(Action arg0) {
		if (actions != null) {
			return actions.removeElement(arg0);
		}
		return false;
	}

	public Vector<PrintListener> getListener() {
		return listener;
	}

	public Vector<Action> getActions() {
		return actions;
	}

	public void setPage(Page page) {
	}

}