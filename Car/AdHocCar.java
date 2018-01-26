package Car;
 
import java.util.Random;
import java.awt.*;

//AdHoc zijn auto's die geen abonnement hebben
public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	
    public AdHocCar(boolean isLate) {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    	//Als het laat is gaan mensen eerder weg
    	if(isLate) {
    		stayMinutes = stayMinutes / 2;
    	}
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}