package com.ojk.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.google.gson.Gson;
import com.ojk.base.OjkApplication;
import com.ojk.base.entity.OjkHeadEntity;

import java.net.NetworkInterface;
import java.net.SocketException;

public class OjkPhoneUtils {

    public static String getHeadInfo() {
        OjkHeadEntity requestHeadBean = new OjkHeadEntity();
        PackageManager pm = OjkApplication.getBaseApplication().getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(OjkApplication.getBaseApplication().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                requestHeadBean.brand = Build.MANUFACTURER;
                requestHeadBean.deviceModel = Build.MODEL;
                requestHeadBean.bag = pi.packageName;
                requestHeadBean.operationSys = "android";
                requestHeadBean.appVersion = versionName;
                requestHeadBean.osVersion = Build.VERSION.RELEASE;
                requestHeadBean.mac = getMacAddress();
                requestHeadBean.udid = getAndroidId(OjkApplication.getBaseApplication());
                requestHeadBean.channel = OjkSPUtils.getChannel(OjkApplication.getBaseApplication());
                requestHeadBean.advertising_id = OjkSPUtils.getGoogleId(OjkApplication.getBaseApplication());
            }
            return new Gson().toJson(requestHeadBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getMacAddress() {
        String macAddress = "";
        NetworkInterface networkInterface = null;

        try {
            if (NetworkInterface.getByName("eth0") != null) {
                networkInterface = NetworkInterface.getByName("eth0");
            } else if (NetworkInterface.getByName("wlan0") != null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (networkInterface == null) {
            return macAddress;
        }
        byte[] macArr = new byte[0];
        try {
            macArr = networkInterface.getHardwareAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : macArr) {
            buf.append(String.format("%02X", b));
        }
        macAddress = buf.toString();
        return macAddress;
    }

}
