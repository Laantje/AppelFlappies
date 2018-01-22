package ParkeerSimulator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsWindow extends JFrame {
	private StatsView statsView;
	private int totalParkedCars;
	
	public StatsWindow() {
		statsView = new StatsView();

	    Container contentPane = getContentPane();
	    contentPane.add(statsView, BorderLayout.CENTER);
	    pack();
	}
	
	//Geef stats door aan statsView
	public void giveStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
		   statsView.updateStats(totalC, parkedC, parkedPC, parkedRC);
	}
	
	private class StatsView extends JPanel {
		private Dimension size;
		private JLabel totalParkedCarsT;
		private JLabel parkedCarsT;
	    private JLabel parkedPassCarsT;
	    private JLabel parkedReservedCarsT;
	   
		public StatsView() {
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
}
