package Car;

import java.util.Random;
import java.awt.*;

public class ReserveSpot extends Car {
	private static final Color COLOR= new Color(202, 170, 221);
	
    public ReserveSpot() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
