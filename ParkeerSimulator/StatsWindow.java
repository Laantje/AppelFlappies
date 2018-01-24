package ParkeerSimulator;

import javax.swing.*;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsWindow extends JFrame {
	private StatsTextView statsTextView;
	private StatsGraphView statsGraphView;
	private int totalParkedCars;
	
	public StatsWindow() {
		//Create Dimension
		this.setPreferredSize(new Dimension(500, 250));
		
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
		private JPanel cards;
		private JLabel checkBoxText;
		private JLabel circleText;
		private JLabel lineText;
		private JLabel barText;
		private final static String circlePanelT = "Cirkel Diagram";
		private final static String linePanelT = "Lijn Diagram";
		private final static String barPanelT = "Staaf Diagram";
		
		public StatsGraphView() {
			//Init borderlayout
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Initializeer JPanel
			cards = new JPanel(new CardLayout());
			
			//Maak nieuwe combobox aan
			String comboBoxItems[] = { circlePanelT, linePanelT, barPanelT };
	        JComboBox comboBox = new JComboBox(comboBoxItems);
	        comboBox.setEditable(false);
	        comboBox.addItemListener(new cardEvents());
	        
			//Maak Jlabel aan
	        checkBoxText = new JLabel("Kies soort diagram:");
	        circleText = new JLabel("Hier komt een cirkel diagram");
	        lineText = new JLabel("Hier komt een lijn diagram");
	        barText = new JLabel("Hier komt een staaf diagram");
	        
	        //Maak keuze jpanels aan
	        JPanel circlePanel = new JPanel();
			JPanel linePanel = new JPanel();
			JPanel barPanel = new JPanel();
			
			//Voeg objecten toe aan de cards
			circlePanel.add(circleText);
			linePanel.add(lineText);
			barPanel.add(barText);
			
			//Voeg de cards toe aan cards
			cards.add(circlePanel, circlePanelT);
	        cards.add(linePanel, linePanelT);
	        cards.add(barPanel, barPanelT);
	        
	        //Zet alles in het midden
	        checkBoxText.setAlignmentX(Component.CENTER_ALIGNMENT);
	        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	        cards.setAlignmentX(Component.CENTER_ALIGNMENT);
	        
	        //Combobox width aanpassen
	        comboBox.setMaximumSize(new Dimension(200, comboBox.getPreferredSize().height));
	        
	        //Voeg de objects toe aan deze JPanel
			this.add(checkBoxText);
			this.add(comboBox);
			this.add(cards);
		}
		
		//Maak class vor event aan
		private class cardEvents implements ItemListener  {
			public void itemStateChanged(ItemEvent evt) {
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, (String)evt.getItem());
		    }
		}
	}
}
