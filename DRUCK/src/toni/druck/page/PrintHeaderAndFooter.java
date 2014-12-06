package toni.druck.page;



public class PrintHeaderAndFooter implements PrintListener, PrintController {
	boolean printTheHeader = true;
	boolean printTheFooter = false;
	Element header = null;
	Element body = null;
	Element footer = null;
	Page page = null;
	int lastHeight;

	public PrintHeaderAndFooter(Page page, String name) {
		this.page = page;
		header = page.getSection(name + "_header");
		footer = page.getSection(name + "_footer");
		body = page.getSection(name);
		printTheHeader = (header != null);

		Verteiler verteiler = page.getVerteiler(name);
		if (verteiler != null && verteiler.getNewHeaderWhen() != null) {
			verteiler = page.getVerteiler(verteiler.getNewHeaderWhen());
			verteiler.addListener(this);
		}
		page.addListener(this);

	}

	public void listenTo(PrintEvent ev) {
		if (header != null) {
			printTheHeader = true;
		}
	}

	public void calculatePrintFooter(DataItem item) {
		if (footer != null && body == item.getSection()) {
			printTheFooter = !item.isNextItemOfTheSameType();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * toni.druck.core.PrintController#print(toni.druck.renderer.PageRenderer)
	 */
	public void print(PageRenderer out) {
		if (printTheHeader) {
			header.print(out);
		}
		body.print(out);
		if (printTheFooter) {
			footer.print(out);
		}
		printTheHeader = false;
		printTheFooter = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toni.druck.core.PrintController#getTestHeight()
	 */
	public int getTestHeight(DataItem item) {
		calculatePrintFooter(item);

		int h = body.getTestHeight(item);
		if (printTheHeader) {
			h += header.getTestHeight(item);
		}
		if (printTheFooter) {
			h += footer.getTestHeight(item);
		}
		return h;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see toni.druck.core.PrintController#getPageShiftHeight()
	 */
	public int getPageShiftHeight() {
		int height = body.getPageShiftHeight();
		if (printTheHeader) {
			height += header.getPageShiftHeight();
		}
		if (printTheFooter) {
			height += footer.getPageShiftHeight();
		}
		return height;
	}

	public boolean isPrintable() {
		return body.isPrintable();
	}

	public boolean isEnabled() {
		return body.isEnabled();
	}

	public void prepareForPrint() {
		if (printTheHeader) {
			header.prepareForPrint();
		}
		body.prepareForPrint();
		if (printTheFooter) {
			footer.prepareForPrint();
		}
	}

	/*
	 * public int getHeight() { return body.getHeight(); }
	 * 
	 * public void setHoeheBeruecksichtigt(boolean hoeheBeruecksichtigt) {
	 * body.setHoeheBeruecksichtigt(hoeheBeruecksichtigt);
	 * 
	 * }
	 */

}
