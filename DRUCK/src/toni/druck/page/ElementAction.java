package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Eine Aktion auf einem Element zur Vorbereitung einer Ausgabe
 * 
 *         Datentyp
 * 
 */
public interface ElementAction {
    void prepareForPrint(Element elem);
}
