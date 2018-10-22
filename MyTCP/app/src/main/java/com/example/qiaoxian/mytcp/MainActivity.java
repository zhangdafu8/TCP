package com.example.qiaoxian.mytcp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ScrollView scrollView;
    private TextView textView;
    private TCPBiz tcpBiz = new TCPBiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.myText);

        tcpBiz.setOnMsgComingListener(new TCPBiz.OnMsgComingListerner() {
            @Override
            public void onComingMsg(String msg) {
                textView.append("client"+msg+"\n");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();
                if(TextUtils.isEmpty(msg)){
                    return;
                }
//                textView.append("client"+msg+"\n");
                editText.setText("");
                tcpBiz.sendMsg(msg);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tcpBiz.onDestroy();
    }
}
