package MVC;

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
		this.setPreferredSize(new Dimension(700, 350));
		
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
	
	//Geef stats van de queues door
	public void giveCarQueues(int normalA, int passA, int reservationA, int payA, int exitA) {
		statsTextView.updateQueues(normalA, passA, reservationA, payA, exitA);
	}
	
	private class StatsTextView extends JPanel {
		//Titel
		private JLabel totalParkedCarsT;
		private JLabel parkedCarsT;
	    private JLabel parkedPassCarsT;
	    private JLabel parkedReservedCarsT;
	    private JLabel normalQueueT;
	    private JLabel passQueueT;
	    private JLabel reservationQueueT;
	    private JLabel payQueueT;
	    private JLabel exitQueueT;
	    
	    //Actueel
	    private JLabel totalParkedCarsAmount;
		private JLabel parkedCarsAmount;
	    private JLabel parkedPassCarsAmount;
	    private JLabel parkedReservedCarsAmount;
	    private JLabel normalQueueAmount;
	    private JLabel passQueueAmount;
	    private JLabel reservationQueueAmount;
	    private JLabel payQueueAmount;
	    private JLabel exitQueueAmount;
	   
		public StatsTextView() {
			//Maak een boxlayout van deze JPanel
			this.setLayout(new GridLayout(0,2));
			
			//Maak JLabels aan
	        totalParkedCarsT = new JLabel("Totaal aantal geparkeerde auto's: ");
	        parkedCarsT = new JLabel("Aantal regulier geparkeerde auto's: ");
	        parkedPassCarsT = new JLabel("Aantal abonnement geparkeerde auto's: ");
	        parkedReservedCarsT = new JLabel("Aantal gereserveerd geparkeerde auto's: ");
	        normalQueueT = new JLabel("Aantal reguliere klanten bij ingang: ");
	        passQueueT = new JLabel("Aantal abonnement klanten bij ingang: ");
	        reservationQueueT = new JLabel("Aantal gereserveerde klanten bij ingang: ");
	        payQueueT = new JLabel("Aantal klanten aan het betalen: ");
	        exitQueueT = new JLabel("Aantal klanten bij uitgang: ");
	        
	        //Maak JLabels aan
	        totalParkedCarsAmount = new JLabel("0");
	        parkedCarsAmount = new JLabel("0");
	        parkedPassCarsAmount = new JLabel("0");
	        parkedReservedCarsAmount = new JLabel("0");
	        normalQueueAmount = new JLabel("0");
	        passQueueAmount = new JLabel("0");
	        reservationQueueAmount = new JLabel("0");
	        payQueueAmount = new JLabel("0");
	        exitQueueAmount = new JLabel("0");
	           
	        //Maak de font groter
	        totalParkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedPassCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedReservedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        normalQueueT.setFont(new Font("", Font.PLAIN, 18));
	        passQueueT.setFont(new Font("", Font.PLAIN, 18));
	        reservationQueueT.setFont(new Font("", Font.PLAIN, 18));
	        payQueueT.setFont(new Font("", Font.PLAIN, 18));
	        exitQueueT.setFont(new Font("", Font.PLAIN, 18));
	        
	        //Maak de font groter
	        totalParkedCarsAmount.setFont(new Font("", Font.PLAIN, 18));
	        parkedCarsAmount.setFont(new Font("", Font.PLAIN, 18));
	        parkedPassCarsAmount.setFont(new Font("", Font.PLAIN, 18));
	        parkedReservedCarsAmount.setFont(new Font("", Font.PLAIN, 18));
	        normalQueueAmount.setFont(new Font("", Font.PLAIN, 18));
	        passQueueAmount.setFont(new Font("", Font.PLAIN, 18));
	        reservationQueueAmount.setFont(new Font("", Font.PLAIN, 18));
	        payQueueAmount.setFont(new Font("", Font.PLAIN, 18));
	        exitQueueAmount.setFont(new Font("", Font.PLAIN, 18));
	        
	        //Voeg JLabel toe
	        this.add(totalParkedCarsT);
	        this.add(totalParkedCarsAmount);
	        this.add(parkedCarsT);
	        this.add(parkedCarsAmount);
	        this.add(parkedPassCarsT);
	        this.add(parkedPassCarsAmount);
	        this.add(parkedReservedCarsT);
	        this.add(parkedReservedCarsAmount);
	        this.add(normalQueueT);
	        this.add(normalQueueAmount);
	        this.add(passQueueT);
	        this.add(passQueueAmount);
	        this.add(reservationQueueT);
	        this.add(reservationQueueAmount);
	        this.add(payQueueT);
	        this.add(payQueueAmount);
	        this.add(exitQueueT);
	        this.add(exitQueueAmount);
		}
		
		//Geef stats van de queues door
		public void updateQueues(int normalA, int passA, int reservationA, int payA, int exitA) {
			normalQueueAmount.setText(String.valueOf(normalA));
			passQueueAmount.setText(String.valueOf(passA));
			reservationQueueAmount.setText(String.valueOf(reservationA));
			payQueueAmount.setText(String.valueOf(payA));
			exitQueueAmount.setText(String.valueOf(exitA));
		}
		
		//Laat actuele stats zien
		public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
			totalParkedCarsAmount.setText(String.valueOf(totalC));
			parkedCarsAmount.setText(String.valueOf(parkedC));
			parkedPassCarsAmount.setText(String.valueOf(parkedPC));
			parkedReservedCarsAmount.setText(String.valueOf(parkedRC));
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
