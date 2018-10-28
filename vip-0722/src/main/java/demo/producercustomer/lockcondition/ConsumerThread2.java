package demo.producercustomer.lockcondition;

import demo.producercustomer.waitnotifyAll.Resource;

public class ConsumerThread2 extends Thread{
    private Resource2 resource;

    public ConsumerThread2(Resource2 resource) {
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

