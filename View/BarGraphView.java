package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraphView extends JPanel {
	private static final long serialVersionUID = 1L;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private DefaultCategoryDataset dataset;
	private Color purple;

	public BarGraphView() {
		dataset = new DefaultCategoryDataset();
		
		//Maak color aan
		purple = new Color(106, 60, 137);
		
		this.chart = createBarChart();
		
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(550,350));
		this.add(chartPanel);
	}
	
	//Laat actuele stats zien
	public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {		
	    dataset.addValue( totalC, "Totaal", "Soorten Klanten");
	    dataset.addValue( parkedC, "Regulier", "Soorten Klanten");
	    dataset.addValue( parkedPC, "Abonnement", "Soorten Klanten");
	    dataset.addValue( parkedRC, "Reservering", "Soorten Klanten");
	}
	
	private JFreeChart createBarChart() {
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Aantal Geparkeerde Auto's",
            "Klanten", "Aantal",
            dataset,
            PlotOrientation.VERTICAL,
            true,     // include legend
            true,
            false
        );
        
        //set  bar chart color
        CategoryPlot cplot = (CategoryPlot)chart.getPlot();
        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());

        BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, Color.GREEN);
        r.setSeriesPaint(1, Color.RED);
        r.setSeriesPaint(2, Color.BLUE);
        r.setSeriesPaint(3, purple);
        
        return chart;
    }
	
}