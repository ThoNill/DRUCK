package toni.druck.elements;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import toni.druck.page.Page;
import toni.druck.standardElemente.StandardElement;

public class Image extends StandardElement {
    private static final Logger LOG = Logger.getLogger(Image.class.getName());

    private String filename;
    private int[] boundingBox = new int[4];

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

        int count = 0;
        try (InputStream in = new BufferedInputStream(getClass()
                .getClassLoader().getResourceAsStream(filename))) {
            int old = 0;
            int neu = in.available();
            while (old != neu) {
                old = neu;
                in.read();
                neu = in.available();
                count++;
            }

        } catch (IOException e) {
            LOG.error("can not read", e);
        }
        byte[] btext = new byte[count];
        try (InputStream in = new BufferedInputStream(getClass()
                .getClassLoader().getResourceAsStream(filename))) {
            int bytesRead = in.read(btext);
            if (bytesRead > 0) {
                extractBoundingBox(btext, in);
            }
        } catch (FileNotFoundException e) {
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }

    }

    private void extractBoundingBox(byte[] btext, InputStream in)
            throws IOException {
        String text = new String(btext);
        setText(text);
        in.close();
        int posBoundingBox = text.indexOf("%%BoundingBox:")
                + "%%BoundingBox:".length();
        StringBuilder s = new StringBuilder();
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
        String[] ss = t.split(" +");
        boundingBox[0] = Integer.parseInt(ss[1]);
        boundingBox[1] = Integer.parseInt(ss[2]);
        boundingBox[2] = Integer.parseInt(ss[3]);
        boundingBox[3] = Integer.parseInt(ss[4]);
    }

    public int[] getBoundlingBox() {
        return boundingBox;
    }
}
