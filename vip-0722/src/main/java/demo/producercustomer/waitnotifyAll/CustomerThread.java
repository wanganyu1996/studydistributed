package demo.producercustomer.waitnotifyAll;

public class CustomerThread extends Thread{
    private Resource resource;

    public CustomerThread(Resource resource) {
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

