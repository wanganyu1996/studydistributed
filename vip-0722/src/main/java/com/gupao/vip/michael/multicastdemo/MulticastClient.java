package com.gupao.vip.michael.multicastdemo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastClient {
    public static void main(String[] args) throws IOException {
        InetAddress group=InetAddress.getByName("224.5.6.7");
        MulticastSocket socket=new MulticastSocket(8888);
        socket.joinGroup(group);
        byte[] buf=new byte[256];
        while(true){
            DatagramPacket datagramPacket=new DatagramPacket(buf,buf.length);
            socket.receive(datagramPacket);
            String msg=new String(datagramPacket.getData());
            System.out.println("接收到的数据"+msg);

        }

    }
}
