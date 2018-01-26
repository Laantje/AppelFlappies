package Car;

import java.util.Random;
import java.awt.*;

//Parking pass betekent abonnement
public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;
	
    public ParkingPassCar(boolean isLate) {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    	//Als het laat is, gaan mensen eerder weg.
    	if(isLate) {
    		stayMinutes = stayMinutes / 2;
    	}
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
