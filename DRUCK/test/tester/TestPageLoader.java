package tester;

import toni.druck.core.StandardPageLoader;
import toni.druck.core.StandardElement;
import toni.druck.core.VHElement;
import toni.druck.core.Variable;
import toni.druck.core2.Element;
import toni.druck.core2.Page;
import toni.druck.core2.Verteiler;
import toni.druck.elements.Add;
import toni.druck.elements.Hbox;
import toni.druck.elements.Sum;
import toni.druck.elements.TextField;
import toni.druck.elements.Vbox;

public class TestPageLoader extends StandardPageLoader {

	public TestPageLoader() {
		super();
	}

	public Page createPage(String name) {
		if ("sum".equals(name)) {
			return createSumPage(name);
		}
		return createPage1(name);
	}

	public Page createPage1(String name) {
		Page p = new Page(name);
		Element s = new StandardElement(Page.HEADER, p);
		p.addSection(s);

		VHElement vb = new Vbox("kunde", p);

		s = new StandardElement("s1", p);
		s.setText("Kunde1");
		s.setBordered(true);
		s.setHeight(10);
		s.setWidth(212);
		s.setLinewidth(3);
		s.setGrayscale(80);
		s.setFilled(true);

		vb.addChild(s);

		Hbox hb = new Hbox("h", p);

		Variable t = new TextField("s2", p);
		t.setText("Kunde2");
		t.setBordered(true);
		t.setHeight(10);
		t.setWidth(50);
		t.setPaddingX(5);
		t.setText("");
		t.setVariable("name");

		hb.addChild(t);

		t = new TextField("s3", p);
		t.setText("Kunde2");
		t.setBordered(true);
		t.setHeight(10);
		t.setWidth(50);
		t.setPaddingX(5);
		t.setText("");
		t.setVariable("id");

		hb.addChild(t);

		vb.addChild(hb);

		p.addSection(vb);

		Verteiler v = new Verteiler("kunde", "name,id");
		p.addVerteiler(v);
		p.createController();
		p.layout();
		return p;
	}

	public Page createSumPage(String name) {
		Page p = new Page(name);

		Variable fname = createFeld("name", p);

		Variable fbetrag1 = createFeld("betrag1", p);

		Variable fbetrag2 = createFeld("betrag2", p);

		Variable fadd = createFeld("add", p);

		Variable fsum = createFeld("sum", p);

		Add add = new Add("betrag1,betrag2", "add", p.getData());
		Sum sum = new Sum("betrag1", "sum", p.getData());

		Hbox hbox1 = new Hbox("item", p);
		Hbox hbox2 = new Hbox("summe", p);

		hbox1.addChild(fname, fbetrag1, fbetrag2, fadd);
		hbox2.addChild(fsum);

		p.addSection(hbox1);
		p.addSection(hbox2);

		Verteiler v = new Verteiler("kunde", "name,id");
		v.addListener(sum);
		p.addVerteiler(v);

		v = new Verteiler("item", "betrag1,betrag2");
		v.addAction(add);
		v.addAction(sum);
		p.addVerteiler(v);
		p.createController();
		p.layout();
		return p;
	}

	private Variable createFeld(String name, Page p) {
		Variable f = new TextField(name, p);
		f.setVariable(name);
		f.setHeight(10);
		f.setWidth(50);
		return f;
	}

}
