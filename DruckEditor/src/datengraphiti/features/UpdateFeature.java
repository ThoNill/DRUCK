package datengraphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import datengraphiti.diagram.PositionBoxUpdate;
import datengraphiti.diagram.UpdateHelperFeature;
import Daten.PositionBox;

public class UpdateFeature extends UpdateHelperFeature {

	public UpdateFeature(IFeatureProvider fp) {
		super(fp,new PositionBoxUpdate());
	}

	@Override
	protected IReason checkReasonsWithoutId(GraphicsAlgorithm ga, Object obj) {
		PositionBox bo = (PositionBox) obj;
		if (ga.getX() != bo.getX() || ga.getY() != bo.getY()
				|| ga.getWidth() != bo.getWidth()
				|| ga.getHeight() != bo.getHeight()) {
			return Reason.createTrueReason("Dimension geändert");
		}
		return Reason.createFalseReason();
	}

	@Override
	protected void updateWithoutId(GraphicsAlgorithm ga, Object obj) {
		PositionBox bo = (PositionBox) obj;
		ga.setX(bo.getX());
		ga.setY(bo.getY());
		ga.setWidth(bo.getWidth());
		ga.setHeight(bo.getHeight());
	}





}
