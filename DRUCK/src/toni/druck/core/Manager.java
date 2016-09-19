package toni.druck.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom2.Element;

import toni.druck.filter.BasisFilter;
import toni.druck.filter.Filter;
import toni.druck.filter.FilterFactory;
import toni.druck.filter.FilterGroup;
import toni.druck.page.DataItem;
import toni.druck.page.Page;
import toni.druck.page.PageRenderer;

/**
 * 
 * @author Thomas Nill
 * 
 *         Verwaltet das Zusammenspiel des Einlesens und verarbeiten der DatItem
 * 
 *         Der reader liest die Daten von einer Date ein und erzeugt DataItems
 *         Diese DataItems werden in eine FIFO datenQueue zwischengespeichert.
 *         Bei der Verarbeitung werden durch den Manager Sonderbefehle
 *         abgefangen
 * 
 *         print - eine neue Seite erzwingen layout| Layoutname -- ein neues
 *         Layout laden output|Name der Ausgabedatei -- Ausgabe festlegen
 *         include| Dateiname -- zusätzliche Daten z.Bsp Skripte zur Ausgabe
 *         einlesen.
 * 
 */

public class Manager extends BasisFilter {
    private static final Logger LOG = Logger.getLogger("Manager");

    private Reader reader = null; // verantwortlich für das Einlesen der Daten
    private DataFIFO datenQueue; // zwischenspeichern, damit auf nachfolgende
                                 // Items zugegriffen werden kann
    private FilterGroup filterGroup = null; // Filter der DataItems
    private Page page; // aktuelles Layout, das verwendet wird
    private PageRenderer renderer; // Art der Ausgabe: Postscript, PDF

    public Manager(PageLoader loader, PageRenderer renderer) {
        this.renderer = renderer;
        datenQueue = new DataFIFO(10000, loader);
    }

    public void print(String dataFileName) {
        try {
            print(new FileReader(dataFileName));
        } catch (FileNotFoundException e) {
            LOG.error(e);
        }
    }

    public void print(java.io.Reader input) {
        try {
            reader = new Reader(datenQueue);
            reader.read(input);

            do {
                DataItem item;
                item = datenQueue.take();
                getFilter().receive(item);

            } while (!reader.isEmpty());

            finishPage();
            renderer.endDocument();
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }

    @Override
    public void receive(DataItem item) {
        if (item != null
                && (doLoadLayout(item) || doOutput(item) || doPrint(item)
                        || doInclude(item) || doData(item))) {

        }
    }

    private boolean doInclude(DataItem d) {
        boolean ok = DataItem.INCLUDE.equals(d.getCommand());
        if (ok) {
            try {
                renderer.include(d.getData(1));
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return ok;
    }

    private void finishPage() {
        if (page != null) {
            page.finish(renderer);
            page = null;
        }
    }

    private boolean doLoadLayout(DataItem d) {
        boolean ok = DataItem.LAYOUT.equals(d.getCommand());
        if (ok) {
            if (page != null) {
                transferData(d, page, d.getPage());
                page.printNewPage(renderer);
            }
            finishPage();
            setPage(d);
        }
        return ok;
    }

    protected void transferData(DataItem d, Page oldPage, Page newPage) {
        if (d == null || oldPage == null || newPage == null)
            return;

        int size = d.getSize();
        for (int pos = 2; pos < size; pos++) {
            String name = d.getData(pos);
            newPage.putData(name, oldPage.getData(name));
        }
    }

    private void setPage(DataItem d) {
        page = d.getPage();
        Vector<Element> filterElements = page.getFilterElements();
        if (filterElements != null) {
            filterGroup = FilterFactory.getFilterGroup(filterElements);
            filterGroup.addFollower(this);
        }
    }

    private boolean doOutput(DataItem d) {
        boolean ok = DataItem.OUTPUT.equals(d.getCommand());
        if (ok) {
            try {
                renderer.setOutput(d.getData(1));

            } catch (FileNotFoundException e) {
                LOG.error(e);

            }

        }
        return ok;
    }

    private boolean doPrint(DataItem d) {
        boolean ok = DataItem.PRINT.equals(d.getCommand())
                || DataItem.ENDOFFILE.equals(d.getCommand());
        if (ok) {
            page.printNewPage(renderer);
        }
        ;
        return ok;
    }

    private boolean doData(DataItem d) {
        page = d.getPage();
        if (page != null) {
            page.printSectionOfTheItem(d, renderer);
        }
        return true;
    }

    private Filter getFilter() {
        if (filterGroup == null) {
            return this;
        }
        return filterGroup;
    }

}
