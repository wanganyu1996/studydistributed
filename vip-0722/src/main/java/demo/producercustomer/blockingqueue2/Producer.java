package demo.producercustomer.blockingqueue2;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    BlockingQueue<String> queue;
    @Override
    public void run() {

        try {
            String temp = " Product,生产线程：" + Thread.currentThread().getName();
            System.out.println("Product "+Thread.currentThread().getName()+" I have made a product");
            queue.put(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
