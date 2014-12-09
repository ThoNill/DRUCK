package datengraphiti.features;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

import Daten.PositionBox;

public class ResizeShapeFeature extends DefaultResizeShapeFeature {

	public ResizeShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void resizeShape(IResizeShapeContext context) {
		super.resizeShape(context);
		PictogramElement pe = context.getPictogramElement();
		PositionBox bereich = (PositionBox) Graphiti.getLinkService()
				.getBusinessObjectForLinkedPictogramElement(pe);
		GraphicsAlgorithm ge = pe.getGraphicsAlgorithm();

		final int w = ge.getWidth();
		final int h = ge.getHeight();
		final PositionBox fBereich = bereich;

		TransactionalEditingDomain domain = TransactionUtil
				.getEditingDomain(bereich);

		if (domain != null) {
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				public void doExecute() {

					fBereich.setWidth(w);
					fBereich.setHeight(h);
					
				}
			});
		}
	}
}
