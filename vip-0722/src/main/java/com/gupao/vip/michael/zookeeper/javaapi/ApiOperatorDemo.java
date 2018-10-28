package com.gupao.vip.michael.zookeeper.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ApiOperatorDemo implements Watcher {
    private final static String CONNECTSTRING = "192.168.1.111:2181,192.168.1.112:2181," + "192.168.1.114:2181,192.168.1.115:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new ApiOperatorDemo());
        countDownLatch.await();
       /* System.out.println(zooKeeper.getState());
        String result = zooKeeper.create("/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData("/node1", new ApiOperatorDemo(), stat);
        System.out.println("创建成功" + result);

        zooKeeper.setData("/node1", "mic123".getBytes(), -1);
        Thread.sleep(2000);
        zooKeeper.setData("/node1", "mic234".getBytes(), -1);
        Thread.sleep(2000);

//        zooKeeper.delete("/mic/mic1", -1);
//        Thread.sleep(2000);

        //创建节点和子节点
        String path = "/node12";
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        TimeUnit.SECONDS.sleep(1);

        Stat stat = zooKeeper.exists(path + "/node1",true);
        if(stat==null){
            zooKeeper.create(path + "/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
        zooKeeper.setData(path+"/node1", "mic123".getBytes(), -1);
        TimeUnit.SECONDS.sleep(1);*/


        //获取指定节点下的子节点
        List<String> children= zooKeeper.getChildren("/node12", true);

        System.out.println(children);
        //权限控制

     }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            System.out.println(watchedEvent.getState() + "===>" + watchedEvent.getType());
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {

            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("数据变更路径:" + watchedEvent.getPath() + "改变后的值:" +
                            zooKeeper.getData(watchedEvent.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("子节点变更路径" + watchedEvent.getPath() + "节点的值:" +
                            zooKeeper.getData(watchedEvent.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                try {
                    System.out.println("节点创建路径" + watchedEvent.getPath() + "节点的值:" +
                            zooKeeper.getData(watchedEvent.getPath(), true, stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                System.out.println("节点删除路径" + watchedEvent.getPath());
            }
            System.out.println(watchedEvent.getType());
        }
    }
}
