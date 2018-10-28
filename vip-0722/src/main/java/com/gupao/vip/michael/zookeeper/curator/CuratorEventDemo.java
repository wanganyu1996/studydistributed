package com.gupao.vip.michael.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

public class CuratorEventDemo {
    /**
     * 三种watcher做节点监控
     * pathcache 监视一个路径下子节点的创建、删除、节点数据更新
     * NodeCache 监视一个节点的创建、更新、删除
     * TreeCahce pathcache+nodecahce结合 合体（监视路径下的创建、删除、更新事件）
     */
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorClientUtils.getInstance();
/*
        //节点变化Nodecache
        NodeCache cache = new NodeCache(curatorFramework, "/curator", false);
        cache.start(true);
        cache.getListenable().addListener(()-> System.out.println("节点数据变化，变化后的值："+new String(cache.getCurrentData().getData())));
        curatorFramework.setData().forPath("/curator","菲菲".getBytes());*/
        /**
         * PathChildrenCache
         */
        PathChildrenCache cache = new PathChildrenCache(curatorFramework,"/event",true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        //Normal
        cache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent) ->{
           switch (pathChildrenCacheEvent.getType()){
               case CHILD_ADDED:
                   System.out.println("添加子节点");
                   break;
               case CHILD_REMOVED:
                   System.out.println("删除子节点");
                   break;
               case CHILD_UPDATED:
                   System.out.println("更新子节点");
                   break;
           }

        });
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event", "event".getBytes());
        TimeUnit.SECONDS.sleep(1);

        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/event/event1","1".getBytes());
        TimeUnit.SECONDS.sleep(1);

        curatorFramework.setData().forPath("/event/event1","222".getBytes());
        TimeUnit.SECONDS.sleep(1);

        curatorFramework.delete().forPath("/event/event1");
        System.in.read();
    }

}
