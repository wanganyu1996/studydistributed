package demo.producercustomer.blockingqueue;

import demo.producercustomer.lockcondition.Resource2;

public class ProducerThread3 extends Thread{
    private Resource3 resource;
    public ProducerThread3(Resource3 resource){
        this.resource = resource;
    }
    @Override
    public void run() {
      while(true){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resource.add();
      }
    }
}
