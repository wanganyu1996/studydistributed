package com.gupao.vip.michael.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionDemo {
    private final static String  CONNECTSTRING="192.168.1.111:2181,192.168.1.112:2181,"+"192.168.1.114:2181,192.168.1.115:2181";
    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(CONNECTSTRING,4000);
        zkClient.deleteRecursive("/mic");
        System.out.println(zkClient+"===>success");
        List<String> list = zkClient.getChildren("/node12");
        System.out.println(list);
        zkClient.writeData("/node12","node5");
        TimeUnit.SECONDS.sleep(2);
        //wat cher
        zkClient.subscribeDataChanges("/node12", new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("节点名称："+s+"==>节点修改后的值"+list);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        zkClient.writeData("/node12","node5");
        TimeUnit.SECONDS.sleep(2);
        zkClient.subscribeChildChanges("/node", new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {

            }
        });
    }
}
