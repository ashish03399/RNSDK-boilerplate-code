package com.rnsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rnsample.databinding.ActivityRnfragmentBinding;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.rnandroidsdk.RNSDK;

public class RNFragmentActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private ActivityRnfragmentBinding binding;
    ReactRootView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRnfragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
                rv = RNSDK.getInstance().getRNView(RNFragmentActivity.this, getIntent().getBundleExtra("nativeBundle"), "RNModule");
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.reactNativeFragment.addView(rv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RNSDK.getInstance().onHostResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RNSDK.getInstance().onHostPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rv != null) {
            rv.unmountReactApplication();
            rv = null;
        }
        RNSDK.getInstance().onHostDestroy(this);

    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}
