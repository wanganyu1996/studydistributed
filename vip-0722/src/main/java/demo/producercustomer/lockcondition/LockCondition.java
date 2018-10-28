package demo.producercustomer.lockcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition customerCondition = lock.newCondition();
        Condition produceCondition = lock.newCondition();

        Resource2 resource2 = new Resource2(lock, produceCondition, customerCondition);

        //生产者线程
        ProducerThread2 producer1 = new ProducerThread2(resource2);
        ProducerThread2 producer2 = new ProducerThread2(resource2);

        //消费者线程
        ConsumerThread2 consumer1 = new ConsumerThread2(resource2);
       ConsumerThread2 consumer2 = new ConsumerThread2(resource2);
   /*     ConsumerThread2 consumer3 = new ConsumerThread2(resource2);*/

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
       /* consumer3.start();*/
    }
}
