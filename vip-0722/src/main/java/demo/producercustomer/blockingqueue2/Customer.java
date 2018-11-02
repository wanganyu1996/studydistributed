package demo.producercustomer.blockingqueue2;

import java.util.concurrent.BlockingQueue;

public class Customer  implements Runnable{
    private BlockingQueue<String> queue;

    public Customer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String temp=queue.take();
            System.out.println("消费者消费了"+temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
