package toni.druck.xml;

import org.apache.log4j.Logger;
import org.jdom2.Document;

import toni.druck.core.PageLoader;
import toni.druck.page.Page;
import toni.druck.page.Verteiler;


/*****
 * 
 * @author Thomas Nill
 * 
 * Läd ein XML Layout und erzeugt daraus eine {@link Page}.
 * 
 */
public class XMLPageLoader extends XMLDocumentLoader implements PageLoader {
	static Logger logger = Logger.getLogger(XMLPageLoader.class.getSimpleName());
	
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
			logger.error("Resource " + name + " do not exist");
		}
		return null;
	}



}
