package datengraphiti.diagram;

import java.util.Collection;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.util.ColorConstant;

public abstract class UpdateHelper {
	public static final String MY_ID = "MyId";

	
	public void updateWithId(Diagram diagram,ContainerShape pic, Object obj) {
		IPeService peService = GraphitiUi.getPeService();
		updateShape(diagram, obj, peService, pic);
		
		Collection<Shape> allShapes = peService.getAllContainedShapes(pic);
		for (Shape shape : allShapes) {
			updateShape(diagram, obj, peService, shape);
		}
	}


	private void updateShape(Diagram diagram, Object obj, IPeService peService,
			Shape shape) {
		String value = peService.getPropertyValue(shape, MY_ID);
		if (value != null) {
			update(value, diagram, shape, obj);
		}
	}
	
	
	public Color getColor(Diagram diagram,int red, int green, int blue) {
		IGaService gaService = Graphiti.getGaService();
		ColorConstant color = new ColorConstant(red, green, blue);
		return gaService.manageColor(diagram, color);
	}



	public abstract void update(String value, Diagram diagram,Shape shape, Object obj);

	public boolean hasPropertyValue(PropertyContainer c,String key,String value) {
		IPeService peService = GraphitiUi.getPeService();
		String avalue = peService.getPropertyValue(c, key);
		return (avalue != null && value.equals(value));
	}
	
	public void setPropertyValue(PropertyContainer c,String key,String value) {
		IPeService peService = GraphitiUi.getPeService();
		peService.setPropertyValue(c, key,value);
	}
	
	public boolean hasPropertyValue(PropertyContainer c,String key,boolean value) {
		IPeService peService = GraphitiUi.getPeService();
		String avalue = peService.getPropertyValue(c, key);
		return (avalue != null && ((avalue.equals("true") && value) || (avalue.equals("false") && !value)));
	}
	
	public void setPropertyValue(PropertyContainer c,String key,boolean value) {
		IPeService peService = GraphitiUi.getPeService();
		peService.setPropertyValue(c, key,((value) ? "true" : "false"));
	}
	
	public IReason checkReasonsWithtId(ContainerShape pic, Object obj) {
		IPeService peService = GraphitiUi.getPeService();
		String reasonText = null;
		
		reasonText = checkReasonOfShape(obj, peService,pic);
		if (reasonText != null)
			return Reason.createTrueReason(reasonText);

		Collection<Shape> allShapes = peService.getAllContainedShapes(pic);
		for (Shape shape : allShapes) {
			reasonText = checkReasonOfShape(obj, peService, shape);
			if (reasonText != null)
				return Reason.createTrueReason(reasonText);
		}
		return null;
	}


	private String checkReasonOfShape(Object obj, IPeService peService,
			Shape shape) {
		String value = peService.getPropertyValue(shape, UpdateHelper.MY_ID);
		if (value != null) {
			return getUpdateReason(value, shape, obj);
		}
		return null;
	}
	
	public abstract String getUpdateReason(String value, Shape shape, Object obj);
}
