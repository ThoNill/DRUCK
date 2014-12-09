package datengraphiti.features;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

import Daten.PositionBox;

public class MoveShapeFeature extends DefaultMoveShapeFeature {

	public MoveShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void moveShape(IMoveShapeContext context) {
		super.moveShape(context);
		PictogramElement pe = context.getPictogramElement();
		PositionBox bereich = (PositionBox) Graphiti.getLinkService()
				.getBusinessObjectForLinkedPictogramElement(pe);
		GraphicsAlgorithm ge = pe.getGraphicsAlgorithm();

		final int x = ge.getX();
		final int y = ge.getY();
		final PositionBox fBereich = bereich;

		TransactionalEditingDomain domain = TransactionUtil
				.getEditingDomain(bereich);

		if (domain != null) {
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				public void doExecute() {

					fBereich.setX(x);
					fBereich.setY(y);
				}
			});
		}
	}
}
