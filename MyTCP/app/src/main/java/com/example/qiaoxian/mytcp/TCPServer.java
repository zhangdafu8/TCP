package com.example.qiaoxian.mytcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public void start(){
        ServerSocket serverSocket = null;
        try {
            while(true){
                serverSocket = new ServerSocket(9090);
                MsgPool.getInstance().start();
                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println("ip:"+socket.getInetAddress().getHostAddress()+",port="
                            +socket.getPort()+"is connected");

                    ClientTask clientTask = new ClientTask(socket);
                    MsgPool.getInstance().addLisnter(clientTask);
                    clientTask.start();
                }


//                socket.getInputStream();
//                socket.getOutputStream();

            }


        } catch (IOException e) {

        }

    }
    public static void main(String[] args){
        new TCPServer().start();
    }
}
