package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         {@link PrintController} um die Ausgabe von Header und Footer
 *         Abschnitten zu kontrollieren
 * 
 */
public class PrintHeaderAndFooter implements PrintListener, PrintController {
    private boolean printTheHeader = true;
    private boolean printTheFooter = false;
    private Element header = null;
    private Element body = null;
    private Element footer = null;
    private Page page = null;

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
            page.print(header, out);
        }
        page.print(body, out);
        if (printTheFooter) {
            page.print(footer, out);
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
        return 0;
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
