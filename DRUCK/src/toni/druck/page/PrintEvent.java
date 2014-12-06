package toni.druck.page;



public class PrintEvent {

	public static final int DATEN = 1;
	public static final int NEUE_SEITE = 2;

	public int type;
	private PageRenderer renderer;

	public PrintEvent(int type, PageRenderer renderer) {
		super();
		this.type = type;
		this.renderer = renderer;
	}

	public int getStatus() {
		return renderer.getStatus();
	}

}
