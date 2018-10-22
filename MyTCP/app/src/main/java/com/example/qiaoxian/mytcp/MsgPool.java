package com.example.qiaoxian.mytcp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MsgPool {

    private static MsgPool instance = new MsgPool();

    private LinkedBlockingQueue<String> mQueue = new LinkedBlockingQueue<>();

    private MsgPool(){

    }

    public static MsgPool getInstance(){
        return instance;
    }

    public void start(){
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        String msg = mQueue.take();
                        for(MsgComingListener listener:mListener){
                            listener.onMsgComing(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public interface MsgComingListener{
        void onMsgComing(String msg);
    }

    private List<MsgComingListener> mListener = new ArrayList<>();

    public void addLisnter(MsgComingListener listener){
        mListener.add(listener);
    }

    public void sendMsg(String msg){
        try {
            mQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
