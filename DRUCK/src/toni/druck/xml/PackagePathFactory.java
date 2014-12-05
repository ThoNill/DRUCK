package toni.druck.xml;

import org.apache.log4j.Logger;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.Element;
import org.jdom2.Namespace;

import toni.druck.helper.ClassFactory;

public class PackagePathFactory extends DefaultJDOMFactory {
	static Logger logger = Logger.getLogger(PackagePathFactory.class);

	private String prefix;
	private String dir;

	public PackagePathFactory() {
		super();
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public Element element(final int line, final int col, final String name,
			Namespace namespace) {
		return createElement(name);
	}

	@Override
	public Element element(final int line, final int col, final String name) {
		return createElement(name);
	}

	@Override
	public Element element(final int line, final int col, final String name,
			String uri) {
		return createElement(name);
	}

	@Override
	public Element element(final int line, final int col, final String name,
			String prefix, String uri) {
		return createElement(name);
	}

	public Element createElement(String name) {
		String filename = getDir() + name + ".xml";
		if (IncludeElement.hasTemplate(filename)) {
			return new IncludeElement(name, filename);
		}

		Object obj = ClassFactory.getInstance(prefix + name);
		JDOMElementWithReference elem = new JDOMElementWithReference(name, obj);
		elem.setName(name);
		return elem;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if (prefix == null) {
			throw new NullPointerException();
		}

		this.prefix = prefix;
	}

}
