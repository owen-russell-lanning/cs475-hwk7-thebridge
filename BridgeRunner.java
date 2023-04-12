
/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {

		// parse in the command line arguments. make sure number strings are valid
		if (args.length < 2) {
			System.out.println("Not enough arguments given");
			return;
		}

		int limit = 0;
		int numCars = 0;

		try {
			limit = Integer.parseInt(args[0]);
			numCars = Integer.parseInt(args[1]);
		} catch (NumberFormatException err) {
			System.out.println("Invalid arguments given");
			return;
		}

		if(limit <= 0 || numCars <= 0){
			System.out.println("Arguments must be positive");
			return;
		}

		// setup the bridge
		OneLaneBridge bridge = new OneLaneBridge(limit);

		// init cars and threads
		Car[] cars = new Car[numCars];
		Thread[] threads = new Thread[numCars];
		for (int i = 0; i < numCars; i++) {
			cars[i] = new Car(i, bridge);
			threads[i] = new Thread(cars[i]);
		}

		// start threads
		for (int i = 0; i < numCars; i++) {
			threads[i].start();
		}

		// join threads
		for (int i = 0; i < numCars; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException err) {
				System.out.println("thread interrupted");
			}
		}

		System.out.println("All cars have crossed!!");
	}

}