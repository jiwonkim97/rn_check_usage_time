package com.usingnative;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

import android.widget.Toast;
import android.util.Log;
import android.util.LongSparseArray;
import android.app.usage.UsageEvents;
import android.app.usage.UsageEvents.Event;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.os.PowerManager;

import java.util.Calendar;
import java.util.List;

public class UsageStatModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public UsageStatModule(ReactApplicationContext reactContext){
        super(reactContext);
        this.reactContext = reactContext;
    }
    //Context context;

    @Override
    public String getName() {
        return "UsageStatModule";  // Name of the Native Modules.
    }

    // @ReactMethod
    // public void show(Promise promise){
    //     Calendar cal = Calendar.getInstance();
    //     promise.resolve((int)cal.getTimeInMillis());
    // }

    @ReactMethod
    private void getAppUsageStats(Promise promise){
        Context context = reactContext;
        Calendar cal = Calendar.getInstance();
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.YEAR, -1);    // 1
        long startTime = cal.getTimeInMillis();

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        PackageManager packagemanager = context.getPackageManager();

        String testData = "";
        for (UsageStats us : usList){
            String pn = us.getPackageName();
            //ApplicationInfo appInfo = packagemanager.getApplicationInfo(pn, packagemanager.ApplicationInfoFlags.of());
            //String appLabel = packagemanager.getApplicationLabel(appinfo).toString();

            //testData += appLabel;
            testData += pn;//packagemanager.getApplicationLabel(appInfo).toString();
            testData += "&^%$#";
            testData += Long.toString(us.getTotalTimeInForeground());
            testData += "#$%^&";
        }
        promise.resolve(testData);
    }
    @ReactMethod
    private static void getForegroundAppName(Promise promise){
        Context context = reactContext;
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");


        long lastRunAppTimeStamp = 0L;

        final long INTERVAL = 1000 * 60;
        final long end = System.currentTimeMillis();
        // 1 minute ago
        final long begin = end - INTERVAL;

        LongSparseArray packageNameMap = new LongSparseArray<>();
        final UsageEvents usageEvents = usm.queryEvents(begin, end);
        while (usageEvents.hasNextEvent()){
            UsageEvents.Event event = new UsageEvents.Event();
            usageEvents.getNextEvent(event);
        
            if(isForeGroundEvent(event)) {
            packageNameMap.put(event.getTimeStamp(), event.getPackageName());
                if(event.getTimeStamp() > lastRunAppTimeStamp) {
                    lastRunAppTimeStamp = event.getTimeStamp();
                }
            }
        }
        promise.resolve(packageNameMap.get(lastRunAppTimeStamp, ""));
        //return packageNameMap.get(lastRunAppTimeStamp, "");
    }
    private static boolean isForeGroundEvent(UsageEvents.Event event) {
        if(event == null){
            return false;
        }

        if(BuildConfig.VERSION_CODE >= 29){
            return event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED;

        }

        return event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND;
    }

    @ReactMethod
    private void isScreenOn(Promise promise){
        Context context = reactContext;
        PowerManager pm = (PowerManager) context.getSystemService("power");
        promise.resolve(pm.isInteractive());
    }

    @ReactMethod
    private void getIntent(Promise promise){
        
    }
}