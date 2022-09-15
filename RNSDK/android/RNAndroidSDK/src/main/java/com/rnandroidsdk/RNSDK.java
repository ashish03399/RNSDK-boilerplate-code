
package com.rnandroidsdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactFragment;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;


import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class RNSDK {
    private static RNSDK instance;

    public RNBridge RNBridge;
    private WeakReference<Activity> myActivityReference; // do not leak memory
    private WeakReference<Activity> cfFragmentActivityReference; // do not leak memory
    public static ReactNativeHost mReactNativeHost;

    public RNBridge getRNBridge() {
        return RNBridge;
    }

    public void setRNBridge(RNBridge RNBridge) {
        this.RNBridge = RNBridge;
    }


    public ReactNativeHost getReactNativeHost(Application applicationContext) {
        mReactNativeHost = new ReactNativeHost(applicationContext) {

            @Override
            public boolean getUseDeveloperSupport() {
                return BuildConfig.DEBUG;
            }

            protected List<ReactPackage> getPackages() {
                List<ReactPackage> packages = new PackageList(this).getPackages();
                packages.add(new RNBridge());
                return packages;
            }

            @Override
            protected String getJSMainModuleName() {
                return "index";
            }


        };

        return mReactNativeHost;
    }

    public static RNSDK getInstance() {
        if (instance == null) {
            instance = new RNSDK();
        }
        return instance;
    }

    public void startCFActivity() {
        if (cfFragmentActivityReference.get() == null) {
            Log.e("MySDK", "activity can't be null. returning.");
            return;
        }
        Intent intent = new Intent(cfFragmentActivityReference.get(), RNActivity.class);
        cfFragmentActivityReference.get().startActivity(intent);
    }

    public void startCFActivity(Activity activity, Bundle bundle) {
        this.myActivityReference = new WeakReference<Activity>(activity);
        if (activity == null) {
            Log.e("MySDK", "activity can't be null. returning.");
            return;
        }
        Intent intent = new Intent(activity, RNActivity.class);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
    }

    public Fragment getRNFragment(Activity activity, Bundle bundle, String registeredComponent) {
        this.cfFragmentActivityReference = new WeakReference<Activity>(activity);

        Fragment reactNativeFragment = new ReactFragment.Builder()
                .setComponentName(registeredComponent)
                .setLaunchOptions(bundle)
                .build();
        return reactNativeFragment;
    }

    public void onHostResume(Activity activity) {
        ReactNativeHost rnc = ((ReactApplication) activity.getApplication()).getReactNativeHost();
        rnc.getReactInstanceManager().onHostResume(activity, (DefaultHardwareBackBtnHandler) activity);
    }

    public void onHostPause(Activity activity) {
        ReactNativeHost rnc = ((ReactApplication) activity.getApplication()).getReactNativeHost();
        rnc.getReactInstanceManager().onHostPause(activity);
    }

    public void onHostDestroy(Activity activity) {
        ReactNativeHost rnc = ((ReactApplication) activity.getApplication()).getReactNativeHost();
        rnc.getReactInstanceManager().onHostDestroy(activity);
    }

    public ReactRootView getRNView(Activity activity, Bundle bundle, String registeredComponent) throws Exception {
        this.cfFragmentActivityReference = new WeakReference<Activity>(activity);
        ReactRootView mReactRootView = new ReactRootView(activity);
        ReactNativeHost rnc = ((ReactApplication) activity.getApplication()).getReactNativeHost();
        ReactInstanceManager mReactInstanceManager = rnc.getReactInstanceManager();

        mReactRootView.startReactApplication(mReactInstanceManager, registeredComponent, bundle);

        return mReactRootView;
    }

    public void sendEvent(String eventName, @Nullable WritableMap params) {
        RNBridge.sendEvent(eventName, params);
    }

    // TODO - Ashish Need to review from native team
    public static Activity getActivity() throws Exception {
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);

        Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
        if (activities == null)
            return null;

        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                return (Activity) activityField.get(activityRecord);
            }
        }

        return null;
    }

}
