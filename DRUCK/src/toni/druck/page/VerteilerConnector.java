package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Verbindet einen Verteiler mit einem Listener.
 * 
 *         Datenstruktur
 * 
 */
public class VerteilerConnector {
    private String name;
    private PrintListener listener;

    public VerteilerConnector(String name, PrintListener listener) {
        super();
        this.name = name;
        this.listener = listener;
    }

    public String getName() {
        return name;
    }

    public PrintListener getListener() {
        return listener;
    }
}
