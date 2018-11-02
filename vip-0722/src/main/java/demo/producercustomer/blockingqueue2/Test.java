package demo.producercustomer.blockingqueue2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
        Customer customer = new Customer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < 5; i++) {
            new Thread(producer,"Produce"+(i+1)).start();
            new Thread(customer,"Customer"+(i+1)).start();
        }
    }
}
