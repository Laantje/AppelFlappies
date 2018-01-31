package MVC;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineGraphView extends JPanel {
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private XYDataset dataset;
	private XYSeries series1;
    private XYSeries series2;
    private XYSeries series3;
    private XYSeries series4;
	private int totalC = 0;
	private int parkedC = 0;
	private int parkedPC = 0;
	private int parkedRC = 0;
	private int tempTotalC = 0;
	private int tempParkedC = 0;
	private int tempParkedPC = 0;
	private int tempParkedRC = 0;
	private int hour = 0;
	private boolean isCreated;
	
	public LineGraphView() {
		tempTotalC = totalC;
		tempParkedC = parkedC;
		tempParkedPC = parkedPC;
		tempParkedRC = parkedRC;
		
		//Maak series aan
		series1 = new XYSeries("Totaal");
        series2 = new XYSeries("Regulier");
        series3 = new XYSeries("Abonnement");
        series4 = new XYSeries("Gereserveerd");
        
        dataset = createDataset();
        this.chart = createChart(dataset);
        
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(550,350));
        this.add(chartPanel);
        
        isCreated = false;
	}
	
	//Laat actuele stats zien
	public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
		this.totalC = totalC;
		this.parkedC = parkedC;
		this.parkedPC = parkedPC;
		this.parkedRC = parkedRC;
	}
	
	//Laat de uur zien
	public void updateHour(int h) {
		if(hour != h) {
			hour = h;
			
			if(hour == 0) {
				tempTotalC = totalC;
				tempParkedC = parkedC;
				tempParkedPC = parkedPC;
				tempParkedRC = parkedRC;
			} else if(hour == 1) {
				series1.clear();
				series2.clear();
				series3.clear();
				series4.clear();
				
				series1.add(0, tempTotalC);
				series2.add(0, tempParkedC);
				series3.add(0, tempParkedPC);
				series4.add(0, tempParkedRC);
			}
		
			if(hour != 0) {
				series1.add(hour, totalC);
				series2.add(hour, parkedC);
				series3.add(hour, parkedPC);
				series4.add(hour, parkedRC);
			}
		}
	}
	
	private XYDataset createDataset() {

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        dataset.addSeries(series4);
                
        return dataset;
        
    }
	
	private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Aantal Geparkeerde Auto's",      // chart title
            "Tijd(uur)",                      // x axis label
            "Aantal",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
                
        return chart;
        
    }
}