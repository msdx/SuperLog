package com.githang.android.superlog.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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
            file = this.getFilesDir();
        }
        logConfig.logPath = file.getPath() + File.separator + "log";
        Log.d("MainActivity", logConfig.logPath);
        SuperLog.setConfig(logConfig);
        SuperLog log = SuperLog.getLog(MainActivity.class);
//        log.v("verbose log");
//        log.v("verbose log, throwable", new Exception("verbose demo"));
//        log.d("debug log");
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
