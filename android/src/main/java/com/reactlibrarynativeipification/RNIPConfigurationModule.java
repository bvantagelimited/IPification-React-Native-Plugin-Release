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
import com.ipification.mobile.sdk.android.IPConfiguration;
import com.ipification.mobile.sdk.android.callback.CellularCallback;
import com.ipification.mobile.sdk.android.exception.CellularException;
import com.ipification.mobile.sdk.android.response.CoverageResponse;
import android.net.Uri;
import org.jetbrains.annotations.NotNull;

public class RNIPConfigurationModule extends ReactContextBaseJavaModule {
    private final Context context;
    RNIPConfigurationModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

 
    @Override
    public String getName() {
        return "RNIPConfiguration";
    }
    
    /*
    * Deprecated
    */
    @ReactMethod
    public void setConfigFileName(String fileName, Promise p) {
        IPConfiguration.getInstance().setConfigFileName(fileName);
    }

    @ReactMethod
    public void getClientId(Promise p) {
        p.resolve(IPConfiguration.getInstance().getCLIENT_ID());
    }

    @ReactMethod
    public void getRedirectUri(Promise p) {
        if(IPConfiguration.getInstance().getREDIRECT_URI() != null){
            p.resolve(IPConfiguration.getInstance().getREDIRECT_URI().toString());
        }else{
            p.resolve("redirect_uri is null");
        }
       
    }
    /*
    * Deprecated
    */
    @ReactMethod
    public void getCheckCoverageUrl(Promise p) {
        if(IPConfiguration.getInstance().getCOVERAGE_URL() != null){
            p.resolve(IPConfiguration.getInstance().getCOVERAGE_URL().toString());
        }else{
            p.resolve("coverage_url is null");
        }
        
    }

    /*
    * Deprecated
    */
    @ReactMethod
    public void getAuthorizationUrl(Promise p) {
        if(IPConfiguration.getInstance().getAUTHORIZATION_URL() != null){
            p.resolve(IPConfiguration.getInstance().getAUTHORIZATION_URL().toString());
        }else{
            p.resolve("authorization_url is null");
        }
        
    }

    @ReactMethod
    public void setClientId(String clientId, Promise p) {
        IPConfiguration.getInstance().setSDK_TYPE_VALUE("react-native");
        IPConfiguration.getInstance().setCLIENT_ID(clientId);
    }

    @ReactMethod
    public void setRedirectUri(String redirectUri, Promise p) {
        IPConfiguration.getInstance().setREDIRECT_URI(Uri.parse(redirectUri));
    }

    /*
    * Deprecated
    */
    @ReactMethod
    public void setCheckCoverageUrl(String coverageUrl) {
        IPConfiguration.getInstance().setCustomUrls(true);
        IPConfiguration.getInstance().setCOVERAGE_URL(Uri.parse(coverageUrl));
    }

    /*
    * Deprecated
    */
    @ReactMethod
    public void setAuthorizationUrl(String authenticationUrl) {
        IPConfiguration.getInstance().setCustomUrls(true);
        IPConfiguration.getInstance().setAUTHORIZATION_URL(Uri.parse(authenticationUrl));
    }

    @ReactMethod
    public void setENV(String env) {
        if(env.equals("production")){
            IPConfiguration.getInstance().setENV(IPEnvironment.PRODUCTION);
        }
        else {
            IPConfiguration.getInstance().setENV(IPEnvironment.SANDBOX);
        }
        
    }

    @ReactMethod
    public void getENV(Promise p) {
        if(IPEnvironment.PRODUCTION == IPConfiguration.getInstance().getENV()){
            p.resolve("production");
        }else{
            p.resolve("sandbox");
        }
        
    }

    @ReactMethod
    public void enableValidateIMApps(boolean value) {
        IPConfiguration.getInstance().setValidateIMApps(value);
    }

}
