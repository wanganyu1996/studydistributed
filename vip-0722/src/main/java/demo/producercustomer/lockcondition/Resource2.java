package demo.producercustomer.lockcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 资源类
 */
public class Resource2 {

    private int num = 0;//当前资源
    private int size = 10;//资源池中允许存放的资源数目
    private Lock lock;
    private Condition producerCondition;
    private Condition customerCondition;
    public Resource2(Lock lock, Condition producerCondition, Condition customerCondition) {
        this.lock = lock;
        this.producerCondition = producerCondition;
        this.customerCondition = customerCondition;
    }

    public void remove() {
        lock.lock();
        try {
            if (num > 0) {
                num--;
                System.out.println("消费者:" + Thread.currentThread().getName() + "消费了资源。" + "当前资源池还剩：" + num + "个资源！");
                producerCondition.signalAll();
            } else {
                try {
                    customerCondition.await();
                    System.out.println("消费者" + Thread.currentThread().getName() + "进入等待状态");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }

    }

    public void add() {
        lock.lock();
        try {
            if (num < size) {
                num++;
                System.out.println("生产者" + Thread.currentThread().getName() + "生产了一个资源。当前资源池有" + num + "个资源");
                customerCondition.signalAll();//通知消费者消费数据
            } else {
                try {
                    producerCondition.await();
                    System.out.println("生产者" + Thread.currentThread().getName() + "阻塞");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
