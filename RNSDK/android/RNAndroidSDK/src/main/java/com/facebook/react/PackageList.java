package com.facebook.react;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;

import java.util.ArrayList;
import java.util.Arrays;

public class PackageList {
    private Application application;
    private ReactNativeHost reactNativeHost;
    private MainPackageConfig mConfig;

    public PackageList(ReactNativeHost reactNativeHost) {
        this((ReactNativeHost)reactNativeHost, (MainPackageConfig)null);
    }

    public PackageList(Application application) {
        this((Application)application, (MainPackageConfig)null);
    }

    public PackageList(ReactNativeHost reactNativeHost, MainPackageConfig config) {
        this.reactNativeHost = reactNativeHost;
        this.mConfig = config;
    }

    public PackageList(Application application, MainPackageConfig config) {
        this.reactNativeHost = null;
        this.application = application;
        this.mConfig = config;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.reactNativeHost;
    }

    private Resources getResources() {
        return this.getApplication().getResources();
    }

    private Application getApplication() {
        return this.reactNativeHost == null ? this.application : this.reactNativeHost.getApplication();
    }

    private Context getApplicationContext() {
        return this.getApplication().getApplicationContext();
    }

    public ArrayList<ReactPackage> getPackages() {
        return new ArrayList(Arrays.asList(new MainReactPackage(this.mConfig)));
    }
}
