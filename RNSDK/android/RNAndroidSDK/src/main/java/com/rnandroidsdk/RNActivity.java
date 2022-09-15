package com.rnandroidsdk;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class RNActivity extends ReactActivity {

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Nullable
            @Override
            protected Bundle getLaunchOptions() {
                return getIntent().getBundleExtra("bundle");
            }
        };
    }

    @Override
    protected String getMainComponentName() {
        return "RNModule";
    }

}
