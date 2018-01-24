package ParkeerSimulator;

import javax.swing.*;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsWindow extends JFrame {
	private StatsTextView statsTextView;
	private StatsGraphView statsGraphView;
	private int totalParkedCars;
	
	public StatsWindow() {
		//Create Dimension
		this.setPreferredSize(new Dimension(400, 200));
		
		//Maak JPanels aan
		statsTextView = new StatsTextView();
		statsGraphView = new StatsGraphView();

		//Voeg JPanels toe aan contentpane
	    Container contentPane = getContentPane();
	    contentPane.add(statsTextView, BorderLayout.NORTH);
	    contentPane.add(statsGraphView, BorderLayout.SOUTH);
	    pack();
	}
	
	//Geef stats door aan statsView
	public void giveStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
		   statsTextView.updateStats(totalC, parkedC, parkedPC, parkedRC);
	}
	
	private class StatsTextView extends JPanel {
		private JLabel totalParkedCarsT;
		private JLabel parkedCarsT;
	    private JLabel parkedPassCarsT;
	    private JLabel parkedReservedCarsT;
	   
		public StatsTextView() {
			//Maak een boxlayout van deze JPanel
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Maak JLabel voor tijd en dag aan
	        totalParkedCarsT = new JLabel("0");
	        parkedCarsT = new JLabel("0");
	        parkedPassCarsT = new JLabel("0");
	        parkedReservedCarsT = new JLabel("0");
	           
	        //Maak de font groter
	        totalParkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedPassCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedReservedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        
	        //Zet de labels in het midden
	        totalParkedCarsT.setAlignmentX(Component.CENTER_ALIGNMENT);
	        parkedCarsT.setAlignmentX(Component.CENTER_ALIGNMENT);
	        parkedPassCarsT.setAlignmentX(Component.CENTER_ALIGNMENT);
	        parkedReservedCarsT.setAlignmentX(Component.CENTER_ALIGNMENT);
	           
	        //Voeg JLabel toe
	        this.add(totalParkedCarsT);
	        this.add(parkedCarsT);
	        this.add(parkedPassCarsT);
	        this.add(parkedReservedCarsT);
		}
		
		//Laat actuele stats zien
		public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
			totalParkedCarsT.setText("Totaal aantal geparkeerde auto's: " + String.valueOf(totalC));
			parkedCarsT.setText("Aantal willekeurig geparkeerde auto's: " + String.valueOf(parkedC));
			parkedPassCarsT.setText("Aantal abonnement geparkeerde auto's: " + String.valueOf(parkedPC));
			parkedReservedCarsT.setText("Aantal gereserveerde geparkeerde auto's: " + String.valueOf(parkedRC));
		}
	}
	
	//Class voor het laten zien van verschillende grafieken
	private class StatsGraphView extends JPanel {
		private JLabel checkBoxText;
		private final static String circlePanelT = "Cirkel Diagram";
		private final static String linePanelT = "Lijn Diagram";
		private final static String barPanelT = "Staaf Diagram";
		
		public StatsGraphView() {
			//Zet de layout van deze JPanel
			this.setLayout(new CardLayout());
			
			//Maak nieuwe combobox aan
			String comboBoxItems[] = { circlePanelT, linePanelT, barPanelT };
	        JComboBox comboBox = new JComboBox(comboBoxItems);
	        comboBox.setEditable(false);
	        //comboBox.addItemListener(this);
	        
			//Maak Jlabel aan
	        checkBoxText = new JLabel("Kies soort diagram:");
	        
	        //Maak keuze jpanels aan
	        JPanel circlePanel = new JPanel();
			JPanel linePanel = new JPanel();
			JPanel barPanel = new JPanel();
	        
	        //Voeg de objects toe aan het scherm
			this.add(checkBoxText);
			this.add(comboBox);
			this.add(circlePanel, circlePanelT);
	        this.add(linePanel, linePanelT);
	        this.add(barPanel, barPanelT);
		}
	}
}
