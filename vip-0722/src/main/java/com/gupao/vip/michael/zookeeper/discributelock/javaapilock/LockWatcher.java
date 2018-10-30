package com.gupao.vip.michael.zookeeper.discributelock.javaapilock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

public class LockWatcher implements Watcher {
    public LockWatcher(CountDownLatch latch) {
        this.latch = latch;
    }

    private CountDownLatch latch;
    @Override
    public void process(WatchedEvent watchedEvent) {
       if(watchedEvent.getType()==Event.EventType.NodeDeleted){
           latch.countDown();

       }
    }
}
