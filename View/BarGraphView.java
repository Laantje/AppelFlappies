package View;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarGraphView extends JPanel {
	private static final long serialVersionUID = 1L;
	private ChartPanel chartPanel;
	private JFreeChart chart;
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