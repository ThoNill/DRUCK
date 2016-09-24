package tester.filter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import toni.druck.filter.AppendFilter;
import toni.druck.filter.Filter;
import toni.druck.filter.FilterGroup;
import toni.druck.filter.IfExistsFilter;

public class TestDerFilter {

    @Test
    public void testVerkettung() {
        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");
        a.addFollower(b);
        b.addFollower(c);

        TestDataItem item = new TestDataItem("test");

        a.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(1, c.getCount());

        assertArrayEquals(new String[] { "a", "b", "c" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testVerkettung2() {
        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");

        a.addFollower(b);
        try {
            a.addFollower(b);
            fail("Exception fehlt");
        } catch (RuntimeException ex) {

        }

    }

    @Test
    public void testVerkettung3() {

        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");

        group.addFollower(a);
        try {
            group.addFollower(a);
            fail("Exception fehlt");
        } catch (RuntimeException ex) {

        }

    }

    @Test
    public void testFilterGroup() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");
        group.addFollower(a);
        group.addFollower(b);
        group.addFollower(c);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(1, c.getCount());

        assertArrayEquals(new String[] { "a", "b", "c" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testFilterGroupAddFollower2() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        group.addFollower(a);
        group.addFollower(b);
        assertEquals(2, group.getFilterCount());

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());

        assertArrayEquals(new String[] { "a", "b" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testFilterGroupAddFollower() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");
        group.addFollower(a);
        group.addFollower(b);
        group.addFollower(c);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(1, c.getCount());

        assertArrayEquals(new String[] { "a", "b", "c" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testRemoveFilterFromFilterGroup1() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");

        group.addFollower(a);
        group.addFollower(b);
        group.addFollower(c);

        group.removeFollower(a);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(0, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(1, c.getCount());

        assertArrayEquals(new String[] { "b", "c" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testRemoveFilterFromFilterGroup2() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");

        group.addFollower(a);
        group.addFollower(b);
        group.addFollower(c);

        group.removeFollower(b);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(0, b.getCount());
        assertEquals(1, c.getCount());

        assertArrayEquals(new String[] { "a", "c" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testRemoveFilterFromFilterGroup3() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");

        group.addFollower(a);
        group.addFollower(b);
        group.addFollower(c);

        group.removeFollower(c);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(0, c.getCount());

        assertArrayEquals(new String[] { "a", "b" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testRemove2FilterFromFilterGroup4() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");

        group.addFollower(a);
        group.addFollower(b);

        group.removeFollower(c);

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(1, a.getCount());
        assertEquals(1, b.getCount());
        assertEquals(0, c.getCount());

        assertArrayEquals(new String[] { "a", "b" }, item.getFilterReihe()
                .toArray());

    }

    @Test
    public void testEmptyFilterGroup() {
        FilterGroup group = new FilterGroup();

        TestFilter a = new TestFilter("a");
        TestFilter b = new TestFilter("b");
        TestFilter c = new TestFilter("c");

        TestDataItem item = new TestDataItem("test");

        group.receive(item);

        assertEquals(0, a.getCount());
        assertEquals(0, b.getCount());
        assertEquals(0, c.getCount());

        assertArrayEquals(new String[] {}, item.getFilterReihe().toArray());

    }

    private void recorderTest(Filter filter, String in[], String out[]) {
        RecorderFilter recorder = new RecorderFilter();

        filter.addFollower(recorder);

        ItemGenarator generator = new ItemGenarator(in);
        generator.send(filter);

        assertArrayEquals(out, recorder.getCommands().toArray());
    }
    
    private void recorderTest1(Filter filter, String in[], String out[]) {
        RecorderFilter recorder = new RecorderFilter();

        filter.addFollower(recorder);

        ItemGenarator generator = new ItemGenarator(in);
        generator.send(filter);

        System.out.println("in=");
        for(String t: in) {
        System.out.print(t +", ");
        }
        System.out.println();
        System.out.println(recorder.getCommands());
        System.out.println("out=");
        for(String t: out) {
            System.out.print(t + ", ");
            }
            System.out.println();
        assertArrayEquals(out, recorder.getCommands().toArray());
    }

    @Test
    public void testAppendFilter() {
        AppendFilter filter = new AppendFilter();
        filter.setCommand("ziel");
        filter.setAppend("dazu1,dazu2");

        recorderTest(
                filter,
                new String[] { "eins", "zwei", "ziel", "eins" },
                new String[] { "eins", "zwei", "ziel", "dazu1", "dazu2", "eins" });

        filter.setPre(true);

        recorderTest(
                filter,
                new String[] { "eins", "zwei", "ziel", "eins" },
                new String[] { "eins", "zwei", "dazu1", "dazu2", "ziel", "eins" });
    }

    @Test
    public void testIfExistsFilter1() {
        IfExistsFilter filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "ok", "ziel" }, new String[] { "eins", "zwei", "ziel", "ok" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "zwei", "ok", "drei", "ziel" }, new String[] { "eins", "zwei",
                "ziel", "zwei", "ok", "drei" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "ok", "ziel", "endOfFile" }, new String[] { "eins", "zwei",
                "ziel", "ok", "endOfFile" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "zwei", "ok", "drei", "ziel", "endOfFile" }, new String[] {
                "eins", "zwei", "ziel", "zwei", "ok", "drei", "endOfFile" });
        
        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
      
        recorderTest1(filter, new String[] { 
                "eins", "zwei", 
                "ziel", "viel",
                "ziel",  "zwei", "ok", "drei", 
                "ziel", "eins", "endOfFile" }, new String[] {
                "eins", "zwei", 
                "ziel", "zwei","ok","drei", "endOfFile" });

    }

    @Test
    public void testIfExistsFilter2() {
        IfExistsFilter filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
        filter.setExclude(true);

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "ok", "ziel" }, new String[] { "eins", "zwei", "ziel" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
        filter.setExclude(true);

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "zwei", "ok", "drei", "ziel" }, new String[] { "eins", "zwei",
                "ziel" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
        filter.setExclude(true);

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "ziel",
                "ok", "ziel", "endOfFile" }, new String[] { "eins", "zwei",
                "ziel", "ziel", "endOfFile" });

        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
        filter.setExclude(true);

        recorderTest(filter, new String[] { "eins", "zwei", 
                "ziel", 
                "ziel","zwei", "ok", "drei", 
                "ziel", "endOfFile" }, new String[] {
                "eins", "zwei", "ziel", "ziel", "endOfFile" });
        
        filter = new IfExistsFilter();
        filter.setCommand("ziel");
        filter.setExists("ok");
        filter.setExclude(true);

        recorderTest(filter, new String[] { "eins", "zwei", "ziel", "viel","ziel",
                "zwei", "ok", "drei", "ziel", "eins", "endOfFile" }, new String[] {
                "eins", "zwei", "ziel", "viel","ziel", "eins","endOfFile" });
    }

}
