package datengraphiti.properties;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

public class XmlExport {

	public void export(Writer out, EObject obj) throws IOException {
		out.write("<");
		out.write(obj.eClass().getName());
		out.write(" ");
		for (EAttribute attribute : obj.eClass().getEAllAttributes()) {
			Object o = obj.eGet(attribute);
			if (o != null && !"".equals(o)) {
				out.write(" ");
				out.write(attribute.getName().toLowerCase());
				out.write("=\"");
				out.write((o == null) ? "null" : o.toString());
				out.write("\" ");
			}
		}
		
	}

}
