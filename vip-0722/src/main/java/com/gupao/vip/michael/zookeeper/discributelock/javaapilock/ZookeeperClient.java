package com.gupao.vip.michael.zookeeper.discributelock.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 创建回话
 */
public class ZookeeperClient {
    private final static String CONNECTSTRING = "192.168.1.111:2181,192.168.1.112:2181," + "192.168.1.114:2181,192.168.1.115:2181";
    private static int sessionTimeout = 5000;
    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }

    public static void setSessionTimeout(int sessionTimeout) {
        ZookeeperClient.sessionTimeout = sessionTimeout;
    }
}
