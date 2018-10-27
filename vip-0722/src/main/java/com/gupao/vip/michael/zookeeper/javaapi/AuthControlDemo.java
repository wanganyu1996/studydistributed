package com.gupao.vip.michael.zookeeper.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AuthControlDemo implements Watcher{
    private final static String CONNECTSTRING = "192.168.1.111:2181,192.168.1.112:2181," + "192.168.1.114:2181,192.168.1.115:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static CountDownLatch countDownLatch2 = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        zooKeeper = new ZooKeeper(CONNECTSTRING, 5000, new AuthControlDemo());
        countDownLatch.await();
         ACL acl=new ACL(ZooDefs.Perms.CREATE,new Id("digest","root:root"));
         ACL acl2=new ACL(ZooDefs.Perms.CREATE,new Id("ip","192.168.1.110"));
        List<ACL> acls = new ArrayList<>();
        acls.add(acl);
        acls.add(acl2);
        zooKeeper.create("/auth1","123".getBytes(),acls,CreateMode.PERSISTENT);
         //权限控制
        zooKeeper.addAuthInfo("digest","root:root".getBytes());
        zooKeeper.create("/auth1","123".getBytes(),ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);
        zooKeeper.create("/auth1/auth1-1","123".getBytes(),ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);

       ZooKeeper zooKeeper1 = new ZooKeeper(CONNECTSTRING, 5000, new AuthControlDemo());
       zooKeeper1.delete("/auth1",-1);
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
            System.out.println(watchedEvent.getState() + "===>" + watchedEvent.getType());
        }
    }

}
