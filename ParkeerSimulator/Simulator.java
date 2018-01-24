package ParkeerSimulator;

import java.util.Random;

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

    private int tickPause = 100;

    int weekDayArrivals = 200; // average number of arriving cars per hour
    int weekendArrivals = weekDayArrivals * 2; // average number of arriving cars per hour
    int weekDayPassArrivals = 50; // average number of arriving cars per hour
    int weekendPassArrivals = weekDayPassArrivals / 10; // average number of arriving cars per hour
    int weekDayReserves = 100; // average number of reserves per hour
    int weekendReserves = 50; // average number of reserves per hour
    int weekDayReservesArrivals = 30; // average number of arriving reserves per hour
    int weekendReservesArrivals = 50; // average number of arriving reserves per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    int reserveSpeed = 10; // number of reservations that can be made per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        reservationQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 5, 34);
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
        }
    }

    private void tick() {
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
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
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }
    
    private void carsArriving(){
    	//Maak tijdelijke variabelen aan zodat die aangepast kunnen 
    	//worden in de spitsuur voor efficientie
    	int tempWeekDayArrivals = weekDayArrivals;
    	int tempWeekDayPassArrivals = weekDayPassArrivals;
    	int tempWeekendArrivals = weekendArrivals;
    	int tempWeekendPassArrivals = weekendPassArrivals;
    	
    	//Kijk naar de tijd en dag om spitsuren te bepalen
    	if(day < 5) 
    	{
    		if(hour > 7 && hour < 9) //Spitsuren inrijden ochtend
    		{
    			tempWeekDayArrivals += weekDayArrivals; //Verhogen met 100%
    			tempWeekDayPassArrivals += (weekDayPassArrivals / 100) * 150; //Verhogen met 150%
    		}
    		if(day == 3 && hour > 17 && hour < 19) //Donderdag koopavond
    		{
    			tempWeekDayArrivals += (weekDayArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekDayPassArrivals -= (weekDayPassArrivals / 100) * 50; //Verlagen met 50%
    		}
    		else if(day == 4 && hour > 19 && hour < 22) //Vrijdagavond Theater
    		{
    			tempWeekDayArrivals += (weekDayArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekDayPassArrivals -= (weekDayPassArrivals / 100) * 50; //Verlagen met 50%
    		}
    	}
    	else 
    	{
    		if(day == 5 && hour > 19 && hour < 22) //Zaterdag avond theater
    		{
    			tempWeekendArrivals += (weekendArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekendPassArrivals -= (weekendPassArrivals / 100) * 50; //Verlagen met 50%
    		}
    		else if(day == 6 && hour > 12 && hour < 14) //Zondag middag theater
    		{
    			tempWeekendArrivals += (weekendArrivals / 100) * 150; //Verhogen met 150%
    			tempWeekendPassArrivals -= (weekendPassArrivals / 100) * 50; //Verlagen met 50%
    		}
    	}
    	
    	//Reken uit hoeveel auto's er moeten komen 
    	int numberOfCars=getNumberOfCars(tempWeekDayArrivals, tempWeekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(tempWeekDayPassArrivals, tempWeekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayReservesArrivals, weekendReservesArrivals);
        addArrivingCars(numberOfCars, RESERVE);
        numberOfCars=getNumberOfCars(weekDayReserves, weekendReserves);
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
        		}
        		else if (car instanceof ParkingPassCar && simulatorView.getNumberOfPaidOpenSpots()>0)
        		{
        			freeLocation = simulatorView.getFirstPaidFreeLocation();
        			spot = true;
        		}
        		else if (car instanceof ReserveCar && simulatorView.getNumberOfReserveOpenSpots()>0)
        		{
        			freeLocation = simulatorView.getFirstReserveFreeLocation();
        			simulatorView.removeCarAt(freeLocation);
        			spot = true;
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
            // TODO Handle payment.
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
            	entranceCarQueue.addCar(new AdHocCar());
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            }
            break;
    	case RESERVE:
            for (int i = 0; i < numberOfCars; i++) {
            	if(simulatorView.getNumberOfReserveOpenSpots() > 0) {
            		entrancePassQueue.addCar(new ReserveCar());
            	}
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
}