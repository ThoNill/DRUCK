package toni.druck.elementRenderer;



public class StandardRenderer implements ElementRenderer {

	public String render(Object obj) {
		return (obj != null) ? obj.toString() : null;
	}

}
