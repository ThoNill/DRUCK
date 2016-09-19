package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         In dieser Schnittstelle sind die zentralen Funktionen zur Steuerung
 *         es Ausdruckes enthalten
 * 
 *         Objekt-Schnittstelle
 * 
 */
public interface PrintController {

    void prepareForPrint(); // Vorbereitung eines Element zur Ausgabe

    void print(PageRenderer out); // Eigentliche Ausgabe des Elements

    /*
     * H�he um zu testen, ob das Element noch auf die Seite passt, wenn man die
     * Daten von DataItem ber�cksichtigt
     */
    int getTestHeight(DataItem item);

    int getPageShiftHeight(); // H�he der Verschiebung nach Ausgabe des Elements

    boolean isPrintable(); // Kann das Element gedruckt werden.

    boolean isEnabled(); // Kann das Element sichtbar gedruckt werden.

}