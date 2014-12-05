package toni.druck.elements;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import toni.druck.core.StandardElement;
import toni.druck.core2.Page;

public class Image extends StandardElement {
	private String filename;
	private int boundingBox[] = new int[4];

	public Image(String name, Page page) {
		super(name, page);
	}

	public Image() {
		super();
	}

	public String getImage() {
		return filename;
	}

	public void setImage(String filename) {
		this.filename = filename;
		readInputStream(filename);
	}

	private void readInputStream(String filename) {

		InputStream in = new BufferedInputStream(getClass().getClassLoader()
				.getResourceAsStream(filename));
		int count = 0;
		try {
			int old = 0;
			int neu = in.available();
			while (old != neu) {
				old = neu;
				in.read();
				neu = in.available();
				count++;
			}
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}
		;
		in = new BufferedInputStream(getClass().getClassLoader()
				.getResourceAsStream(filename));
		byte btext[] = new byte[count];
		try {
			in.read(btext);
			String text = new String(btext);
			setText(text);
			in.close();
			int posBoundingBox = text.indexOf("%%BoundingBox:")
					+ "%%BoundingBox:".length();
			StringBuffer s = new StringBuffer();
			char c = ' ';
			do {
				c = text.charAt(posBoundingBox);
				if (c == ' ' || (c >= '0' && c <= '9')) {
					posBoundingBox++;
					s.append(c);
				}
			} while (c == ' ' || (c >= '0' && c <= '9'));
			s.append(' ');
			String t = s.toString();
			String ss[] = t.split(" +");
			boundingBox[0] = Integer.parseInt(ss[1]);
			boundingBox[1] = Integer.parseInt(ss[2]);
			boundingBox[2] = Integer.parseInt(ss[3]);
			boundingBox[3] = Integer.parseInt(ss[4]);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int[] getBoundlingBox() {
		return boundingBox;
	}
}
