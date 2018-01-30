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
		this.setPreferredSize(new Dimension(500, 250));
		
		//Maak JPanels aan
		adminView = new AdminView();

		//Voeg JPanels toe aan contentpane
	    Container contentPane = getContentPane();
	    contentPane.add(adminView, BorderLayout.NORTH);
	    pack();
	}
	
	public HashMap<String, Integer> getUpdateValues() {
		return adminView.getUpdateValues();
	}
	
	public boolean getUpdateStatus() {
		return adminView.getUpdateStatus();
	}
	
	private class AdminView extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JLabel titel;
		
		private JLabel enterSpeed;
		private JTextField enterSpeedField;
		private JLabel paymentSpeed;
		private JTextField paymentSpeedField;
	    private JLabel exitSpeed;
	    private JTextField exitSpeedField;
	    
	    private JButton submitButton;
	    private JButton resetButton;

		protected boolean updateNeeded = false;
		HashMap<String, Integer>  map = new HashMap<>();

		public AdminView() {
			//Maak een gridlayout van deze JPanel
			this.setLayout(new GridLayout(0, 2));
			
			//Maak border
			Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	        Border blackLine = BorderFactory.createLineBorder(Color.black);
	        CompoundBorder line = new CompoundBorder(empty, blackLine);
	        Border grid1Border = BorderFactory.createTitledBorder(line, "Admin Tools");
	        
	        this.setBorder(grid1Border);
	        
			//Maak JLabel aan
			titel = new JLabel("0");
			
	        enterSpeed = new JLabel("0");
	        enterSpeedField = new JTextField("0");
	        paymentSpeed = new JLabel("0");
	        paymentSpeedField = new JTextField("0");
	        exitSpeed = new JLabel("0");
	        exitSpeedField = new JTextField("0");
	        
	        submitButton = new JButton("Submit");
	        resetButton = new JButton("Reset");
	           
	        //Maak de font groter
	        titel.setFont(new Font("", Font.PLAIN, 30));

	        enterSpeed.setFont(new Font("", Font.PLAIN, 18));
	        enterSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        paymentSpeed.setFont(new Font("", Font.PLAIN, 18));
	        paymentSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        exitSpeed.setFont(new Font("", Font.PLAIN, 18));
	        exitSpeedField.setFont(new Font("", Font.PLAIN, 18));
	        
	        //Voeg JLabel/JTextField toe
	        this.add(enterSpeed);
	        this.add(enterSpeed);
	        this.add(enterSpeedField);
	        this.add(paymentSpeed);
	        this.add(paymentSpeedField);
	        this.add(exitSpeed);
	        this.add(exitSpeedField);   
	        
	        //Voeg JButtons toe
	        this.add(submitButton);
	        this.add(resetButton);
	        
	        //Geef labels inhoud
	        enterSpeed.setText("Enter Speed: ");
	        paymentSpeed.setText("Payment Speed: ");
	        exitSpeed.setText("Exit Speed: ");
	        
	        //Hashmap initialiseren
	        map.put("enterSpeed", 7);
	        map.put("paymentSpeed", 5);
			map.put("exitSpeed", 3);
	        
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
	                   System.out.println(map.get("exitSpeed"));
	               }
	           });
	        
		}
		
		public void updateValues() {
			//Zet string om in Int
			int enterSpeedValue = Integer.parseInt(enterSpeedField.getText());
			int paymentSpeedValue = Integer.parseInt(paymentSpeedField.getText());
			int exitSpeedValue = Integer.parseInt(exitSpeedField.getText());
			map.put("enterSpeed", enterSpeedValue);
			map.put("paymentSpeed", paymentSpeedValue);
			map.put("exitSpeed", exitSpeedValue);
		}
		
		public HashMap<String, Integer> getUpdateValues() {
			return map;
		}
		
		public boolean getUpdateStatus() {
			return updateNeeded;
		}
		
		public void resetStats() {
			//Zet string om in Int
			map.put("enterSpeed", 7);
			map.put("paymentSpeed", 5);
			map.put("exitSpeed", 3);
		}

	}
}
