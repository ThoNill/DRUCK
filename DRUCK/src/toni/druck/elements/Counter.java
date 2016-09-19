package toni.druck.elements;

import toni.druck.model.DataModel;
import toni.druck.page.Action;
import toni.druck.page.Operation;
import toni.druck.page.Page;
import toni.druck.page.PrintEvent;
import toni.druck.page.PrintListener;

public class Counter extends Operation implements Action, PrintListener {

    private String count;

    private int icount;

    private String clearAt;

    public Counter(String scount, DataModel m) {
        super();
        setCount(scount);
        setDataModel(m);
        connect(m);
    }

    public void setClearAt(String clearAt) {
        this.clearAt = clearAt;
    }

    public Counter() {
        super();
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public void connect(DataModel source) {
        icount = source.getIndex(count);
    }

    protected void inc() {
        writeLong(icount, readLong(icount) + 1);
    }

    protected void set0() {
        writeLong(icount, 0L);
    }

    public void listenTo(PrintEvent ev) {
        set0();
    }

    public void perform() {
        inc();
    }

    @Override
    public void setPage(Page page) {
        super.setPage(page);
        if (clearAt != null) {
            page.addPrintListener(clearAt, this);
        }
    }

}
