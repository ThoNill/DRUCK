package datengraphiti.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import Daten.PositionBox;
import datengraphiti.features.AddDomainObjectFeature;
import datengraphiti.features.AlignFeature;
import datengraphiti.features.AlignFeature.AlignCase;
import datengraphiti.features.CreateDomainObjectFeature;
import datengraphiti.features.LayoutDomainObjectFeature;
import datengraphiti.features.MoveShapeFeature;
import datengraphiti.features.ResizeShapeFeature;
import datengraphiti.features.SaveModelFeature;
import datengraphiti.features.UpdateFeature;

public class DatenGraphitiFeatureProvider extends DefaultFeatureProvider {

	public DatenGraphitiFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return new ICreateFeature[] {new CreateDomainObjectFeature(this)};
	}
	
	
	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context instanceof IAddContext /* && context.getNewObject() instanceof <DomainObject> */) {
			return new AddDomainObjectFeature(this);
		}

		return super.getAddFeature(context);
	}
	
	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		if (context.getPictogramElement() instanceof ContainerShape /* && getBusinessObjectForPictogramElement(context.getPictogramElement()) instanceof <DomainObject> */) {
			return  new LayoutDomainObjectFeature(this);
		}
	
		return super.getLayoutFeature(context);
	}
	
	@Override
	public ICustomFeature[] 
			getCustomFeatures(ICustomContext context) {
	    return new ICustomFeature[] { 
	    		new AlignFeature(this,AlignCase.SET_VORBILD),
	    		new AlignFeature(this,AlignCase.SET_X),
	    		new AlignFeature(this,AlignCase.SET_Y),
	    		new AlignFeature(this,AlignCase.SET_WIDTH),
	    		new AlignFeature(this,AlignCase.SET_HEIGHT),
	    		new AlignFeature(this,AlignCase.NEBENEINANDER),
	    		new AlignFeature(this,AlignCase.UNTEREINANDER),
	    		 new SaveModelFeature(this)
	    		};
	} 
	
	@Override
	public IMoveShapeFeature 
	getMoveShapeFeature(IMoveShapeContext context) {
	    return new MoveShapeFeature(this);
	 } 
	
	@Override
	public IResizeShapeFeature
	getResizeShapeFeature(IResizeShapeContext context) {
		return new ResizeShapeFeature(this);
	 } 
	
	@Override
	public IUpdateFeature 
	getUpdateFeature(IUpdateContext context) {
	   PictogramElement pictogramElement = context.getPictogramElement();
	   if (pictogramElement  instanceof ContainerShape) {
	       Object bo = getBusinessObjectForPictogramElement(pictogramElement);
	       if (bo instanceof PositionBox) {
	           return new UpdateFeature(this);
	       }
	   }
	   return super.getUpdateFeature(context);
	 } 
	
}
