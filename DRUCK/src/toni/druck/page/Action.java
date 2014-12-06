package toni.druck.page;


/*****
 * 
 * Eine Aktion die während des erstellens eines Ausdruckes aufgerufen wird
 * z.Bsp. Zählen der Seiten, addieren von Werten
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
