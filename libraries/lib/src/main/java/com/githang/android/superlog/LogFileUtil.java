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

    private static final LogFileUtil instance =  new LogFileUtil();
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private String lastLogDate;
    private File lastLogFile;

    private LogFileUtil(){}

    /**
     * 将日志写入文件
     * @param level
     * @param tag
     * @param message
     */
    static synchronized void writeLog(char level, String tag, String message) {
        writeLogReady();
        if(instance.lastLogFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    instance.bufferedWriter.append(time).append("    ").append(level).append('/').append(tag).append(" ").append(message).append('\n');
                    instance.bufferedWriter.flush();
                } catch (IOException e) {
                    closeFileHandler();
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
        writeLogReady();
        if(instance.lastLogFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    instance.bufferedWriter.append(time).append("    ").append(level).append('/').append(tag).append(" ").append(message).append('\n');
                    instance.bufferedWriter.flush();
                    tr.printStackTrace(instance.printWriter);
                    instance.printWriter.flush();
                    instance.fileWriter.flush();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    closeFileHandler();
                }
            }
        }
    }

    static synchronized void writeLog(char level, String tag, Throwable tr) {
        writeLogReady();
        if(instance.lastLogFile != null) {
            String time = timeFormat.format(Calendar.getInstance().getTime());
            synchronized (lock) {
                try {
                    instance.bufferedWriter.append(time).append("    ").append(level).append('/').append(tag).append(":");
                    instance.bufferedWriter.flush();
                    tr.printStackTrace(instance.printWriter);
                    instance.printWriter.flush();
                    instance.fileWriter.flush();
                } catch (IOException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    closeFileHandler();
                }
            }
        }
    }

    private static final void writeLogReady() {
        File logFile = getLogFile();
        openFileHandler(logFile);
    }

    /**
     * 返回一个日志文件路径。
     *     * @return
     */
    static File getLogFile() {
        File logDir = new File(SuperLog.config.logPath);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        String currentDate = logFileFormat.format(Calendar.getInstance().getTime());

        // 如果要保存的文件的名字一样
        if(currentDate.equals(instance.lastLogDate) && instance.lastLogFile != null) {
            return instance.lastLogFile;
        }

        // 如果要保存的文件名字与上次的不一样，则重新创建
        File logFile = new File(logDir, "super-" + currentDate + ".log");
        instance.lastLogFile = logFile;
        instance.lastLogDate = currentDate;
        if (!logFile.exists()) {
            synchronized (lock) {
                if (!logDir.exists()) {
                    try {
                        logFile.createNewFile();
                        closeFileHandler();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                        return null;
                    }
                }
            }
        }
        return logFile;
    }

    private static final void openFileHandler(File logFile) {
        if(instance.fileWriter == null ) {
            try {
                Log.d(LOG_TAG, "open fileWriter");
                instance.fileWriter = new FileWriter(logFile, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(instance.bufferedWriter == null) {
            instance.bufferedWriter = new BufferedWriter(instance.fileWriter);
        }
        if(instance.printWriter == null) {
            instance.printWriter = new PrintWriter(instance.fileWriter);
        }
    }

    private static final void closeFileHandler() {
        if(instance.fileWriter != null) {
            try {
                instance.fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                instance.fileWriter = null;
            }
        }
        if(instance.bufferedWriter != null) {
            try {
                instance.bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                instance.bufferedWriter = null;
            }
        }
        if (instance.printWriter != null) {
            instance.printWriter.close();
            instance.printWriter = null;
        }
    }
}
