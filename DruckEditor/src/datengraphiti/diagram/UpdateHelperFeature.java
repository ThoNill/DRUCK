package datengraphiti.diagram;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public abstract class UpdateHelperFeature extends AbstractUpdateFeature {
	UpdateHelper helper = null;

	public UpdateHelperFeature(IFeatureProvider fp,UpdateHelper helper) {
		super(fp);
		this.helper = helper;
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		PictogramElement pic = context.getPictogramElement();
		if (pic == null) {
			return false;
		}
		if (getFeatureProvider() == null) {
			return false;
		}
		Object bo = getBusinessObjectForPictogramElement(pic);
		return bo != null;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		PictogramElement pic = context.getPictogramElement();
		if (pic == null) {
			return Reason.createFalseReason();
		}
		GraphicsAlgorithm ga = pic.getGraphicsAlgorithm();
		if (getFeatureProvider() == null) {
			return Reason.createFalseReason();
		}

		Object bo = getBusinessObjectForPictogramElement(pic);
		if (bo == null) {
			return Reason.createFalseReason();
		}

		if (pic instanceof ContainerShape) {
			IReason reason = helper.checkReasonsWithtId((ContainerShape) pic, bo);
			if (reason != null)
				return reason;
		}

		return checkReasonsWithoutId(ga, bo);
	}

	protected IReason checkReasonsWithoutId(GraphicsAlgorithm ga, Object obj) {
		return Reason.createFalseReason();
	}

	

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pic = context.getPictogramElement();
		GraphicsAlgorithm ga = pic.getGraphicsAlgorithm();
		Object bo = getBusinessObjectForPictogramElement(pic);

		if (pic instanceof ContainerShape) {
			helper.updateWithId(getDiagram(),(ContainerShape) pic, bo);
		}
		updateWithoutId(ga, bo);

		return true;
	}

	protected void updateWithoutId(GraphicsAlgorithm ga, Object obj) {

	}

	
	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return true;
	}

	@Override
	public String getName() {
		return "Auffrischen";
	}

	@Override
	public String getDescription() {
		return "GUI auffrischen";
	}




}
