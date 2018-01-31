package Car;
 
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();

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
    
    public void removeRandom() {
    	int size = queue.size();
    	int rand = new Random().nextInt(100);
    	if (rand > 80) {
    		if (size > 30) {
    			int rand2 = new Random().nextInt(size);
    			queue.remove(rand2);
    		}
    	}
    }
}
