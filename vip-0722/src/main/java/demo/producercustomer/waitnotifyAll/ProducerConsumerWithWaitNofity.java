package demo.producercustomer.waitnotifyAll;

import demo.producercustomer.waitnotifyAll.CustomerThread;
import demo.producercustomer.waitnotifyAll.ProductorThread;
import demo.producercustomer.waitnotifyAll.Resource;

public class ProducerConsumerWithWaitNofity {
    public static void main(String[] args) {
        Resource resource = new Resource();
        ProductorThread productorThread = new ProductorThread(resource);
        ProductorThread productorThread2 = new ProductorThread(resource);
        CustomerThread customerThread = new CustomerThread(resource);

        productorThread.start();
        productorThread2.start();

        customerThread.start();
//        CustomerThread customerThread1 = new CustomerThread(resource);
//        customerThread1.start();
    }
}
