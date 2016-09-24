package toni.druck.page;

/*****
 * 
 * @author Thomas Nill
 * 
 * Basisklasse für arithmetische Operationen
 * 
 * Datentyp
 * 
 */
import java.text.NumberFormat;

import org.apache.log4j.Logger;

import toni.druck.helper.GetNumberFormat;
import toni.druck.model.DataModel;

public abstract class Operation {
    private static final Logger LOG = Logger.getLogger(Operation.class.getName());


    private DataModel model;
    private Page page;

    public Operation() {
        super();
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
        setDataModel(page.getData());
    }

    public void setDataModel(DataModel model) {
        this.model = model;
        connect(model);
    }

    public long readMoney(int index) {
        String s = model.get(index);
        if (s == null || "".equals(s.trim())) {
            return 0;
        }
        try {
            NumberFormat f = GetNumberFormat.format.get();
            return Math.round(f.parse(s).doubleValue() * 100.00);
        } catch (Exception e) {
            LOG.error("can not read money", e);
            return 0;
        }
    }

    public long readLong(int index) {
        String s = model.get(index);
        if (s == null || "".equals(s.trim())) {
            return 0;
        }
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            LOG.error("can not read long", e);
            return 0;
        }
    }

    public String readString(int index) {
        return model.get(index);
    }

    public long[] readArguments(int[] indexe) {
        long[] erg= new long[indexe.length];
        for (int i = 0; i < indexe.length; i++) {
            erg[i] = readMoney(indexe[i]);
        }
        return erg;
    }

    public void writeMoney(int index, long l) {
        double d = l / 100.00;
        NumberFormat f = GetNumberFormat.format.get();
        String s = f.format(d);
        model.put(index, s);
    }

    public void writeLong(int index, long l) {
        String s = Long.toString(l);
        model.put(index, s);
    }

    public abstract void connect(DataModel source);

}