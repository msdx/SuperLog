package com.githang.android.superlog;
/**
 * Created by msdx on 2014/4/22.
 */

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-04-22
 * Time: 00:13
 * Config for SuperLog.
 */
public class LogConfig {
    /**
     * E-mail address to receive log.
     */
    public String receiveEmail;
    /**
     * E-mail user
     */
    public String sendEmailUser;
    /**
     * E-mail password to send Log.
     */
    public String sendEmailPassword;
    /**
     * E-mail port to login.
     */
    public String sendEmailPort = "25";
    /**
     * E-mail host to send.
     */
    public String sendEmailHost;
    /**
     * Mail subject.
     */
    public String mailSubject;
    /**
     * Mail body.
     */
    public String mailBody;
    /**
     * Directory path.
     */
    public String logPath;
    /**
     * Is need to send E-mail.
     */
    public boolean isSendEmail;
    /**
     * Is need to save log to file.
     */
    public boolean isSaveLog;
    /**
     * If need to delete log file after send.
     */
    public boolean deleteFileAfterSend;


    public int saveLevel = SuperLog.DEBUG;
}

