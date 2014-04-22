package com.githang.android.superlog;/**
 * Created by msdx on 2014/4/22.
 */

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-04-22
 * Time: 17:22
 * FIXME56
 */
public class LogSendUtil {
    private static final String LOG_TAG = LogSendUtil.class.getSimpleName();

    static void send() throws Exception {
        File sendDir = sendReady();
        File[] logs = sendDir.listFiles();
        if(logs.length == 0) {
            Log.i(LOG_TAG, "no log file need to send");
            return;
        }
        LogConfig config = SuperLog.config;
        LogSender sender = new LogSender(config.sendEmailUser, config.sendEmailPassword, config.sendEmail, config.receiveEmail, config.sendEmailHost, config.sendEmailPort, config.mailSubject, "");
        for (File log: logs) {
            sender.addAttachment(log.getPath(), log.getName());
        }
        sender.send();
        afterSend(sendDir);
    }

    private static File sendReady() {
        File sendDir = new File(SuperLog.config.logPath, "send");
        if(!sendDir.exists()) {
            sendDir.mkdirs();
        }
        File logDir = new File(SuperLog.config.logPath);

        FileFilter ff = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        };
        File[] logs = logDir.listFiles(ff);
        try {
            File currentLog = LogFileUtil.getLogFile().getCanonicalFile();
            for (File logFile : logs) {
                Log.d("LogSend", logFile.getName());
                if(logFile.getCanonicalFile().equals(currentLog)) {
                    continue;
                }
                logFile.renameTo(new File(sendDir, logFile.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendDir;
    }

    private static void afterSend(File sendDir) {
        File sendOutDir = new File(SuperLog.config.logPath, "sended");
        if(!sendOutDir.exists()) {
            sendOutDir.mkdirs();
        }
        File[] logs = sendDir.listFiles();
        for (File logFile: logs) {
            logFile.renameTo(new File(sendOutDir, logFile.getName()));
        }
        if(SuperLog.config.deleteFileAfterSend) {
            sendDir.deleteOnExit();
        }
    }
}
