package demo.producercustomer.blockingqueue;

import demo.producercustomer.lockcondition.Resource2;

public class ConsumerThread3 extends Thread{
    private Resource3 resource;

    public ConsumerThread3(Resource3 resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                resource.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

