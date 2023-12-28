package com.reactlibrarynativeipification;

import androidx.annotation.NonNull;
import android.net.Uri;
import android.view.View;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNIPificationServicePackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNAuthenticationServiceModule(reactContext));
        modules.add(new RNCoverageServiceModule(reactContext));
        modules.add(new RNIPConfigurationModule(reactContext));
        modules.add(new RNIPEnvironmentModule(reactContext));
        modules.add(new RNIPNotificationModule(reactContext));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
