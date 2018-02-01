package Window;

import javax.swing.*;

import Logic.Model;

import View.*;
import java.awt.*;
import java.awt.event.*;

public class StatsWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private StatsTextView statsTextView;
	private StatsGraphView statsGraphView;
	
	public StatsWindow() {
		//Create Dimension
		this.setPreferredSize(new Dimension(700, 720));
		
		//Maak JPanels aan
		statsTextView = new StatsTextView();
		statsGraphView = new StatsGraphView();
		
		
		 
		//Voeg JPanels toe aan contentpane
	    Container contentPane = getContentPane();
	    contentPane.add(statsTextView, BorderLayout.NORTH);
	    contentPane.add(statsGraphView, BorderLayout.CENTER);
	    //contentPane.add(pieChart, BorderLayout.CENTER);
	    pack();
	}
	
	//Geef stats door aan statsView
	public void giveStats(int totalC, int parkedC, int parkedPC, int parkedRC, int totalCash, int expectedCash) {
		statsTextView.updateStats(totalC, parkedC, parkedPC, parkedRC, totalCash, expectedCash);
		statsGraphView.updateStats(totalC, parkedC, parkedPC, parkedRC);
	}
	
	public void giveHour(int h) {
		statsGraphView.updateHour(h);
	}
	
	//Geef stats van de queues door
	public void giveCarQueues(int normalA, int passA, int reservationA, int payA, int exitA) {
		statsTextView.updateQueues(normalA, passA, reservationA, payA, exitA);
	}
	

	private class StatsTextView extends JPanel {
    private static final long serialVersionUID = 1L;
		//Titel
		private JLabel totalParkedCarsT;
		private JLabel parkedCarsT;
	    private JLabel parkedPassCarsT;
	    private JLabel parkedReservedCarsT;
	    private JLabel moneyTotalT;
	    private JLabel moneyExpectedT;
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
	    private JLabel moneyTotalAmount;
	    private JLabel moneyExpectedAmount;
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
	        moneyTotalT = new JLabel("Geld verdient: ");
	        moneyExpectedT = new JLabel("Verwachte inkomsten: ");
	        
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
	        moneyTotalAmount = new JLabel("0");
	        moneyExpectedAmount = new JLabel("0");
	           
	        //Maak de font groter
	        totalParkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedPassCarsT.setFont(new Font("", Font.PLAIN, 18));
	        parkedReservedCarsT.setFont(new Font("", Font.PLAIN, 18));
	        moneyTotalT.setFont(new Font("", Font.PLAIN, 18));
	        moneyExpectedT.setFont(new Font("", Font.PLAIN, 18));
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
	        moneyTotalAmount.setFont(new Font("", Font.PLAIN, 18));
	        moneyExpectedAmount.setFont(new Font("", Font.PLAIN, 18));
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
	        this.add(moneyTotalT);
	        this.add(moneyTotalAmount);
	        this.add(moneyExpectedT);
	        this.add(moneyExpectedAmount);
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
		public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC, int totalCash, int expectedCash) {
			totalParkedCarsAmount.setText(String.valueOf(totalC));
			parkedCarsAmount.setText(String.valueOf(parkedC));
			parkedPassCarsAmount.setText(String.valueOf(parkedPC));
			parkedReservedCarsAmount.setText(String.valueOf(parkedRC));
			moneyExpectedAmount.setText(String.valueOf(expectedCash));
			moneyTotalAmount.setText(String.valueOf(totalCash));
		}
	}

	//Class voor het laten zien van verschillende grafieken
	private class StatsGraphView extends JPanel {
		private static final long serialVersionUID = 1L;
		private BarGraphView barGraphView;
		private LineGraphView lineGraphView;
		private JPanel cards;

		private JLabel checkBoxText;
		private final static String circlePanelT = "Cirkel Diagram";
		private final static String linePanelT = "Lijn Diagram";
		private final static String barPanelT = "Staaf Diagram";
		private PieView pieview;
		private Model model;
		private JPanel pieChart;
		private JLabel legendaTextN;
		private JLabel legendaTextA;
		private JLabel legendaTextG;

		public StatsGraphView() {
			//Init borderlayout
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Initializeer JPanel
			cards = new JPanel(new CardLayout());
			JPanel circlePanel = new JPanel();
			JPanel linePanel = new JPanel();
			JPanel barPanel = new JPanel();
			
			//Maak nieuwe combobox aan
			String comboBoxItems[] = { circlePanelT, linePanelT, barPanelT };
	        JComboBox<String> comboBox = new JComboBox<String>(comboBoxItems);
	        comboBox.setEditable(false);
	        comboBox.addItemListener(new cardEvents());
	        
			//Maak Jlabel aan
	        checkBoxText = new JLabel("Kies soort diagram:");
      
	        //Create graphs
			barGraphView = new BarGraphView();
	        lineGraphView = new LineGraphView();
	        
			//Voeg objecten toe aan de cards
			linePanel.add(lineGraphView);
			barPanel.add(barGraphView);
	        
			//Zet de layouts van de panels
			circlePanel.setLayout(new BoxLayout(circlePanel, BoxLayout.PAGE_AXIS));
			
			//maak een pie chart aan.
			pieChart = new JPanel();
			pieChart.setLayout(null);
			pieChart.setSize(200,200);
			model = new Model(); 
			model.setAantal(0);
			pieview = new PieView(model);
			pieview.setBounds(0, 0, 250, 200);
			pieview.setBackground(Color.BLACK);
			pieChart.add(pieview);
			pieChart.setBackground(new Color(238,238,238));
			pieChart.setVisible(true);
			
			//Maak pie chart legenda aan.
			legendaTextN = new JLabel("Normale autos");
			legendaTextN.setLayout(null);
			legendaTextN.setBounds(250,50, 100, 50);
			pieChart.add(legendaTextN);
			
			legendaTextA = new JLabel("Abbonementen");
			legendaTextA.setLayout(null);
			legendaTextA.setBounds(250,50, 100, 80);
			pieChart.add(legendaTextA);
			
			legendaTextG = new JLabel("Reserveringen");
			legendaTextG.setLayout(null);
			legendaTextG.setBounds(250,50, 100, 110);
			pieChart.add(legendaTextG);
			
			//Voeg de cards toe aan cards
			cards.add(circlePanel, circlePanelT);
	        cards.add(linePanel, linePanelT);
	        cards.add(barPanel, barPanelT);
	        
	        //Voeg objecten toe aan de cards
	        circlePanel.add(pieChart);
	        
	        //Zet alles in het midden
	        checkBoxText.setAlignmentX(Component.CENTER_ALIGNMENT);
	        comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	        cards.setAlignmentX(Component.CENTER_ALIGNMENT);
	        pieChart.setAlignmentX(Component.CENTER_ALIGNMENT);
	        
	        //Combobox width aanpassen
	        comboBox.setMaximumSize(new Dimension(200, comboBox.getPreferredSize().height));
	        
	        //Voeg de objects toe aan deze JPanel
			this.add(checkBoxText);
			this.add(comboBox);
			this.add(cards);
		}
		
		//Laat actuele stats zien
		public void updateStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
			if(this.isVisible()) {
				pieview.giveStats(totalC, parkedC, parkedPC, parkedRC);
				lineGraphView.updateStats(totalC, parkedC, parkedPC, parkedRC);
				barGraphView.updateStats(totalC, parkedC, parkedPC, parkedRC);
			}
		}
		
		public void updateHour(int h) {
			if(this.isVisible()) {
				lineGraphView.updateHour(h);
			}
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
