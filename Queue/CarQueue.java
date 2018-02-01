package Queue;
 
import java.util.LinkedList;
import java.util.Random;

import Car.Car;

public class CarQueue {
    private LinkedList<Car> queue = new LinkedList<Car>();

    public boolean addCar(Car car) {
        return queue.add(car);
    }

    public Car removeCar() {
        return queue.poll();
    }

    public int carsInQueue(){
    	return queue.size();
    }
    
    public Car checkCar() {
    	return queue.peek();
    }
    
    public void removeImpatient() {
    	int size = queue.size();
    	int rand = new Random().nextInt(100);
    	if(size > 50 && rand > 10) {
    		for(int i = 0; i < 10; i++) {
    			size = queue.size();
    			int rand2 = new Random().nextInt(size);
        		queue.remove(rand2);
    		}
    	}
    	else if(size > 30 && rand > 10) {
    		int rand2 = new Random().nextInt(size);
    		queue.remove(rand2);
    	}
    }
}
