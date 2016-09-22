package toni.druck.elements;

import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;

public class MultiLine extends StandardElement {

    private int rows;
    private int charsInRow = 30;

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
        String[] erg ;
        if (t == null) {
            return new String[] { "" };
        }
        if (t.length() <= charsInRow) {
            erg = new String[1];
            erg[0] = t;
            return erg;
        }
        erg = new String[neededLines(t)];
        for (int i = 0; i < erg.length; i++) {
            if (t.length() > charsInRow) {
                String p = t.substring(0, charsInRow);
                t = fillNextLineIntoErg(t, erg, i, p);
            } else {
                if (t.length() > 0)
                    erg[i] = t.trim();
                else
                    erg[i] = "";
            }
        }
        return erg;
    }

    private String fillNextLineIntoErg(String t, String[] erg, int i, String p) {
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
        return t;
    }

    public int neededLines(String t) {
        int lines = 0;
        while (t.length() > charsInRow) {
            String p = t.substring(0, charsInRow);
            t = nextLine(t, p);
            lines++;
        }
        lines++;
        return lines;
    }

    private String nextLine(String t, String p) {
        if (Character.isSpace(p.charAt(charsInRow - 1))) {
            return t.substring(charsInRow).trim();
        } else {
            int li = p.lastIndexOf(' ');
            if (li >= 0) {
                return t.substring(li).trim();
            } else {
                return t.substring(charsInRow).trim();
            }
        }
    }

}
