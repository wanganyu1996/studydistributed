package com.gupao.vip.michael.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

public class ZkClientApiOperatorDemo {
    private final static String CONNECTSTRING = "192.168.1.111:2181,192.168.1.112:2181," + "192.168.1.114:2181,192.168.1.115:2181";
    private static ZkClient getInstance(){
        return new ZkClient(CONNECTSTRING,5000);
    }

    public static void main(String[] args) {
        ZkClient zkClient = getInstance();
        zkClient.createPersistent("/zkClient/zkClient01/zkClient02",true);
        System.out.println("success");
    }
}
