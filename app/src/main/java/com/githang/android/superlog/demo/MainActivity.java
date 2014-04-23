package com.githang.android.superlog.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.githang.android.superlog.LogConfig;
import com.githang.android.superlog.SuperLog;

import java.io.File;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogConfig logConfig = new LogConfig();
        logConfig.isSaveLog = true;
        File file = this.getExternalFilesDir("log");
        if(file == null) {
            file = new File(this.getFilesDir(), "log");
        }
        logConfig.logPath = file.getPath() ;
        logConfig.isSendEmail = true;
        logConfig.receiveEmail="log@msdx.pw";
        logConfig.sendEmailUser="log@msdx.pw";
        logConfig.sendEmailPassword="********";
        logConfig.sendEmailPort="465";
        logConfig.sendEmailHost="smtp.qq.com";
        Log.d("MainActivity", logConfig.logPath);
        SuperLog.setConfig(logConfig);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        SuperLog.sendHistoryLog(MainActivity.this);
                        SuperLog.sendCurrentLog(MainActivity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        TextView text = (TextView) findViewById(R.id.text);
        StringBuilder sb = new StringBuilder();
        for(File f: file.listFiles()){
            sb.append(f.getPath()).append('\n');
            if(f.isDirectory()) {
                for (File ff : f.listFiles()) {
                    sb.append('\t').append(ff.getName()).append("   ").append(ff.length()).append('\n');
                }
            }
        }
        text.setText(sb.toString());

        SuperLog log = SuperLog.getLog(MainActivity.class);
//        log.v("verbose log");
//        log.v("verbose log, throwable", new Exception("verbose demo"));
        log.d("debug log");
//        log.d("debug log, throwable", new Exception("debug demo"));
//        log.i("info log");
//        log.i("info log, throwable", new Exception("info demo"));
//        log.w("warn log");
//        log.w("warn log, throwable", new Exception("warn demo"));
//        log.w("warn log, throwable", new Exception("warn demo"));
//        log.e("error log");
//        log.e("error log, throwable", new Exception("error demo"));
    }

}
