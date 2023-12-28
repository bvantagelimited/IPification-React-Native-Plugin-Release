package com.reactlibrarynativeipification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.ipification.mobile.sdk.android.IPEnvironment;

import com.ipification.mobile.sdk.android.CellularService;
import com.ipification.mobile.sdk.android.IPEnvironment;
import com.ipification.mobile.sdk.android.callback.CellularCallback;
import com.ipification.mobile.sdk.android.exception.CellularException;
import com.ipification.mobile.sdk.android.response.CoverageResponse;
import android.net.Uri;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;


public class RNIPEnvironmentModule extends ReactContextBaseJavaModule {
    private final Context context;
    RNIPEnvironmentModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

 
    @Override
    public String getName() {
        return "RNIPEnvironment";
    }
    
    

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("SANDBOX", "sandbox");
        constants.put("PRODUCTION", "production");
        return constants;
    }

}
