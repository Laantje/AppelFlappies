package Controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import Car.*;
import Location.Location;
import Queue.CarQueue;
import View.SimulatorView;

public class Simulator {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RESERVE = "3";
	private static final String RESERVATION = "4";
	
	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue reservationQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0; //Maandag = 0; Dinsdag = 1; Woensdag = 2; enzovoort...
    private int hour = 0;
    private int minute = 0;
    
    private int totalParkedCars = 0;
    private int parkedCars = 0;
    private int parkedPassCars = 0;
    private int parkedReservedCars = 0;

    private int tickPause = 100;
    private int skipTicks;
    
    private int moneyTotal = 0;
    private int moneyExpected = 0;
    
    private boolean isPaused;
    private boolean isSkipped;
    
    int weekDayArrivals = 100; // average number of arriving cars per hour
    int weekendArrivals = weekDayArrivals * 2; // average number of arriving cars per hour
    int weekDayPassArrivals = 50; // average number of arriving cars per hour
    int weekendPassArrivals = weekDayPassArrivals / 2; // average number of arriving cars per hour
    int weekDayReserves = 20; // average number of reserves per hour
    int weekendReserves = weekDayReserves * 2; // average number of reserves per hour
    int weekDayReservesArrivals = weekDayReserves; // average number of arriving reserves per hour
    int weekendReservesArrivals = weekendReserves; // average number of arriving reserves per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    int reserveSpeed = 10; // number of reservations that can be made per minute
    
    int enterSpeedStart = enterSpeed; // start value of number of cars that can enter per minute
    int paymentSpeedStart = paymentSpeed; // start value of number of cars that can pay per minute
    int exitSpeedStart = exitSpeed; // start value of number of cars that can leave per minute
    int reserveSpeedStart = reserveSpeed; // start value of number of reservations that can be made per minute
    
    int weekDayArrivalsStart = weekDayArrivals; // start value of average number of arriving cars per hour
    int weekDayPassArrivalsStart = weekDayPassArrivals; // start value of average number of arriving cars per hour
    int weekDayReservesStart = weekDayReserves; // start value of average number of reserves per hour
    int weekDayReservesArrivalsStart = weekDayReservesArrivals * 2; // start value of average number of reserves per hour

