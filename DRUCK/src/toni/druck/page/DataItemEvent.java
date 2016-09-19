package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         {@link PrintEvent} mit einem {@link DataItem}
 * 
 */
public class DataItemEvent extends PrintEvent {
    public DataItem item;

    public DataItemEvent(DataItem item, PageRenderer out) {
        super(DATEN, out);
        this.item = item;
    }

}
