package toni.druck.elementRenderer;


/**
 * 
 * @author Thomas Nill
 * 
 * Standardimplementierung
 * 
 */
public class StandardRenderer implements ElementRenderer {

	public String render(Object obj) {
		return (obj != null) ? obj.toString() : null;
	}

}
