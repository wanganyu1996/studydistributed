package demo.producercustomer.waitnotifyAll;

public class ProductorThread extends Thread{
    private Resource resource;
    public ProductorThread(Resource resource){
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
