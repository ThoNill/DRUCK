package datengraphiti.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;

public class DatenGraphitiDiagramTypeProvider extends AbstractDiagramTypeProvider {

	public DatenGraphitiDiagramTypeProvider() {
		super();
		setFeatureProvider(new DatenGraphitiFeatureProvider(this));
	}
	
	@Override
	public boolean isAutoUpdateAtRuntime() {
		return true;
	}
}
