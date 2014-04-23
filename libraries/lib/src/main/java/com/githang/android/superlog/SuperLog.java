package com.githang.android.superlog;/**
 * Created by msdx on 2014/4/21.
 */

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-04-21
 * Time: 23:57
 * FIXME
 */
public final class SuperLog {
    private static final SuperLog localLog = getLog(SuperLog.class);

    private final String LOG_TAG;


    private SuperLog(Class<?> cls) {
        LOG_TAG = cls.getSimpleName();
    }

    public static final SuperLog getLog(Class<?> cls) {
        return new SuperLog(cls);
    }

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = Log.VERBOSE;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = Log.DEBUG;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = Log.INFO;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = Log.WARN;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = Log.ERROR;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = Log.ASSERT;

    /**
     * LOG的配置。
     */
    protected static LogConfig config;

    static {
        config = new LogConfig();
    }

    public static void sendHistoryLog(Context context) throws Exception {
        sendLog(context, true);
    }

    private static void sendLog (Context context, boolean history) throws Exception {
        if (!config.isSendEmail) {
            return;
        }
        if (config.mailSubject == null) {
            config.mailSubject = "IRAINS LOG " + context.getPackageManager().getApplicationLabel(context.getApplicationInfo()) + " " + Build.DEVICE;
        }
        if(history) {
            LogSendUtil.sendHistory();
        } else {
            LogSendUtil.sendCurrent();
        }
    }

    public static void sendCurrentLog(Context context) throws Exception {
        sendLog(context, false);
    }

    /**
     * 设置配置。
     *
     * @param logConfig
     */
    public static void setConfig(LogConfig logConfig) {
        config = logConfig;
        if (logConfig.isSaveLog) {
            if (TextUtils.isEmpty(logConfig.logPath)) {
                throw new IllegalArgumentException("isSaveLog is true, but logPath is null");
            }
            File path = new File(logConfig.logPath);
            if (path.isFile()) {
                throw new IllegalArgumentException("the logPath is need to set as path, but it's a file");
            }
            if (!path.canWrite()) {
                localLog.e("the log path is not writable");
            } else {

            }
        }
        if (logConfig.isSendEmail &&
                (TextUtils.isEmpty(logConfig.sendEmailUser) ||
                        TextUtils.isEmpty(logConfig.sendEmailPassword) ||
                        TextUtils.isEmpty(logConfig.receiveEmail))) {
            throw new IllegalArgumentException("the isSendEmail is true, but send mail or send mail password, receive email is null");
        }
    }


    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param msg The message you would like logged.
     */
    public int v(String msg) {
        if (config.isSaveLog && config.saveLevel <= VERBOSE) {
            LogFileUtil.writeLog(LogFileUtil.VERBOSE, LOG_TAG, msg);
        }
        return Log.v(LOG_TAG, msg);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public int v(String msg, Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= VERBOSE) {
            LogFileUtil.writeLog(LogFileUtil.VERBOSE, LOG_TAG, msg, tr);
        }
        return Log.v(LOG_TAG, msg, tr);
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param msg The message you would like logged.
     */
    public int d(String msg) {
        if (config.isSaveLog && config.saveLevel <= DEBUG) {
            LogFileUtil.writeLog(LogFileUtil.DEBUG, LOG_TAG, msg);
        }
        return Log.d(LOG_TAG, msg);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public int d(String msg, Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= DEBUG) {
            LogFileUtil.writeLog(LogFileUtil.DEBUG, LOG_TAG, msg, tr);
        }
        return Log.d(LOG_TAG, msg, tr);
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param msg The message you would like logged.
     */
    public int i(String msg) {
        if (config.isSaveLog && config.saveLevel <= INFO) {
            LogFileUtil.writeLog(LogFileUtil.INFO, LOG_TAG, msg);
        }
        return Log.i(LOG_TAG, msg);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public int i(String msg, Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= INFO) {
            LogFileUtil.writeLog(LogFileUtil.INFO, LOG_TAG, msg, tr);
        }
        return Log.i(LOG_TAG, msg, tr);
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param msg The message you would like logged.
     */
    public int w(String msg) {
        if (config.isSaveLog && config.saveLevel <= WARN) {
            LogFileUtil.writeLog(LogFileUtil.WARN, LOG_TAG, msg);
        }
        return Log.w(LOG_TAG, msg);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public int w(String msg, Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= WARN) {
            LogFileUtil.writeLog(LogFileUtil.WARN, LOG_TAG, msg, tr);
        }
        return Log.w(LOG_TAG, msg, tr);
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     * <p/>
     * The default level of any tag is set to INFO. This means that any level above and including
     * INFO will be logged. Before you make any calls to a logging method you should check to see
     * if your tag should be logged. You can change the default level by setting a system property:
     * 'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>'
     * Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will
     * turn off all logging for your tag. You can also create a local.prop file that with the
     * following in it:
     * 'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
     * and place that in /data/local.prop.
     *
     * @param level The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23.
     */
    public boolean isLoggable(int level) {
        return Log.isLoggable(LOG_TAG, level);
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public int w(Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= WARN) {
            LogFileUtil.writeLog(LogFileUtil.WARN, LOG_TAG, tr);
        }
        return Log.w(LOG_TAG, tr);
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public int e(String msg) {
        if (config.isSaveLog && config.saveLevel <= ERROR) {
            LogFileUtil.writeLog(LogFileUtil.ERROR, LOG_TAG, msg);
        }
        return Log.e(LOG_TAG, msg);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public int e(String msg, Throwable tr) {
        if (config.isSaveLog && config.saveLevel <= ERROR) {
            LogFileUtil.writeLog(LogFileUtil.ERROR, LOG_TAG, msg, tr);
        }
        return Log.e(LOG_TAG, msg, tr);
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

}
