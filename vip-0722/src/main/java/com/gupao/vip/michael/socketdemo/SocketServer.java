package com.gupao.vip.michael.socketdemo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
            ServerSocket serverSocket=null;
            serverSocket=new ServerSocket(8888);//启动服务
            while(true) {
                final Socket socket = serverSocket.accept();//等待接受请求
                new Thread(()->{
                    try {
                    //读取数据
                    BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //发送数据
                    PrintWriter writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    while(true){
                        String clientData=reader.readLine();//读取客户端发送过来的消息
                        if(clientData==null){
                            break;
                        }
                        System.out.println("服务端接受到的数据："+clientData);
                        writer.println("Hello Tom;***");
                        writer.flush();
                    }

                    }catch (Exception e){

                    }
               }).start();
            }



    }
}
