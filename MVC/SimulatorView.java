package MVC;

import javax.swing.*;

import Car.AdHocCar;
import Car.Car;
import Car.ParkingPassCar;
import Car.ReserveCar;
import Controller.Location;
import Controller.ReserveSpot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorView extends JFrame {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfPaidOpenSpots;
    private int numberOfOpenSpots;
    private int numberOfReserveOpenSpots;
    private Car[][][] cars;

   public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
       this.numberOfFloors = numberOfFloors;
       this.numberOfRows = numberOfRows;
       this.numberOfPlaces = numberOfPlaces;
       this.numberOfOpenSpots =(numberOfFloors-1)*numberOfRows*numberOfPlaces;
       this.numberOfPaidOpenSpots =1*numberOfRows*numberOfPlaces;
       cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
       
       carParkView = new CarParkView();

       Container contentPane = getContentPane();
       contentPane.add(carParkView, BorderLayout.CENTER);
       pack();
       setVisible(true);

       updateView();
   }

   public void updateView() {
       carParkView.updateView();
   }
   
	public int getNumberOfFloors() {
       return numberOfFloors;
   }

   public int getNumberOfRows() {
       return numberOfRows;
   }

   public int getNumberOfPlaces() {
       return numberOfPlaces;
   }

   public int getNumberOfOpenSpots(){
   		return numberOfOpenSpots;
   }

   //Geef tijd door aan carParkView
   public void giveTime(int m, int h, int d) {
	   carParkView.updateTime(m,h,d); //Verstuur de tijd naar carParkView
   }
   
   //Geef stats door aan statswindow
   public void giveStats(int totalC, int parkedC, int parkedPC, int parkedRC) {
	   carParkView.statsWindow.giveStats(totalC, parkedC, parkedPC, parkedRC);
   }

   public int getNumberOfPaidOpenSpots(){
	   	return numberOfPaidOpenSpots;
   }
   
   public int getNumberOfReserveOpenSpots(){
	   	return numberOfReserveOpenSpots;
  }
   
   public Car getCarAt(Location location) {
       if (!locationIsValid(location)) {
           return null;
       }
       return cars[location.getFloor()][location.getRow()][location.getPlace()];
   }

   public boolean setCarAt(Location location, Car car) {
       if (!locationIsValid(location)) {
           return false;
       }
       Car oldCar = getCarAt(location);
       if (oldCar == null) {
           cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
           car.setLocation(location);
           if (car instanceof AdHocCar || car instanceof ReserveCar) {
        	   numberOfOpenSpots--;
           }
           else if(car instanceof ParkingPassCar)
           {
        	   numberOfPaidOpenSpots--;
           }
           else if(car instanceof ReserveSpot)
           {
        	   numberOfOpenSpots--;
        	   numberOfReserveOpenSpots++;
           }  
           return true;
       }
       return false;
   }

   public Car removeCarAt(Location location) {
       if (!locationIsValid(location)) {
           return null;
       }
       Car car = getCarAt(location);
       if (car == null) {
           return null;
       }
       cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
       car.setLocation(null);
       if (car instanceof AdHocCar || car instanceof ReserveCar) {
    	   numberOfOpenSpots++;
       }
       else if(car instanceof ParkingPassCar)
       {
    	   numberOfPaidOpenSpots++;
       }
       else if(car instanceof ReserveSpot)
       {
    	   numberOfReserveOpenSpots--;
    	   numberOfOpenSpots++;
       }    
       return car;
   }

   public Location getFirstFreeLocation() {
       for (int floor = 1; floor < getNumberOfFloors(); floor++) {
           for (int row = 0; row < getNumberOfRows(); row++) {
               for (int place = 0; place < getNumberOfPlaces(); place++) {
                   Location location = new Location(floor, row, place);
                   if (getCarAt(location) == null) {
                       return location;
                   }
               }
           }
       }
       return null;
   }
   
   public Location getFirstReserveFreeLocation() {
       for (int floor = 1; floor < getNumberOfFloors(); floor++) {
           for (int row = 0; row < getNumberOfRows(); row++) {
               for (int place = 0; place < getNumberOfPlaces(); place++) {
                   Location location = new Location(floor, row, place);
                   if (getCarAt(location) instanceof ReserveSpot) {
                       return location;
                   }
               }
           }
       }
       return null;
   }
   
   public Location getFirstPaidFreeLocation() {
       int floor = 0;
       for (int row = 0; row < getNumberOfRows(); row++) {
            for (int place = 0; place < getNumberOfPlaces(); place++) {
                Location location = new Location(floor, row, place);
                if (getCarAt(location) == null) {
                       return location;
               }
           }
       }
       return null;
   }

   public Car getFirstLeavingCar() {
       for (int floor = 0; floor < getNumberOfFloors(); floor++) {
           for (int row = 0; row < getNumberOfRows(); row++) {
               for (int place = 0; place < getNumberOfPlaces(); place++) {
                   Location location = new Location(floor, row, place);
                   Car car = getCarAt(location);
                   if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                       return car;
                   }
               }
           }
       }
       return null;
   }

   public void tick() {
       for (int floor = 0; floor < getNumberOfFloors(); floor++) {
           for (int row = 0; row < getNumberOfRows(); row++) {
               for (int place = 0; place < getNumberOfPlaces(); place++) {
                   Location location = new Location(floor, row, place);
                   Car car = getCarAt(location);
                   if (car != null) {
                       car.tick();
                   }
               }
           }
       }
   }

   private boolean locationIsValid(Location location) {
       int floor = location.getFloor();
       int row = location.getRow();
       int place = location.getPlace();
       if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
           return false;
       }
       return true;
   }
   
   private class CarParkView extends JPanel {
       private StatsWindow statsWindow;
       private Dimension size;
       private Image carParkImage;
       private JLabel clockDay;
       private JLabel clockTime;
       private JButton statsButton;
       private int minute;
       private int hour;
       private int day;
   
       /**
        * Constructor for objects of class CarPark
        */
       public CarParkView() {
    	   //Create Dimension
           size = new Dimension(0, 0); 
           
           //Maak een nieuwe stats windows aan
           statsWindow = new StatsWindow();
           
           //Maak JLabel voor tijd en dag aan
           clockDay = new JLabel("Maandag");
           clockTime = new JLabel(String.valueOf(hour) + ":" + String.valueOf(minute));
           
           //Maak de font groter
           clockDay.setFont(new Font("", Font.PLAIN, 30));
           clockTime.setFont(new Font("", Font.PLAIN, 30));
           
           //Voeg de stats button toe
           statsButton = new JButton("Statistieken");
           
           //Geef de stats button een event
           statsButton.addActionListener( new ActionListener()
           {
               public void actionPerformed(ActionEvent e)
               {
                   activateStatsWindow();
               }
           });
           
           //Voeg JLabel toe
           this.add(clockDay);
           this.add(clockTime);
           
           //Voeg JButton toe
           this.add(statsButton);
       }
   
       /**
        * Overridden. Tell the GUI manager how big we would like to be.
        */
       public Dimension getPreferredSize() {
           return new Dimension(800, 500);
       }
       
       //Maak een nieuwe windows aan met stats
       private void activateStatsWindow() {
    	   //Kijk of stats window al open is
    	   if(statsWindow.isVisible()) {
    		   statsWindow.setVisible(false);
    	   }
    	   else {
    		   statsWindow.setVisible(true);
    	   }
       }
   
       /**
        * Overriden. The car park view component needs to be redisplayed. Copy the
        * internal image to screen.
        */
       public void paintComponent(Graphics g) {
           if (carParkImage == null) {
               return;
           }
   
           Dimension currentSize = getSize();
           if (size.equals(currentSize)) {
               g.drawImage(carParkImage, 0, 0, null);
           }
           else {
               // Rescale the previous image.
               g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
           }
       }
       
       public void updateTime(int m, int h, int d) {
    	   //Zet de tijd variabelen
    	   minute = m;
    	   hour = h;
    	   day = d;
    	   
    	   //Check welke dag het is
    	   switch(day) {
    	   		case 0:
    	   			clockDay.setText("Maandag");
    	   			break;
    	   		case 1:
    	   			clockDay.setText("Dinsdag");
    	   			break;
    	   		case 2:
    	   			clockDay.setText("Woensdag");
    	   			break;
    	   		case 3:
    	   			clockDay.setText("Donderdag");
    	   			break;
    	   		case 4:
    	   			clockDay.setText("Vrijdag");
    	   			break;
    	   		case 5:
    	   			clockDay.setText("Zaterdag");
    	   			break;
    	   		case 6:
    	   			clockDay.setText("Zondag");
    	   			break;
    	   }
    	   
    	   //Zet de clock goed
    	   if(hour < 10) {
    		   if(minute < 10) {
    			   clockTime.setText("0" + String.valueOf(hour) + ":0" + String.valueOf(minute));
    		   }
    		   else {
    			   clockTime.setText("0" + String.valueOf(hour) + ":" + String.valueOf(minute));
    		   }
    	   }
    	   else {
    		   if(minute < 10) {
    			   clockTime.setText(String.valueOf(hour) + ":0" + String.valueOf(minute));
    		   }
    		   else {
    			   clockTime.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
    		   }
    	   }
       }
   
       public void updateView() {
           // Create a new car park image if the size has changed.
           if (!size.equals(getSize())) {
               size = getSize();
               carParkImage = createImage(size.width, size.height);
           }
           Graphics graphics = carParkImage.getGraphics();
           for(int floor = 0; floor < getNumberOfFloors(); floor++) {
               for(int row = 0; row < getNumberOfRows(); row++) {
               	//Maak een temporary int aan voor de plaatsen
               	int tempNumberOfPlaces;
               	//Kijk of 'floor' hoger is als 0. Zoja, maak een plaats minder.
               	//Dit is om te verkomen dat er meer dan 500 plaatsen worden gemaakt.
               	if(floor > 0) {
               		tempNumberOfPlaces = getNumberOfPlaces() - 1;
               	}
               	else {
               		tempNumberOfPlaces = getNumberOfPlaces();
               	}
               	for(int place = 0; place < tempNumberOfPlaces; place++) {
               		Color color = null;
               		Location location = new Location(floor, row, place);
                       Car car = getCarAt(location);
                       if(car == null) {
                       		switch (location.getType()) {
                               case 0:  color = new Color(196, 213, 239);
                                        break;
                               case 1:  color = new Color(255, 216, 214);
                                        break;
                       		}
                       }
                       else {
                       	color = car.getColor();
                       }
                       
                       drawPlace(graphics, location, color);
               	}
           }
        }
           repaint();
       }
   
       /**
        * Paint a place on this car park view in a given color.
        */
       private void drawPlace(Graphics graphics, Location location, Color color) {
           graphics.setColor(color);
           graphics.fillRect(
                   location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                   60 + location.getPlace() * 10,
                   20 - 1,
                   10 - 1); // TODO use dynamic size or constants
       }
   }
}