package MVC;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class BarGraphView extends JPanel {
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private int totalC = 0;
	private int parkedC = 0;
	private int parkedPC = 0;
	private int parkedRC = 0;
	private DefaultCategoryDataset dataset;

	public BarGraphView() {
		dataset = new DefaultCategoryDataset();
		
		this.chart = createBarChart();
		
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(550,350));
		this.add(chartPanel);
	}
	
	//Laat actuele stats zien
	public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
		this.totalC = totalC;
		this.parkedC = parkedC;
		this.parkedPC = parkedPC;
		this.parkedRC = parkedRC;
		
	    dataset.addValue( totalC, "Totaal", "Soorten Klanten");
	    dataset.addValue( parkedC, "Regulier", "Soorten Klanten");
	    dataset.addValue( parkedPC, "Abonnement", "Soorten Klanten");
	    dataset.addValue( parkedRC, "Reservering", "Soorten Klanten");
	}
	
	private JFreeChart createBarChart() {
        // create the chart...
        return ChartFactory.createBarChart(
            "Aantal Geparkeerde Auto's",
            "Klanten", "Aantal",
            dataset,
            PlotOrientation.VERTICAL,
            true,     // include legend
            true,
            false
        );
    }
	
}