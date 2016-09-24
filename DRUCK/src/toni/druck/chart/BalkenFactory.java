package toni.druck.chart;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

/**
 * 
 * @author Thomas Nill
 * 
 *         Dient dazu, um eine Balkengrafik auszugeben
 * 
 */
public class BalkenFactory implements DruckChartFactory {
    private String title;
    private String xtitle;
    private String ytitle;
    private int minX = 1;
    private int groupSize = 2;
    private boolean fehlendeWerteFuellen = false;

    public Boolean getFehlendeWerteFuellen() {
        return fehlendeWerteFuellen;
    }

    public void setFehlendeWerteFuellen(Boolean fehlendeWerteFuellen) {
        this.fehlendeWerteFuellen = fehlendeWerteFuellen;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXtitle() {
        return xtitle;
    }

    public void setXtitle(String xtitle) {
        this.xtitle = xtitle;
    }

    public String getYtitle() {
        return ytitle;
    }

    public void setYtitle(String ytitle) {
        this.ytitle = ytitle;
    }

    @Override
    public JFreeChart getChart(Chart chart, int theme) {
        String[] values= chart.getVariablen();
        if (values != null) {
            DefaultCategoryDataset dataSet = fülleDasDataSet(chart, values);
            JFreeChart jfc = ChartFactory.createBarChart(getTitle(),
                    getXtitle(), getYtitle(), dataSet,
                    PlotOrientation.VERTICAL, false, true, false);
            if (theme == PS_THEMA) {
                setThema(jfc);
            }
            return jfc;
        } else {
            return null;
        }
    }

    // Wegen Benutzung in Tests ist diese Methode public
    public DefaultCategoryDataset fülleDasDataSet(Chart chart, String[] values) {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int j = 0; j < values.length; j++) {
            if (values[j] != null && !"".equals(values[j].trim())) {
                String gruppenName = "group " + j;
                String[] daten = values[j].split("#");
                füllenDerDatenFürEineGruppe(dataSet, gruppenName, daten);
            }
        }
        return dataSet;
    }

    private void füllenDerDatenFürEineGruppe(DefaultCategoryDataset dataSet,
            String gruppenName, String[] daten) {
        int groupSize = getGroupSize();
        if (daten.length % groupSize == 0 && daten.length > 0) {
            int von = getMinX();
            for (int i = 0; i < daten.length; i += groupSize) {
                if (getFehlendeWerteFuellen()) {
                    int bis = Integer.parseInt(daten[i]);
                    fehlendeWerteAuffüllen(dataSet, gruppenName, von, bis - 1);
                    von = bis + 1;
                }
                int pos = (groupSize == 2) ? i : i + 2;
                dataSet.setValue(Double.parseDouble(daten[i + 1]), gruppenName,
                        daten[pos]);
            }
        }
    }

    private void fehlendeWerteAuffüllen(DefaultCategoryDataset dataSet,
            String groupName, int von, int bis) {
        for (int i = von; i <= bis; i++) {
            dataSet.setValue(0.0, groupName, "" + i);
        }
    }

    private void setThema(JFreeChart chart) {
        StandardChartTheme theme = (StandardChartTheme) StandardChartTheme
                .createJFreeTheme();

        theme.setTitlePaint(Color.decode("#4572a7"));
        theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint(Color.white);
        theme.setChartBackgroundPaint(Color.white);
        theme.setGridBandPaint(getGray(100));
        theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint(Color.decode("#666666"));
        theme.apply(chart);
    }

    private Color getGray(int scale) {
        return new Color(scale, scale, scale);
    }
}
