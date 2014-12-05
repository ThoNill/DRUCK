package toni.druck.elements;

import toni.druck.core.StandardElement;
import toni.druck.core2.Page;

public class MultiLine extends StandardElement {

	int rows;
	int charsInRow = 30;

	public MultiLine(String name, Page page) {
		super(name, page);
	}

	public MultiLine() {
		super();
	}

	public int getCharsInRow() {
		return charsInRow;
	}

	public void setCharsInRow(int charsInRow) {
		this.charsInRow = charsInRow;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public String[] getTexte() {
		String t = getText();
		if (t == null) {
			return new String[] { "" };
		}
		// System.out.println(t);
		if (t.length() <= charsInRow) {
			String erg[] = new String[1];
			erg[0] = t;
			return erg;
		}
		String erg[] = new String[neededLines(t)];
		for (int i = 0; i < erg.length; i++) {
			if (t.length() > charsInRow) {
				String p = t.substring(0, charsInRow);
				if (Character.isSpace(p.charAt(charsInRow - 1))) {
					erg[i] = p.trim();
					t = t.substring(charsInRow).trim();
				} else {
					int li = p.lastIndexOf(' ');
					if (li >= 0) {
						erg[i] = t.substring(0, li).trim();
						t = t.substring(li).trim();
					} else {
						erg[i] = p;
						t = t.substring(charsInRow).trim();
					}
				}
			} else {
				if (t.length() > 0)
					erg[i] = t.trim();
				else
					erg[i] = "";
			}
		}
		return erg;
	}

	public int neededLines(String t) {
		int lines = 0;
		while (t.length() > charsInRow) {
			String p = t.substring(0, charsInRow);
			if (Character.isSpace(p.charAt(charsInRow - 1))) {
				t = t.substring(charsInRow).trim();
			} else {
				int li = p.lastIndexOf(' ');
				if (li >= 0) {
					t = t.substring(li).trim();
				} else {
					t = t.substring(charsInRow).trim();
				}
			}
			lines++;
		}
		;
		lines++;
		return lines;
	}

}
