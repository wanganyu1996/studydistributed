package com.gupao.vip.michael.zookeeper.discributelock.javaapilock.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 选主的服务
 */
public class MasterSelector {
    private ZkClient zkClient;
    //需要争抢的节点
    public static final String MASTER_PATH="/master";
    //注册节点内容的变化
    private IZkDataListener dataListener;
    //其他服务器
    private UserCenter server;
    //主服务器
    private UserCenter master;
    private   boolean isRunning=false;

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public MasterSelector(UserCenter server,ZkClient zkClient) {
        System.out.println("["+server+"] 去争抢master权限");
        this.server = server;
        this.zkClient = zkClient;
        this.dataListener=new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //如果节点被删除，发起选主操作
                chooseMaster();
            }
        };
    }
    public void start(){
        //开始选举
        if(!isRunning){
            isRunning = true;
            //注册事件
            zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
            chooseMaster();
        }
    }
    public void stop(){
        //停止选举
        if(isRunning){
            isRunning = false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
            realseMaster();
        }
    }

    /**
     * 具体选主实现逻辑
     */
    private void chooseMaster(){
        if(!isRunning){
            System.out.println("当前服务没有启动");
            return;
        }
        try {
            zkClient.createEphemeral(MASTER_PATH, server);
            //把server节点赋值给master
            master = server;
            System.out.println(master + "->我现在已经是master，你们要听我的");
            //定时器
            //master释放(master出现故障) 每五秒钟释放一次
            scheduledExecutorService.schedule(() -> {
                realseMaster();//释放锁
            },2,TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
          //表示master已经存在

            UserCenter userCenter=zkClient.readData(MASTER_PATH, true);
            if(userCenter==null){
                System.out.println("路径已经存在");
                chooseMaster();
            }else{
                master = userCenter;

            }
        }
    }
    private void realseMaster(){
        //释放锁(故障模拟过程)
        //判断当前是不是master,只有master才需要释放
        if(checkIsMaster()){
            zkClient.delete(MASTER_PATH);
        }
    }
    private boolean checkIsMaster(){
        //判断当前的server是不是master
        UserCenter userCenter = zkClient.readData(MASTER_PATH);
        if(userCenter.getMc_name().equals(server.getMc_name())){
            master = userCenter;
            return true;
        }
        return false;
    }
}
