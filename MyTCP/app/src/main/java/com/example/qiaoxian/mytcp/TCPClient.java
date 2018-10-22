package com.example.qiaoxian.mytcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public Scanner mScanner;

    public TCPClient(){
        mScanner = new Scanner(System.in);
        mScanner.useDelimiter("\n");
    }

    public void start(){
        try {
            Socket socket = new Socket("192.168.50.29",9090);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            new Thread(){
                @Override
                public void run() {
                    String line = null;
                    try{
                        while((line = br.readLine())!=null){
                            System.out.println(line);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();

            while(true){
                String msg = mScanner.next();
                bw.write(msg);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new TCPClient().start();
    }
}
