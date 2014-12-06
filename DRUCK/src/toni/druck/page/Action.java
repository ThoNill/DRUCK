package toni.druck.page;


/*****
 * 
 * Eine Aktion die w�hrend des erstellens eines Ausdruckes aufgerufen wird
 * z.Bsp. Z�hlen der Seiten, addieren von Werten
 * 
 * Object Interface
 * 
 * @author Thomas Nill
 *
 */
public interface Action {
	void perform();
	void setPage(Page page);
}
