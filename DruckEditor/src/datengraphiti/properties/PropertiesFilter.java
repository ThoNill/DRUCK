package datengraphiti.properties;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public class PropertiesFilter extends AbstractPropertySectionFilter {
 
    @Override
    protected boolean accept(PictogramElement pe) {
  /*      EObject eObject =
            Graphiti.getLinkService()
            .getBusinessObjectForLinkedPictogramElement(pe);
        if (eObject instanceof PositionBox) {
            return true;
        }
        return false;*/
    	return true;
    }


}
