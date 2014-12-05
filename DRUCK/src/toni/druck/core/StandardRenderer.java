package toni.druck.core;


public class StandardRenderer implements ElementRenderer {

	public String render(Object obj) {
		return (obj != null) ? obj.toString() : null;
	}

}
