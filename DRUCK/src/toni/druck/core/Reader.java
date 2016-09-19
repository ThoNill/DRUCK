/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package toni.druck.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import toni.druck.helper.DebugAssistent;
import toni.druck.page.DataItem;

/**
 * 
 * @author Thomas Nill
 * 
 *         Liest Daten aus einer Datei ein. Jede Zeile der Datei gibt ein
 *         DataItem
 * 
 */

public class Reader implements Runnable {
    static Logger cat = Logger.getLogger("Reader");

    private DataFIFO queue;
    private boolean isEmpty = false;
    private java.io.Reader input;

    public Reader(DataFIFO queue) {
        super();
        this.queue = queue;
        isEmpty = false;
    }

    public void run() {
        try {
            isEmpty = false;
            BufferedReader file = null;
            file = new BufferedReader(input, 1000);
            String line;

            line = file.readLine();
            while (line != null) {
                DataItem t = new DataItem(line);
                queue.offer(t);
                line = file.readLine();
            }
            queue.offer(new DataItem(DataItem.ENDOFFILE));
            file.close();
            isEmpty = true;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Exception ex) {
            DebugAssistent.log(ex);
        }

    }

    public void read(String filename) throws IOException {
        cat.debug("open " + filename);
        read(new FileReader(filename));
    }

    public void read(java.io.Reader input) {
        this.input = input;
        Thread t = new Thread(this);
        t.start();
    }

    public boolean isEmpty() {
        return isEmpty && queue.isEmpty();
    }
}
