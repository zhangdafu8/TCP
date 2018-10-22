package com.example.qiaoxian.mytcp;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPBiz {
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private Handler handler = new Handler(Looper.getMainLooper());

    private OnMsgComingListerner monMsgComingListerner;


    public interface OnMsgComingListerner{
        void onComingMsg(String msg);
        void onError(Exception ex);
    }

    public void setOnMsgComingListener(OnMsgComingListerner listener){
        monMsgComingListerner = listener;
    }



    public TCPBiz(){
        new Thread(){
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.50.29", 9090);
                    is = socket.getInputStream();
                    os = socket.getOutputStream();
                    final BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    String line = null;
                    while((line = br.readLine())!=null){
                        final String finalLine = line;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(monMsgComingListerner!=null){
                                    monMsgComingListerner.onComingMsg(finalLine);
                                }
                            }
                        });

                    }

                }catch(final IOException e){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(monMsgComingListerner!=null){
                                monMsgComingListerner.onError(e);
                            }
                        }
                    });

                }
            }
        };
    }

    public void sendMsg(final String msg){
        new Thread(){
            @Override
            public void run() {

                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                    bw.write(msg);
                    bw.newLine();
                    bw.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    public void onDestroy() {
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(is!=null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(os!=null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}