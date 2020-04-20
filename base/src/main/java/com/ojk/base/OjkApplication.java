package com.ojk.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.OnAttributionChangedListener;
import com.adjust.sdk.OnDeviceIdsRead;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import androidx.multidex.MultiDex;

public class OjkApplication extends Application {

    private static OjkApplication ojkApplication;

    public static OjkApplication getBaseApplication() {
        if (ojkApplication == null) {
            ojkApplication = new OjkApplication();
        }
        return ojkApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        ojkApplication = this;

        FacebookSdk.setApplicationId("1090527414623249");
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);

        String appToken = "nc14t7dofjsw";
        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {

            }
        });
        Adjust.getGoogleAdId(this, new OnDeviceIdsRead() {
            @Override
            public void onGoogleAdIdRead(String googleAdId) {

            }
        });

    }

    private static final class AdjustLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }
    }

}
