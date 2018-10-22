package com.example.qiaoxian.mytcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTask extends Thread implements MsgPool.MsgComingListener{

    private Socket socketTask;
    private InputStream is;
    private OutputStream os;

    public ClientTask(Socket socket){
        socketTask = socket;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println("read : "+line);
                MsgPool.getInstance().sendMsg(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMsgComing(String msg) {
        try {
            os.write(msg.getBytes());
            os.write("\n".getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
