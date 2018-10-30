package com.gupao.vip.michael.zookeeper.discributelock.javaapilock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DistributeLock {
    //根节点
    public static final String ROOT_LOCKS = "/LOCKS";
    private ZooKeeper zooKeeper;
    //会话超时时间
    private int sessionTimeout;
    //记录锁节点id
    private String lockID;
    //节点数据
    private final static byte[] data = {1, 2};

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public DistributeLock() {
        try {
            this.zooKeeper = ZookeeperClient.getInstance();
            this.sessionTimeout = ZookeeperClient.getSessionTimeout();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public  boolean lock() {
        try {
            lockID = zooKeeper.create(ROOT_LOCKS + "/ ", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "->成功创建了lock节点[ " + lockID + "],开始去竞争锁");
            List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS, true);//获取根节点下的所有子节点
            SortedSet<String> sortedSet = new TreeSet<String>();
            for (String children : childrenNodes) {
                sortedSet.add(ROOT_LOCKS + "/" + children);
            }
            String first = sortedSet.first();
            if (lockID.equals(first)) {
                //表示当前是最小节点
                System.out.println(Thread.currentThread().getName() + "成功获得锁，lock节点为：[" + lockID + "]");
                return true;
            }
            SortedSet<String> lessThanLockId = ((TreeSet<String>) sortedSet).headSet(lockID);
            if (!lessThanLockId.isEmpty()) {
                //拿到比当前LOCKID这个节点更小的节点
                String prevLockID = lessThanLockId.last();
                zooKeeper.exists(prevLockID, new LockWatcher(countDownLatch));
                countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                //如果会话超时或者节点被删除（释放）了
                System.out.println(Thread.currentThread().getName() + "成功获取锁：[" + lockID + "]");
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public  boolean unlock() {
        System.out.println(Thread.currentThread().getName() + "->开始释放锁[" + lockID + "]");
        try {
            zooKeeper.delete(lockID, -1);
            System.out.println("节点[" + lockID + "]成功被删除");
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Random random = new Random();
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                DistributeLock lock = null;
                try {
                    lock = new DistributeLock();
                    latch.countDown();
                    latch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }

            }).start();
        }
    }
}
