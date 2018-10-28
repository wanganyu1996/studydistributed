package com.gupao.vip.michael.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CuratorOperatorDemo {
    private final static String  CONNECTSTRING="192.168.1.111:2181,192.168.1.112:2181,"+"192.168.1.114:2181,192.168.1.115:2181";
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorClientUtils.getInstance();
        System.out.println("连接成功.....");
        /**
         * fluent风格
         */
        /**
         * 创建节点
         */
       /* try {
           String result= curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curator/curator1/curator11", "123".getBytes());
           System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /**
         * 删除节点
         *//*
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/zkClient");*/
//        /**
//         * 查询节点
//         */
//        Stat stat = new Stat();
//        byte[] bytes=curatorFramework.getData().storingStatIn(stat).forPath("/curator");
//        System.out.println(new String(bytes)+"==>stat:"+stat);

        /***
         * 更新
         */
      /*  Stat stat=curatorFramework.setData().forPath("/curator","123".getBytes());
        System.out.println(stat);*/

        /**
         * 异步操作
         */
       /* ExecutorService service = Executors.newFixedThreadPool(1);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println(Thread.currentThread().getName()+"==>resultCode:"+curatorEvent.getResultCode()+"==>"+curatorEvent.getType());
                countDownLatch.countDown();
            }
        },service).forPath("/mic","123".getBytes());
        countDownLatch.await();
        service.shutdown();*/
        /**
         * 事务操作
         */
       Collection<CuratorTransactionResult>  resultCollections=curatorFramework.inTransaction().create().forPath("/trans", "111".getBytes()).and()
                .setData().forPath("/curator", "111".getBytes()).and().commit();
       for (CuratorTransactionResult result:resultCollections){
           System.out.println(result.getForPath()+"==>"+result.getType());
       }


    }

}
