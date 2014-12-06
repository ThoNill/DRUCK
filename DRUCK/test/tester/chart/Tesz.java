package tester.chart;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Tesz {

	public static JFreeChart generatePieChart() {
		DefaultPieDataset dataSet = new DefaultPieDataset();
		dataSet.setValue("China", 19.64);
		dataSet.setValue("India", 17.3);
		dataSet.setValue("United States", 4.54);
		dataSet.setValue("Indonesia", 3.4);
		dataSet.setValue("Brazil", 2.83);
		dataSet.setValue("Pakistan", 2.48);
		dataSet.setValue("Bangladesh", 2.38);

		JFreeChart chart = ChartFactory.createPieChart(
				"World Population by countries", dataSet, true, true, false);

		/*
		 * PiePlot plot = (PiePlot) chart.getPlot();
		 * plot.setSectionOutlinesVisible(false);
		 * plot.setNoDataMessage("No data available");
		 * plot.setBackgroundAlpha((float) 0.25);
		 */
		return chart;
	}

	public static JFreeChart generateBarChart() {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.setValue(791, "P", "1750 AD");
		dataSet.setValue(978, "Pop.", "1800 AD");
		dataSet.setValue(1262, "Pop.", "1850 AD");
		dataSet.setValue(1650, "Pop.", "1900 AD");
		dataSet.setValue(2519, "Pop.", "1950 AD");
		dataSet.setValue(6070, "Pop.", "2000 AD");

		JFreeChart chart = ChartFactory.createBarChart("World Pop. growth",
				"Year", "Pop. in millions", dataSet, PlotOrientation.VERTICAL,
				false, true, false);

		return chart;
	}

	public static void main(String[] args) {
		writeChartToPDF(generateBarChart(), 500, 400, "C:/print/barchart.pdf");
		writeChartToPDF(generatePieChart(), 500, 400, "C:/print/piechart.pdf");
		writeChartToPS(generatePieChart(), 400, 200, "C:/print/piechart2.ps");
	}

	public static void writeChartToPDF(JFreeChart chart, int width, int height,
			String fileName) {
		PdfWriter writer = null;

		Document document = new Document();

		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					fileName));
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			Graphics2D graphics2d = new PdfGraphics2D(contentByte, width,
					height, new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
					height);

			chart.draw(graphics2d, rectangle2d);

			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}

	public static void writeChartToPS(JFreeChart chart, int width, int height,
			String fileName) {

		try {

			EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
			g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());

			g2d.setupDocument(new FileOutputStream(fileName), width, height);
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,
					height);

			chart.draw(g2d, rectangle2d);

			// g2d.dispose();

			g2d.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}