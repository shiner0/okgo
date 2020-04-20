package com.ojk.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class OjkSPUtils {


    public static SharedPreferences share(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ojk", Context.MODE_PRIVATE);
        return sharedPreferences;
    }


    public static void clearSp(Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putString("token", "");
        e.putString("phone", "");
        e.putString("uid", "");
        e.apply();
    }


    public static boolean getPrivacy(Context context) {
        return share(context).getBoolean("privacy", false);
    }

    public static void setPrivacy(boolean value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putBoolean("privacy", value);
        e.apply();
    }


    public static long getStartTime(Context context) {
        return share(context).getLong("startTime", 0);
    }

    public static void setStartTime(long value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putLong("startTime", value);
        e.apply();
    }


    public static String getToken(Context context) {
        return share(context).getString("token", "");
    }

    public static void setToken(String value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putString("token", value);
        e.apply();
    }

    public static String getPhone(Context context) {
        return share(context).getString("phone", "");
    }

    public static void setPhone(String value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putString("phone", value);
        e.apply();
    }

    public static String getChannel(Context context) {
        return share(context).getString("channel", "googleplay");
    }

    public static void setChannel(String value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putString("channel", value);
        e.apply();
    }

    public static String getGoogleId(Context context) {
        return share(context).getString("googleId", "");
    }

    public static void setGoogleId(String value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putString("googleId", value);
        e.apply();
    }


    public static long getUid(Context context) {
        return share(context).getLong("uid", 0);
    }

    public static void setUid(long value, Context context) {
        SharedPreferences.Editor e = share(context).edit();
        e.putLong("uid", value);
        e.apply();
    }



}