    public Simulator() {
    	isPaused = false;
    	isSkipped = false;
    	
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        reservationQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 5, 34);
        music();
    }
  
  public void run() {
      startValue();
    	//Do-while loop voor het blijven 'tick'en
    	do {
    		//Tick het programma; Dit houdt het draaiende
    		tick();
    		
    		//Kijk of er 100 ticks geskipt moeten worden
            if(isSkipped && skipTicks < 100) {
            	skipTicks++;
            }
            else if(skipTicks == 100) {
            	isSkipped = false;
            	tickPause = 100;
            }
    	}
    	while(simulatorView.isVisible());
    	
    	//Als de loop stopt, sluit het programma
    	System.exit(0);
    }

    private void tick() {
    	//Check status van simulator
    	checkStatus();
    	
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	
    	//Kijk of er gepauzeerd is
    	if(!isPaused || isPaused && isSkipped) {
    		advanceTime();
        	handleExit();
        	updateViews();
        	handleEntrance();
        	//Tel de geparkeerde auto's bij elkaar op tot een totaal geheel
        	totalParkedCars = parkedCars + parkedPassCars + parkedReservedCars;
        	//Geef stats door aan SimulatorView
        	sendStats();
    	}
    }
    


    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
            
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }
        simulatorView.giveTime(minute, hour, day); //Verstuur de tijd naar carParkView
    }
    
    private void sendStats() {
    	//Tel de geparkeerde auto's bij elkaar op tot een totaal geheel
    	totalParkedCars = parkedCars + parkedPassCars + parkedReservedCars;
    	//Geef stats door aan SimulatorView
    	simulatorView.giveStats(totalParkedCars, parkedCars, parkedPassCars, parkedReservedCars, moneyTotal, moneyExpected); 
    	simulatorView.giveQueues(entranceCarQueue.carsInQueue(), entrancePassQueue.carsInQueue(), reservationQueue.carsInQueue(), paymentCarQueue.carsInQueue(), exitCarQueue.carsInQueue());
    }
    
    public void startValue() {
    	simulatorView.giveStartValues(enterSpeedStart, paymentSpeedStart, exitSpeedStart, reserveSpeedStart, weekDayArrivalsStart, weekDayPassArrivalsStart, weekDayReservesStart, weekDayReservesArrivalsStart);
    }
    
    //Kijk of de main window nog open staat, zoja terminate program
    private void checkWindow() {
    	if(!simulatorView.isVisible()) {
    		System.exit(0);
    	}
    }
    
    public void updateEnterSpeed(int enterSpeed) {
    	this.enterSpeed = enterSpeed;
    }
    
    public void updatePaymentSpeed(int paymentSpeed) {
    	this.paymentSpeed = paymentSpeed;
    }
    
    public void updateExitSpeed(int exitSpeed) {
    	this.exitSpeed = exitSpeed;
    }
    
    public void updateReserveSpeed(int reserveSpeed) {
    	this.reserveSpeed = reserveSpeed;
    }
  
    public void updateWeekDayArrivals(int weekDayArrivals) {
    	this.weekDayArrivals = weekDayArrivals;
    }
    
    public void updateWeekDayPassArrivals(int weekDayPassArrivals) {
    	this.weekDayPassArrivals = weekDayPassArrivals;
    }
   
    public void updateWeekDayReserves(int weekDayReserves) {
    	this.weekDayReserves = weekDayReserves;
    }
    	     
    public void updateWeekDayReservesArrivals(int weekDayReservesArrivals) {
    	this.weekDayReservesArrivals = weekDayReservesArrivals;
    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    	carsEntering(reservationQueue);
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
        carsImpatient();
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }

	public void music(){		
		try {
			URL defaultMusic = getClass().getResource("/Resources/audio/background.wav");
		     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultMusic);
		     Clip clip = AudioSystem.getClip();
		     clip.open(audioInputStream);
		     clip.start( );
	      } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, e);
		        System.out.print("file not found");
		  } 
	}
  
    //Kijk wat de status is van de simulator
    private void checkStatus() {    	
    	//Check of de window nog open is
    	checkWindow();
    	
    	//Check of er gepauzeerd is
    	isPaused = simulatorView.checkPausedStatus();
        
        //Check of er geskipt is
    	if(simulatorView.checkSkipStatus()) {
    		isSkipped = true;
    		skipTicks = 0;
    		tickPause = 0;
    	}
    	
    	//Check of er iets met de admin tools is gebeurd
    	if(simulatorView.getUpdateStatus() == true) {
			giveNewValues();			
		}
    }
    
    private void carsArriving(){
    	//Maak tijdelijke variabelen aan zodat die aangepast kunnen 
    	//worden in de spitsuur voor efficientie
    	int tempWeekDayArrivals = weekDayArrivals;
    	int tempWeekDayPassArrivals = weekDayPassArrivals;
    	int tempWeekDayReserves = weekDayReserves;
    	int tempWeekendArrivals = weekendArrivals;
    	int tempWeekendPassArrivals = weekendPassArrivals;
    	int tempWeekendReserves = weekendReserves;
    	
    	//'s Nachts komen er minder mensen
    	if(hour >= 0 && hour < 6) {
    		tempWeekDayArrivals = weekDayArrivals / 5;
    		tempWeekDayPassArrivals = weekDayPassArrivals / 3;
    		tempWeekDayReserves = weekDayReserves / 3;
    		tempWeekendArrivals = weekDayArrivals / 5;
    		tempWeekendPassArrivals = weekDayPassArrivals / 3;
    		tempWeekendReserves = weekDayReserves / 3;
    	}
    	//Tussen 6 & 7 en 22 & 24 komen er iets meer mensen
    	else if(hour == 6 || day < 4 && hour >= 21 && hour < 24 || hour >= 22 && hour < 24) {
    		tempWeekDayArrivals = weekDayArrivals / 4;
    		tempWeekDayPassArrivals = weekDayPassArrivals / 3;
    		tempWeekDayReserves = weekDayReserves / 3;
    		tempWeekendArrivals = weekendArrivals / 4;
    		tempWeekendPassArrivals = weekendPassArrivals / 3;
    		tempWeekendReserves = weekendReserves / 3;
    	}
    	//Overdag komt de normale aantal
    	else {
    		tempWeekDayArrivals = weekDayArrivals;
        	tempWeekDayPassArrivals = weekDayPassArrivals;
        	tempWeekDayReserves = weekDayReserves;
        	tempWeekendArrivals = weekendArrivals;
        	tempWeekendPassArrivals = weekendPassArrivals;
        	tempWeekendReserves = weekendReserves;
    	}
    	
    	//Kijk naar de tijd en dag om spitsuren te bepalen
    	if(day < 5) 
    	{
    		if(hour > 7 && hour < 9) //Spitsuren inrijden ochtend
    		{
    			tempWeekDayArrivals += weekDayArrivals; //Verhogen met 100%
    			tempWeekDayPassArrivals += (weekDayPassArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekDayReserves -= (weekDayReserves / 100) * 50; //Verlagen met 50%;
    		}
    		if(day == 3 && hour > 17 && hour < 19) //Donderdag koopavond
    		{
    			tempWeekDayArrivals += (weekDayArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekDayPassArrivals -= (weekDayPassArrivals / 100) * 50; //Verlagen met 50%
    			tempWeekDayReserves += (weekDayReserves / 100) * 50; //Verhogen met 50%
    		}
    		else if(day == 4 && hour > 19 && hour < 22) //Vrijdagavond Theater
    		{
    			tempWeekDayArrivals += (weekDayArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekDayPassArrivals -= (weekDayPassArrivals / 100) * 50; //Verlagen met 50%
    			tempWeekDayReserves += (weekDayReserves / 100) * 50; //Verhogen met 50%
    		}
    	}
    	else 
    	{
    		if(day == 5 && hour > 19 && hour < 22) //Zaterdag avond theater
    		{
    			tempWeekendArrivals += (weekendArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekendPassArrivals -= (weekendPassArrivals / 100) * 50; //Verlagen met 50%
    			tempWeekendReserves += (weekDayReserves / 100) * 50; //Verhogen met 50%
    		}
    		else if(day == 6 && hour > 12 && hour < 14) //Zondag middag theater
    		{
    			tempWeekendArrivals += (weekendArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekendPassArrivals -= (weekendPassArrivals / 100) * 50; //Verlagen met 50%
    			tempWeekendReserves += (weekDayReserves / 100) * 50; //Verhogen met 50%
    		}
    	}
    	
    	//Maak de arrivals een deel van de reservations
    	weekDayReservesArrivals = tempWeekDayReserves;
    	weekendReservesArrivals = tempWeekendReserves;
    	
    	//Reken uit hoeveel auto's er moeten komen 
    	int numberOfCars=getNumberOfCars(tempWeekDayArrivals, tempWeekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(tempWeekDayPassArrivals, tempWeekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayReservesArrivals, weekendReservesArrivals);
        addArrivingCars(numberOfCars, RESERVE);
        numberOfCars=getNumberOfCars(tempWeekDayReserves, tempWeekendReserves);
        addArrivingCars(numberOfCars, RESERVATION);
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        // Look at first car in line
        Car car = queue.checkCar();
        //if reservation :
        if (car instanceof ReserveSpot) {
        	while (queue.carsInQueue()>0 && 
        			simulatorView.getNumberOfOpenSpots()>0 &&
        			i<reserveSpeed) {
                car = queue.removeCar();
                Location freeLocation;
                freeLocation = simulatorView.getFirstFreeLocation();
                simulatorView.setCarAt(freeLocation, car);
                moneyTotal += 3;
                i++;
        	}
        }
        //if any car:
        else {        	
        	while (queue.carsInQueue()>0 && 
        			(simulatorView.getNumberOfOpenSpots()>0 || 
        			simulatorView.getNumberOfPaidOpenSpots()>0 || 
        			simulatorView.getNumberOfReserveOpenSpots()>0) &&
        			i<enterSpeed) {
        		car = queue.checkCar();
        		Location freeLocation = null;
        		Boolean spot = false; //Is there a spot available?
        		if((car instanceof AdHocCar) && simulatorView.getNumberOfOpenSpots()>0) {
        			freeLocation = simulatorView.getFirstFreeLocation();
        			spot = true;
        			moneyExpected += 5;
        			parkedCars++;
        		}
        		else if (car instanceof ParkingPassCar && simulatorView.getNumberOfPaidOpenSpots()>0)
        		{
        			freeLocation = simulatorView.getFirstPaidFreeLocation();
        			spot = true;
        			parkedPassCars++;
        		}
        		else if (car instanceof ReserveCar && simulatorView.getNumberOfReserveOpenSpots()>0)
        		{
        			freeLocation = simulatorView.getFirstReserveFreeLocation();
        			simulatorView.removeCarAt(freeLocation);
        			spot = true;
        			moneyExpected += 3;
        			parkedReservedCars++;
        		}
        		//Als er een gereserveerde auto in de rij staat en er geen gereserveerde plek is
        		else if (car instanceof ReserveCar && simulatorView.getNumberOfReserveOpenSpots() == 0)
        		{
        			car = queue.removeCar();
        			entranceCarQueue.addCar(new AdHocCar(false));
        		}
        		if (spot == true) {
        			car = queue.removeCar();
        			simulatorView.setCarAt(freeLocation, car);
        			i++;
        		}
        		else
        		{
        			i = enterSpeed; // if no spot is available, end loop
        		}
        	}
        }
        
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
        	}
        	else {
        		carLeavesSpot(car);
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            if(car instanceof AdHocCar) {
            	moneyTotal += 5;
            	moneyExpected -= 5;
            }else if (car instanceof ReserveCar) {
            	moneyTotal += 3;
            	moneyExpected -= 3;
            }
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	//Kijk hoelaat het is
            	if(hour < 20 && hour > 6) {
            		entranceCarQueue.addCar(new AdHocCar(false));
            	}
            	else {
            		entranceCarQueue.addCar(new AdHocCar(true));
            	}
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	//Kijk hoelaat het is
            	if(hour < 20 && hour > 6) {
            		entrancePassQueue.addCar(new ParkingPassCar(false));
            	}
            	else {
            		entrancePassQueue.addCar(new ParkingPassCar(true));
            	}
            }
            break;
    	case RESERVE:
            for (int i = 0; i < numberOfCars; i++) {
            	//if(simulatorView.getNumberOfReserveOpenSpots() > 0) {
            		entrancePassQueue.addCar(new ReserveCar());
            	//}
            }
            break;
    	case RESERVATION:
            for (int i = 0; i < numberOfCars; i++) {
            	reservationQueue.addCar(new ReserveSpot());
            }
            break;
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
        //Kijk wat voor klant het is; pas de stats aan
        if(car instanceof AdHocCar) {
        	parkedCars--;
        }
        else if(car instanceof ReserveCar) {
        	parkedReservedCars--;
        }
        else if(car instanceof ParkingPassCar)
        {
        	parkedPassCars--;
        }
    }
    
    private void carsImpatient() {
    	entranceCarQueue.removeImpatient();
    	entrancePassQueue.removeImpatient();
    }
    
    public int getMinute() {
    	return minute;
    }
    
    public int getHour() {
    	return hour;
    }
    
    public int getDay() {
    	return day;
    }
    
    //Huidige waardes updaten met ingevoerde gegevens uit admin tools
    private void giveNewValues() {
    	HashMap<String, Integer>  map = simulatorView.getUpdateValues();
    	updateEnterSpeed(map.get("enterSpeed"));
    	updatePaymentSpeed(map.get("paymentSpeed"));
    	updateExitSpeed(map.get("exitSpeed"));
    	updateReserveSpeed(map.get("reserveSpeed"));
    	
    	updateWeekDayArrivals(map.get("weekDayArrivals"));
    	updateWeekDayPassArrivals(map.get("weekDayPassArrivals"));
    	updateWeekDayReserves(map.get("weekDayReserves"));
    	updateWeekDayReservesArrivals(map.get("weekDayReservesArrivals"));	
	}
}