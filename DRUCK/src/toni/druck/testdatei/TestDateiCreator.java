package toni.druck.testdatei;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Vector;

import toni.druck.core2.Page;
import toni.druck.core2.Verteiler;
import toni.druck.xml.XMLPageLoader;

public class TestDateiCreator {
	Page page;
	Vector<TestDateiVerteiler> verteiler = new Vector<TestDateiVerteiler>();
	String outDir;
	String inDir;
	
	



	public TestDateiCreator(String outDir, String inDir) {
		super();
		this.outDir = outDir;
		this.inDir = inDir;
	}

	public void read(BufferedReader in) throws IOException {
		String line = in.readLine();
		while (line != null) {
			line = line.trim();
			if (!"".equals(line)) {
				String split[] = line.split(":");
				Verteiler v = page.getVerteiler(split[0]);
				int count = (split.length == 2) ? Integer.parseInt(split[1])
						: 1;
				verteiler.add(new TestDateiVerteiler(v, count));
			}
			line = in.readLine();
		}
		in.close();
	}

	public void print(Writer out) throws IOException {
		out.write("output|");
		out.write(outDir + page.getName());
		out.write(".ps\n");
		out.write("layout|");
		out.write(inDir + page.getName());
		out.write("\n");
		for (TestDateiVerteiler v : verteiler) {
			v.print(out);
		}
		out.write("print\n");
		out.close();
	}

	public static void main(String args[]) {
		try {
			String pagename = args[0];
			TestDateiCreator creator = new TestDateiCreator("","");
			creator.loadAndWrite(pagename);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadAndWrite(String pagename) throws FileNotFoundException,
			IOException {
		XMLPageLoader loader = new XMLPageLoader();
		page = loader.createPage(pagename);
		read(new BufferedReader(new FileReader(pagename + ".testdescr")));
		print(new BufferedWriter(new FileWriter("test" + pagename + ".txt")));
	}

	public void loadAndWrite(Page page, InputStream in, OutputStream out)
			throws FileNotFoundException, IOException {
		this.page = page;
		read(new BufferedReader(new InputStreamReader(in, "ISO-8859-1")));
		print(new BufferedWriter(new OutputStreamWriter(out, "ISO-8859-1")));
	}

}
