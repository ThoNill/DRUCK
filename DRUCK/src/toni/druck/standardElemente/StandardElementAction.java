package toni.druck.standardElemente;

import toni.druck.model.DataModel;
import toni.druck.page.Element;
import toni.druck.page.ElementAction;
import toni.druck.page.Page;

/*****
 * 
 * @author Thomas Nill
 * 
 *         Standardimplementierung der {@link ElementAction} Schnittstelle
 * 
 * 
 */
public abstract class StandardElementAction implements ElementAction {

    @Override
    public void prepareForPrint(Element elem) {
        prepareForPrint(elem, elem.getData(), elem.getPage());
    }

    abstract protected void prepareForPrint(Element elem, DataModel data,
            Page page);

}
