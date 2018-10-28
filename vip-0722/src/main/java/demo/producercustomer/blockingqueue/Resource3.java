package demo.producercustomer.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 资源类
 */
public class Resource3 {

    private BlockingQueue resourceQueue = new LinkedBlockingQueue(10);
    public void remove() {
        try {
            resourceQueue.take();
            System.out.println("消费者:" + Thread.currentThread().getName() + "消费了资源。" + "当前资源池还剩：" + resourceQueue.size() + "个资源！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    public void add() {
        try {
            resourceQueue.put(1);
            System.out.println("生产者" + Thread.currentThread().getName() + "生产了一个资源。当前资源池有" + resourceQueue.size() + "个资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
