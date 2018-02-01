package MVC;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AdminWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private AdminView adminView;
	
	public AdminWindow() {
		//Create Dimension
		this.setPreferredSize(new Dimension(650, 400));
		
		//Maak JPanels aan
		adminView = new AdminView();

		//Voeg JPanels toe aan contentpane
	    Container contentPane = getContentPane();
	    contentPane.add(adminView, BorderLayout.NORTH);
	    pack();
	}
	
	public void giveStartValues(int enterSpeedStart, int paymentSpeedStart, int exitSpeedStart, int reserveSpeedStart, int weekDayArrivalsStart, int weekDayPassArrivalsStart, int weekDayReservesStart, int weekDayReservesArrivalsStart) {
		adminView.startValues(enterSpeedStart, paymentSpeedStart, exitSpeedStart, reserveSpeedStart, weekDayArrivalsStart, weekDayPassArrivalsStart, weekDayReservesStart, weekDayReservesArrivalsStart);
	}	
	
	public HashMap<String, Integer> getUpdateValues() {
		return adminView.getUpdateValues();
	}
	
	public boolean getUpdateStatus() {
		return adminView.getUpdateStatus();
	}
	
	private class AdminView extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JLabel enterSpeed;
		private JTextField enterSpeedField;
		private JLabel paymentSpeed;
		private JTextField paymentSpeedField;
	    private JLabel exitSpeed;
	    private JTextField exitSpeedField;
	    private JLabel reserveSpeed;
	    private JTextField reserveSpeedField;
	    	    
	    private JLabel weekDayArrivals;
	    private JTextField weekDayArrivalsField;
	    private JLabel weekDayPassArrivals;
	    private JTextField weekDayPassArrivalsField;
	    private JLabel weekDayReserves;
	    private JTextField weekDayReservesField;
	    private JLabel weekDayReservesArrivals;
	    private JTextField weekDayReservesArrivalsField;
	    
	    private JButton submitButton;
	    private JButton resetButton;    
	    
        int enterSpeedStart;
		int paymentSpeedStart;
		int exitSpeedStart;
		int reserveSpeedStart;
		int weekDayArrivalsStart;
		int weekDayPassArrivalsStart;
		int weekDayReservesStart;
		int weekDayReservesArrivalsStart;

		protected boolean updateNeeded = false;
		HashMap<String, Integer>  map = new HashMap<>();

		public AdminView() {
			//Maak een gridlayout van deze JPanel
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Maak meerdere Jpanels
	        JPanel buttons = new JPanel();
	        buttons.setLayout(new FlowLayout());
	        
	        JPanel speed = new JPanel();
	        speed.setLayout(new GridLayout(0,2));
	        
	        JPanel time = new JPanel();
	        time.setLayout(new GridLayout(0,2));
			
			//Maak border
			Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	        Border blackLine = BorderFactory.createLineBorder(Color.black);
	        CompoundBorder line = new CompoundBorder(empty, blackLine);
	        Border grid1Border = BorderFactory.createTitledBorder(line, "Admin Tools");
	        Border speedBorder = BorderFactory.createTitledBorder(line, "Snelheid aanpassen");
	        Border timeBorder = BorderFactory.createTitledBorder(line, "Aantal auto's aanpassen");
	        
	        this.setBorder(grid1Border); 
	        speed.setBorder(speedBorder);
	        time.setBorder(timeBorder);
	        
			//Maak JLabel aan
	        enterSpeed = new JLabel("0");
	        enterSpeedField = new JTextField("0");
	        paymentSpeed = new JLabel("0");
	        paymentSpeedField = new JTextField("0");
	        exitSpeed = new JLabel("0");
	        exitSpeedField = new JTextField("0");
	        reserveSpeed = new JLabel("0");
	        reserveSpeedField = new JTextField("0");
	        	        
	        weekDayArrivals = new JLabel("0");
	        weekDayArrivalsField = new JTextField("0");
	        weekDayPassArrivals = new JLabel("0");
	        weekDayPassArrivalsField = new JTextField("0");
	        weekDayReserves = new JLabel("0");
	        weekDayReservesField = new JTextField("0");
	        weekDayReservesArrivals = new JLabel("0");
	        weekDayReservesArrivalsField = new JTextField("0");
	        
	        submitButton = new JButton("Submit");
	        resetButton = new JButton("Reset");
	           
	        //Maak de font groter

	        enterSpeed.setFont(new Font("", Font.PLAIN, 18));
	        enterSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        paymentSpeed.setFont(new Font("", Font.PLAIN, 18));
	        paymentSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        exitSpeed.setFont(new Font("", Font.PLAIN, 18));
	        exitSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        reserveSpeed.setFont(new Font("", Font.PLAIN, 18));
	        reserveSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        	        
	        weekDayArrivals.setFont(new Font("", Font.PLAIN, 18));
	        weekDayArrivalsField.setFont(new Font("", Font.PLAIN, 18));
	        weekDayPassArrivals.setFont(new Font("", Font.PLAIN, 18));
	        weekDayPassArrivalsField.setFont(new Font("", Font.PLAIN, 18));
	        weekDayReserves.setFont(new Font("", Font.PLAIN, 18));
	        weekDayReservesField.setFont(new Font("", Font.PLAIN, 18));
	        weekDayReservesArrivals.setFont(new Font("", Font.PLAIN, 18));
	        weekDayReservesArrivalsField.setFont(new Font("", Font.PLAIN, 18));
	        
	        //Voeg JLabel/JTextField toe
	        speed.add(enterSpeed);
	        speed.add(enterSpeedField);
	        speed.add(paymentSpeed);
	        speed.add(paymentSpeedField);
	        speed.add(exitSpeed);
	        speed.add(exitSpeedField);
	        speed.add(reserveSpeed);
	        speed.add(reserveSpeedField);
	       	        
	        time.add(weekDayArrivals);
	        time.add(weekDayArrivalsField);
	        time.add(weekDayPassArrivals);
	        time.add(weekDayPassArrivalsField);
	        time.add(weekDayReserves);
	        time.add(weekDayReservesField);
	        time.add(weekDayReservesArrivals);
	        time.add(weekDayReservesArrivalsField);
	        
	        //Voeg JButtons toe
	        buttons.add(submitButton);
	        buttons.add(resetButton);
	        
	        //Toon Jpanels
	        add(speed);
	        add(time);
	        add(buttons);
	        
	        //Geef labels inhoud
	        enterSpeed.setText("Inrij snelheid: ");
	        paymentSpeed.setText("Betaal snelheid: ");
	        exitSpeed.setText("Uitrij snelheid: ");
	        reserveSpeed.setText("Reservatie snelheid: ");
	        	        
	        weekDayArrivals.setText("Normale auto's aankomst: ");
	        weekDayPassArrivals.setText("Geabonneerde auto's aankomst: ");
	        weekDayReserves.setText("Reservaties: ");
	        weekDayReservesArrivals.setText("Gereserveerde auto's aankomst: ");
	        
	        //Geef de submit button een event
	           submitButton.addActionListener( new ActionListener()
	           {
	               public void actionPerformed(ActionEvent e)
	               {
	                   updateValues();
	                   updateNeeded = true;
	               }
	           });
	           
	           resetButton.addActionListener( new ActionListener()
	           {
	               public void actionPerformed(ActionEvent e)
	               {
	                   resetStats();
	                   
	               }
	           });
	           
	           
		}
		
		public void updateValues() {
			//Zet string om in Int
			int enterSpeedValue = Integer.parseInt(enterSpeedField.getText());
			int paymentSpeedValue = Integer.parseInt(paymentSpeedField.getText());
			int exitSpeedValue = Integer.parseInt(exitSpeedField.getText());
			int reserveSpeedValue = Integer.parseInt(exitSpeedField.getText());	
			int weekDayArrivalsValue = Integer.parseInt(weekDayArrivalsField.getText());
			int weekDayPassArrivalsValue = Integer.parseInt(weekDayPassArrivalsField.getText());
			int weekDayReservesValue = Integer.parseInt(weekDayReservesField.getText());
			int weekDayReservesArrivalsValue = Integer.parseInt(weekDayReservesArrivalsField.getText());
			
			map.put("enterSpeed", enterSpeedValue);
			map.put("paymentSpeed", paymentSpeedValue);
			map.put("exitSpeed", exitSpeedValue);
			map.put("reserveSpeed", reserveSpeedValue);
			map.put("weekDayArrivals", weekDayArrivalsValue);
			map.put("weekDayPassArrivals", weekDayPassArrivalsValue);
			map.put("weekDayReserves", weekDayReservesValue);
			map.put("weekDayReservesArrivals", weekDayReservesArrivalsValue);
		}
		
		public void startValues(int enterSpeedStart, int paymentSpeedStart, int exitSpeedStart, int reserveSpeedStart, int weekDayArrivalsStart, int weekDayPassArrivalsStart, int weekDayReservesStart, int weekDayReservesArrivalsStart) {
  			this.enterSpeedStart = enterSpeedStart;
  			this.paymentSpeedStart = paymentSpeedStart;
  			this.exitSpeedStart = exitSpeedStart;
 			this.reserveSpeedStart = reserveSpeedStart;
 			this.weekDayArrivalsStart = weekDayArrivalsStart;
 			this.weekDayPassArrivalsStart = weekDayPassArrivalsStart;
 			this.weekDayReservesStart = weekDayReservesStart;
 			this.weekDayReservesArrivalsStart = weekDayReservesArrivalsStart;
  			resetStats();
  		}
		
		public HashMap<String, Integer> getUpdateValues() {
			return map;
		}
		
		public boolean getUpdateStatus() {
			return updateNeeded;
		}
		
		public void resetStats() {			
			map.put("enterSpeed", enterSpeedStart);
			map.put("paymentSpeed", paymentSpeedStart);
			map.put("exitSpeed", exitSpeedStart);
			map.put("reserveSpeed", reserveSpeedStart);
			map.put("weekDayArrivals", weekDayArrivalsStart);
			map.put("weekDayPassArrivals", weekDayPassArrivalsStart);
			map.put("weekDayReserves", weekDayReservesStart);
			map.put("weekDayReservesArrivals", weekDayReservesArrivalsStart);
			 
			enterSpeedField.setText(Integer.toString(enterSpeedStart));
			paymentSpeedField.setText(Integer.toString(paymentSpeedStart));
			exitSpeedField.setText(Integer.toString(exitSpeedStart));
			reserveSpeedField.setText(Integer.toString(reserveSpeedStart));
			
			weekDayArrivalsField.setText(Integer.toString(weekDayArrivalsStart));
			weekDayPassArrivalsField.setText(Integer.toString(weekDayPassArrivalsStart));
			weekDayReservesField.setText(Integer.toString(weekDayReservesStart));
			weekDayReservesArrivalsField.setText(Integer.toString(weekDayReservesArrivalsStart));
		}
	}
}
