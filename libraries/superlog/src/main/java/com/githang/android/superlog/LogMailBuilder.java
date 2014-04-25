package com.githang.android.superlog;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * User: Geek_Soledad(msdx.android@qq.com)
 * Date: 2014-04-25
 * Time: 10:54
 * FIXME
 */
public class LogMailBuilder {
    private String host;
    private String port;
    private String user;
    private String pass;
    private String from;
    private String to;
    private String subject;
    private String body;

    private LogMailBuilder(String user, String pass, String to) {
        this.user = user;
        this.pass = pass;
        this.to = to;
    }

    public static final LogMailBuilder build(String user, String pass, String to) {
        return new LogMailBuilder(user, pass, to);
    }

    public LogMailBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public LogMailBuilder setPort(String port) {
        this.port = port;
        return this;
    }

    public LogMailBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public LogMailBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    public LogMailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public LogMailBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public LogMail create() {
        if(from == null) {
            from = user;
        }
        if(body == null) {
            body = "";
        }
        return new LogMail(user, pass, from, to, host, port, subject, body);
    }

    public static class BodyBuilder {
        private static String body = null;
        public static String buildBody(Context context) {
            if (body == null) {
                StringBuilder sb = new StringBuilder();

                sb.append("APPLICATION INFORMATION").append('\n');
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = context.getApplicationInfo();
                sb.append("Application : ").append(pm.getApplicationLabel(ai)).append('\n');

                try {
                    PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
                    sb.append("Version Code: ").append(pi.versionCode).append('\n');
                    sb.append("Version Name: ").append(pi.versionName).append('\n');
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                sb.append('\n').append("DEVICE INFORMATION").append('\n');
                sb.append("Board: ").append(Build.BOARD).append('\n');
                sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append('\n');
                sb.append("BRAND: ").append(Build.BRAND).append('\n');
                sb.append("CPU_ABI: ").append(Build.CPU_ABI).append('\n');
                sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append('\n');
                sb.append("DEVICE: ").append(Build.DEVICE).append('\n');
                sb.append("DISPLAY: ").append(Build.DISPLAY).append('\n');
                sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append('\n');
                sb.append("HARDWARE: ").append(Build.HARDWARE).append('\n');
                sb.append("HOST: ").append(Build.HOST).append('\n');
                sb.append("ID: ").append(Build.ID).append('\n');
                sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append('\n');
                sb.append("PRODUCT: ").append(Build.PRODUCT).append('\n');
                sb.append("SERIAL: ").append(Build.SERIAL).append('\n');
                sb.append("TAGS: ").append(Build.TAGS).append('\n');
                sb.append("TYPE: ").append(Build.TYPE).append('\n');
                sb.append("USER: ").append(Build.USER).append('\n');

                body = sb.toString();
            }
            return body;
        }
    }
}
