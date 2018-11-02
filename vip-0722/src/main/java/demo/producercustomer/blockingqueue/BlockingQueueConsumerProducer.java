package demo.producercustomer.blockingqueue;

public class BlockingQueueConsumerProducer {
    public static void main(String[] args) {
        Resource3 resource = new Resource3();
        //生产者线程
        ProducerThread3 p = new ProducerThread3(resource);
        ProducerThread3 p2 = new ProducerThread3(resource);
        ProducerThread3 p3 = new ProducerThread3(resource);
        //多个消费者
        ConsumerThread3 c1 = new ConsumerThread3(resource);
        //ConsumerThread3 c2 = new ConsumerThread3(resource);
       // ConsumerThread3 c3 = new ConsumerThread3(resource);

        p.start();
        p2.start();
        p3.start();
        c1.start();
        //c2.start();
        //c3.start();


    }
}
