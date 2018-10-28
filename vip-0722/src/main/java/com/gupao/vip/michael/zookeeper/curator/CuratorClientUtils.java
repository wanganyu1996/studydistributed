package com.gupao.vip.michael.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorClientUtils {
    private static CuratorFramework curatorFramework;
    private final static String  CONNECTSTRING="192.168.1.111:2181,192.168.1.112:2181,"+"192.168.1.114:2181,192.168.1.115:2181";
    public static CuratorFramework getInstance(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECTSTRING,5000,5000,new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }
}
