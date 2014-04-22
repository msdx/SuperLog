package com.githang.android.superlog;/**
 * Created by msdx on 2014/4/22.
 */

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-04-22
 * Time: 13:32
 * FIXME
 */
public class LogFileUtil {
    private static final String LOG_TAG = LogFileUtil.class.getSimpleName();

    protected static final char VERBOSE = 'V';
    protected static final char DEBUG = 'D';
    protected static final char INFO = 'I';
    protected static final char WARN = 'W';
    protected static final char ERROR = 'E';

    private static final Object lock = new Object();
    private static final SimpleDateFormat logFileFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());

    /**
     * 将日志写入文件
     * @param level
     * @param tag
     * @param message
     */
    static synchronized void writeLog(char level, String tag, String message) {
        File logFile = getLogFile();
        if (logFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    FileWriter fw = new FileWriter(logFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.append(time).append("    ").append(level).append('/').append(tag).append(" ").append(message).append('\n');
                    bw.flush();
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 将日志写入文件。
     * @param level
     * @param tag
     * @param message
     * @param tr
     */
    static synchronized void writeLog(char level, String tag, String message, Throwable tr) {
        File logFile = getLogFile();
        if(logFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    FileWriter fw = new FileWriter(logFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.append(time).append("    ").append(level).append('/').append(tag).append(" ").append(message).append('\n');
                    bw.flush();
                    PrintWriter pw = new PrintWriter(fw);
                    tr.printStackTrace(pw);
                    pw.flush();
                    fw.flush();
                    bw.close();
                    pw.close();
                    fw.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }
    }

    static synchronized void writeLog(char level, String tag, Throwable tr) {
        File logFile = getLogFile();
        if(logFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    FileWriter fw = new FileWriter(logFile, true);
                    PrintWriter pw = new PrintWriter(fw);
                    tr.printStackTrace(pw);
                    pw.flush();
                    fw.flush();
                    pw.close();
                    fw.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 返回一个日志文件路径。
     *     * @return
     */
    private static File getLogFile() {
        File logDir = new File(SuperLog.config.logPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        File logFile = new File(logDir, logFileFormat.format(Calendar.getInstance().getTime()) + ".log");
        if (!logFile.exists()) {
            synchronized (lock) {
                if (!logDir.exists()) {
                    try {
                        logFile.createNewFile();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        return null;
                    }
                }
            }
        }
        return logFile;
    }
}
