package datengraphiti.features;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;

import Daten.DatenFactory;
import Daten.PositionBox;
import datengraphiti.properties.XmlExport;

public class SaveModelFeature extends AbstractCustomFeature {

	public SaveModelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {

		return "Model speichern";
	}

	@Override
	public String getDescription() {
		return "Speichert das Model";
	}

	@Override
	public void execute(ICustomContext context) {
		Diagram diagram = getDiagram();
		try {
			CharArrayWriter out = writeXmlToMemory(diagram);
			writeFromMemoryToFile(diagram, out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private CharArrayWriter writeXmlToMemory(Diagram diagram)
			throws IOException {
		XmlExport exportHelper = new XmlExport();
		CharArrayWriter out = xmlRootElementStart(diagram, exportHelper);
		xmlDerModelElementeSchreiben(diagram, exportHelper, out);
		xmlRootElementEnde(out);
		return out;
	}

	private void writeFromMemoryToFile(Diagram diagram, CharArrayWriter out)
			throws UnsupportedEncodingException, CoreException {
		InputStream input = new ByteArrayInputStream(out.toString().getBytes(
				"UTF-8"));
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();

		String filename = calculateFilename(diagram);
		IPath path = new Path(filename);
		IFile file = root.getFile(path);

		if (file.isAccessible()) {
			file.delete(true, null);
		}

		file.create(input, true, null);
	}

	private CharArrayWriter xmlRootElementStart(Diagram diagram,
			XmlExport exportHelper) throws IOException {
		PositionBox section = createSection(diagram);
		CharArrayWriter out = new CharArrayWriter();
		exportHelper.export(out, section);
		xmlElementEnde(out);
		return out;
	}

	private void xmlDerModelElementeSchreiben(Diagram diagram,
			XmlExport exportHelper, CharArrayWriter out) throws IOException {
		EList<PictogramLink> pictograms = diagram.getPictogramLinks();
		for (PictogramLink plink : pictograms) {
			EList<EObject> modelObjects = plink.getBusinessObjects();
			for (EObject modelObject : modelObjects) {
				exportHelper.export(out, modelObject);
				xmlElementEnde(out);
			}
		}
	}

	private void xmlElementEnde(CharArrayWriter out) throws IOException {
		out.write(">\n");
	}
	
	private void xmlRootElementEnde(CharArrayWriter out) throws IOException {
		out.write("</PositionBox>\n");
	}

	private PositionBox createSection(Diagram diagram) {

		EList<PictogramLink> pictograms = diagram.getPictogramLinks();

		PositionBox section = DatenFactory.eINSTANCE.createPositionBox();
		section.setName(diagram.getName());

		for (PictogramLink plink : pictograms) {
			EList<EObject> modelObjects = plink.getBusinessObjects();
			for (EObject modelObject : modelObjects) {
				if (modelObject instanceof PositionBox) {
					PositionBox box = (PositionBox) modelObject;
					section.setWidth(Math.max(section.getWidth(), box.getX()
							+ box.getWidth()));
					section.setHeight(Math.max(section.getHeight(), box.getY()
							+ box.getHeight()));
				}
			}
		}
		return section;
	}

	private String calculateFilename(Diagram diagram) {
		URI diagramUri = diagram.eResource().getURI();
		String filename = diagramUri.toString();
		filename = filename.replaceFirst("^platform:/resource/", "");

		filename = filename.replaceFirst("\\.diagram$", ".xml");
		return filename;
	}

	@Override
	public boolean hasDoneChanges() {
		return false;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
}
