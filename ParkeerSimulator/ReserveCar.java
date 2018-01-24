package ParkeerSimulator;
 

import java.util.Random;
import java.awt.*;

public class ReserveCar extends Car {
	private static final Color COLOR= new Color(106, 60, 137);
	
    public ReserveCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
