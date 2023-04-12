public class OneLaneBridge extends Bridge {

    private int limit;

    /**
     * 
     * @param limit the total number of cars allowed on this bridge at one time
     */
    public OneLaneBridge(int limit) {
        super();
        this.limit = limit;
    }

    public void Print(){
        System.out.println("Bridge (dir = " + this.direction + "): " + this.bridge);
    }

    @Override
    public void arrive(Car car) throws InterruptedException {

        if (!(this.bridge.size() == limit || (car.getDirection() != super.direction && this.bridge.size() != 0))) {
            synchronized (this) {
                if (this.bridge.size() == 0) {
                    this.direction = car.getDirection();
                }

                car.setEntryTime(super.currentTime);
                super.bridge.add(car);
                this.Print();
                currentTime += 1;
            }
        } else {
            synchronized (this) {
                this.wait();
            }
            arrive(car);
        }

    }

    @Override
    public void exit(Car car) throws InterruptedException {

        while (super.bridge.indexOf(car) != 0) {
            synchronized (this) {
                this.wait(100);
            }
        }

        synchronized (this) {

            super.bridge.remove(car);
            this.Print();

            this.notifyAll();

        }

    }
}
