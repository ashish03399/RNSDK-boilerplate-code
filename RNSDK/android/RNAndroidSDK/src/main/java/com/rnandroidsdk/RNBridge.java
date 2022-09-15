package com.rnandroidsdk;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ViewManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNBridge extends ReactContextBaseJavaModule implements ReactPackage {
    private final static String TAG = "ConsumerFinanceBridge";
    private static WeakReference<ReactApplicationContext> reactApplicationContextWeakReference;

    public RNBridge(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContextWeakReference = new WeakReference<ReactApplicationContext>(reactApplicationContext);
    }

    public RNBridge() {
        super();
    }

    /**
     * sendEvent from Java to React
     * */
    public static void sendEvent(String eventName, @Nullable WritableMap params) {
        if(reactApplicationContextWeakReference == null || reactApplicationContextWeakReference.get() == null) {
            Log.d(TAG, "Sending event to react failed because reactContext is null. eventName: " + eventName);
            return;
        }
        reactApplicationContextWeakReference.get()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);


    }

    @ReactMethod
        public void sendDataToNative(String data){
            Log.d("RNBridge Native Logs", data);
        };


    /**
     * TODO: Ashish will remove this
     * Showing toast
     * */
    public static void showToast(String text) {
        Toast.makeText(reactApplicationContextWeakReference.get(),text,Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNBridge(reactContext));
        return modules;
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @ReactMethod
    public void addListener(String eventName) {
        // Keep: Required for RN built in Event Emitter Calls.
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public String getName() {
        return "RNBridge";
    }
}
