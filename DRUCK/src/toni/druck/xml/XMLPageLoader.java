package toni.druck.xml;

import org.jdom2.Document;

import toni.druck.core.PageLoader;
import toni.druck.core2.Page;

public class XMLPageLoader extends XMLDocumentLoader implements PageLoader {

	public Page createPage(String name) {
		try {

			Document doc = createDocument(name);

			DruckWalker walker = new DruckWalker();
			walker.walkAlong(doc);
			Page page = walker.getPage();
			page.createController();
			page.layout();
			return page;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



}
