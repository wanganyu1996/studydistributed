package com.gupao.vip.michael.zookeeper.discributelock.javaapilock.zkclient;

import java.io.Serializable;

public class UserCenter implements Serializable {

    private static final long serialVersionUID = -1126650135299604428L;
    //机器信息
    private int mic_id;
    //机器名称
    private String mc_name;

    public int getMic_id() {
        return mic_id;
    }

    public void setMic_id(int mic_id) {
        this.mic_id = mic_id;
    }

    public String getMc_name() {
        return mc_name;
    }

    public void setMc_name(String mc_name) {
        this.mc_name = mc_name;
    }

    @Override
    public String toString() {
        return "UserCenter{" +
                "mic_id=" + mic_id +
                ", mc_name='" + mc_name + '\'' +
                '}';
    }
}
