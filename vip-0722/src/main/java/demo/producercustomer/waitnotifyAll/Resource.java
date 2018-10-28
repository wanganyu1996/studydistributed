package demo.producercustomer.waitnotifyAll;

/**
 * 资源类
 */
public class Resource {
  private int num=0;//当前资源
  private int size=10;//资源池中允许存放的资源数目
  public synchronized void remove(){
      if(num>0){
          num--;
          System.out.println("消费者:"+Thread.currentThread().getName()+"消费了资源。"+"当前资源池还剩："+num+"个资源！");
          notifyAll();
      }else{
          try {
              wait();
              System.out.println("消费者"+Thread.currentThread().getName()+"进入等待状态");
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
  public synchronized void add(){
      if(num<size){
          num++;
          System.out.println("生产者"+Thread.currentThread().getName()+"生产了一个资源。当前资源池有"+num+"个资源");
          notifyAll();//通知消费者消费数据
      }else{
          try {
              wait();
              System.out.println("生产者"+Thread.currentThread().getName()+"阻塞");
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
}
