package Car;

import java.awt.*;

public class ReserveSpot extends Car {
	private static final Color COLOR= new Color(202, 170, 221);
	
    public ReserveSpot() {
    	int stayMinutes = 60;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
