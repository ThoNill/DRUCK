package toni.druck.page;

import java.util.ArrayList;
import java.util.List;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Standard Implementierung einer Action
 * 
 *         Datentyp
 * 
 */
public class EventAction implements Action {

    private List<PrintListener> listener = null;
    private List<Action> actions = null;

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
            listener = new ArrayList<PrintListener>();
        }
        listener.add(arg0);
    }

    public boolean removeListener(Object arg0) {
        if (listener != null) {
            return listener.remove(arg0);
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
            actions = new ArrayList<Action>();
        }
        actions.add(arg0);
    }

    public boolean removeAction(Action arg0) {
        if (actions != null) {
            return actions.remove(arg0);
        }
        return false;
    }

    public List<PrintListener> getListener() {
        return listener;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setPage(Page page) {
    }

}