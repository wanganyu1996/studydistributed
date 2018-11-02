package com.gupao.vip.michael.zookeeper.discributelock.javaapilock.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MasterChooseTest {

    private final static String CONNECTSTRING = "192.168.1.111:2181,192.168.1.112:2181," + "192.168.1.114:2181,192.168.1.115:2181";
    public static void main(String[] args)  throws IOException {
        List<MasterSelector> selectorLists = new ArrayList<>();
        try {

            for (int i = 0; i < 10; i++) {
                ZkClient zkClient=new ZkClient(CONNECTSTRING,5000,5000,
                        new SerializableSerializer());
                 // clients.add(zkClient);
                UserCenter userCenter = new UserCenter();
                userCenter.setMic_id(i);
                userCenter.setMc_name("客户端："+i);
                MasterSelector selector = new MasterSelector(userCenter,zkClient);
                selectorLists.add(selector);
                //触发选举
                selector.start();
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            for (MasterSelector selector:selectorLists) {
                selector.stop();
            }
        }

    }
}
