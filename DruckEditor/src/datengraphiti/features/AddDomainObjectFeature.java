package datengraphiti.features;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import datengraphiti.diagram.PositionBoxUpdate;
import datengraphiti.diagram.UpdateHelper;
import Daten.PositionBox;

public class AddDomainObjectFeature extends AbstractAddFeature implements
		IAddFeature {
	UpdateHelper helper;

	public AddDomainObjectFeature(IFeatureProvider fp) {
		super(fp);
		helper = new PositionBoxUpdate();
	}

	@Override
	public boolean canAdd(IAddContext context) {
		// TODO: check for right domain object instance below
		return /* context.getNewObject() instanceof DomainObject && */context
				.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		int width = context.getWidth();
		int height = context.getHeight();

		if (width < 0) {
			width = 140;
		}

		if (height < 0) {
			height = 70;
		}
		context.getHeight();

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();
		PositionBox bereich = (PositionBox) context.getNewObject();

		ContainerShape containerShape = peCreateService.createContainerShape(
				targetDiagram, true);
		RoundedRectangle roundedRectangle = gaService.createRoundedRectangle(
				containerShape, 5, 5);
		gaService.setLocationAndSize(roundedRectangle, context.getX(),
				context.getY(), width, height);
		setId(containerShape,"filled");	
		
		Shape shape = peCreateService.createShape(containerShape, false);
		setId(shape,"text");		
		Text text = gaService.createText(shape, bereich.getName());
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				
		gaService.setLocationAndSize(text, 0, 0, width, height);

		peCreateService.createChopboxAnchor(containerShape);


		fromGuiToModel(bereich, containerShape.getGraphicsAlgorithm());

		link(containerShape, bereich);
		
		update(containerShape,bereich);
		return containerShape;
	}

	private void update(ContainerShape containerShape, Object bereich) {
		helper.updateWithId(getDiagram(), containerShape, bereich);
	}

	private void fromGuiToModel(PositionBox bereich, GraphicsAlgorithm ga) {

		TransactionalEditingDomain domain = TransactionUtil
				.getEditingDomain(bereich);
		final PositionBox fBereich = bereich;

		final int x = ga.getX();
		final int y = ga.getY();
		final int w = ga.getWidth();
		final int h = ga.getHeight();

		if (domain != null) {
			domain.getCommandStack().execute(new RecordingCommand(domain) {

				@Override
				public void doExecute() {
					fBereich.setX(x);
					fBereich.setY(y);
					fBereich.setWidth(w);
					fBereich.setHeight(h);
				}
			});
		}

	}



	
	public void setId(PropertyContainer c, String value) {
		IPeService peService = GraphitiUi.getPeService();

		peService.setPropertyValue(c, UpdateHelper.MY_ID, value);
	}

}
