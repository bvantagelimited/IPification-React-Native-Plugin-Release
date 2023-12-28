package com.reactlibrarynativeipification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;


import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.ipification.mobile.sdk.android.IPConfiguration;
import com.ipification.mobile.sdk.android.CellularService;
import com.ipification.mobile.sdk.android.IPificationServices;
import com.ipification.mobile.sdk.android.callback.CellularCallback;
import com.ipification.mobile.sdk.android.callback.IPificationCallback;
import com.ipification.mobile.sdk.android.exception.CellularException;
import com.ipification.mobile.sdk.android.exception.IPificationError;
import com.ipification.mobile.sdk.android.request.AuthRequest;
import com.ipification.mobile.sdk.android.response.AuthResponse;
import com.ipification.mobile.sdk.im.IMLocale;
import com.ipification.mobile.sdk.im.IMService;
import com.ipification.mobile.sdk.im.IMTheme;


import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RNAuthenticationServiceModule extends ReactContextBaseJavaModule {
    private Context context;
    RNAuthenticationServiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
            // Add the listener for `onActivityResult`
        reactContext.addActivityEventListener(mActivityEventListener);

    }

    @Override
    public String getName() {
        return "RNAuthenticationService";
    }


    @ReactMethod
    public void startAuthorizationNoParam(ReadableMap params, final Callback callback) {
        Log.d("DEBUG", "startAuthorizationNoParam");
        doAuthWith(params, new IPificationCallback() {
            @Override
            public void onSuccess(AuthResponse authResponse) {
                Log.d("DEBUG", "onSuccess " + authResponse.getCode());
                callback.invoke(null, authResponse.getCode(), authResponse.getState(), authResponse.getResponseData());
            }

            @Override
            public void onError(IPificationError e) {
                Log.d("DEBUG", "onError " + e.getErrorMessage());
                callback.invoke(e.getErrorMessage(), null);
            }
            @Override
            public void onIMCancel() {
                callback.invoke(null, null, null, null, true);
            }
        });


    }
    @ReactMethod
    public void startAuthorization(ReadableMap params, final Callback callback) {
        Log.d("DEBUG", "startAuthorization");
        doAuthWith(params, new IPificationCallback() {
            @Override
            public void onSuccess(AuthResponse authResponse) {
                Log.d("DEBUG", "onSuccess " + authResponse.getCode());
                callback.invoke(null, authResponse.getCode(), authResponse.getState(), authResponse.getResponseData());
            }

            @Override
            public void onError(IPificationError e) {
                Log.d("DEBUG", "onError " + e.getErrorMessage());
                callback.invoke(e.getErrorMessage(), null);
            }
            @Override
            public void onIMCancel() {
                callback.invoke(null, null, null, null, true);
            }
        });


    }
    @ReactMethod
    public void unregisterNetwork() {
        CellularService.Companion.unregisterNetwork(this.context);
    }


    // sub function
    private void doAuthWith(ReadableMap params, final IPificationCallback cb) {
        AuthRequest.Builder authRequestBuilder = new AuthRequest.Builder();
        try {
            String channel = "";
            HashMap data = params.toHashMap();
            for(Object key : data.keySet()){
                Log.d("DEBUG","map: " + key + " "+data.get(key));
                if(!key.toString().equals("state") && !key.toString().equals("scope")){
                    authRequestBuilder.addQueryParam(key.toString(), data.get(key).toString());
                    if(key.toString().equals("channel")){
                        channel = data.get(key).toString();
                    }
                } else if(key.toString().equals("state")){
                    authRequestBuilder.setState(data.get(key).toString());
                } else if(key.toString().equals("scope")){
                    authRequestBuilder.setScope(data.get(key).toString());
                }               
            }

            AuthRequest authRequest = authRequestBuilder.build();
            if(!channel.equals("") && getCurrentActivity() != null){
                IPificationServices.Factory.startAuthentication(getCurrentActivity(), authRequest, cb);
            }else{
                CellularService<AuthResponse> doAuthService = new CellularService<>(context);
                doAuthService.performAuth(authRequest, new CellularCallback<AuthResponse>() {
                    @Override
                    public void onSuccess(AuthResponse authResponse) {
                        cb.onSuccess(authResponse);
                    }

                    @Override
                    public void onError(@NotNull CellularException error) {
                        cb.onError(new IPificationError(new Exception(error.getErrorMessage())));
                    }
                    @Override
                    public void onIMCancel() {
                    }
                });
            }
        }catch(Exception e){
            cb.onError(new IPificationError(e));
        }

    }

    private ActivityEventListener mActivityEventListener = new ActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == IPConfiguration.getInstance().getREQUEST_CODE()) {
               IMService.Factory.onActivityResult(requestCode, resultCode, data);
            }
        }
        @Override
        public void onNewIntent(Intent intent){

        }


    };
    @ReactMethod
    public void updateAndroidTheme(ReadableMap params){
        try{
            String backgroundColor = params.getString("backgroundColor");
            String toolbarTextColor = params.getString("toolbarTextColor");
            String toolbarColor = params.getString("toolbarColor");
            IPificationServices.Factory.setTheme(new IMTheme(Color.parseColor(backgroundColor), Color.parseColor(toolbarTextColor), Color.parseColor(toolbarColor)));
        }catch (Exception ex){
            Log.e("RNAuthentication", "updateAndroidTheme error: " +ex.getLocalizedMessage());
        }
    }
    @ReactMethod
    public void updateAndroidLocale(ReadableMap params){
        try{
            String mainTitle = !params.isNull("mainTitle") ?  params.getString("mainTitle") : "";
            String description = !params.isNull("description") ?  params.getString("description") : "";
            String whatsappText = !params.isNull("whatsappBtnText") ?  params.getString("whatsappBtnText") : "";
            String telegramText = !params.isNull("telegramBtnText") ? params.getString("telegramBtnText") : "";
            String viberText = !params.isNull("viberBtnText") ?  params.getString("viberBtnText") : "";
            String toolbarText = !params.isNull("toolbarText") ?  params.getString("toolbarText") : "";
            IPificationServices.Factory.setLocale(new IMLocale( mainTitle, description, whatsappText, telegramText, viberText , toolbarText, View.VISIBLE));

        }catch (Exception ex){
            Log.e("RNAuthentication", "updateAndroidLocale error: "+ ex.getLocalizedMessage());
        }
    }
    @ReactMethod
    public void updateIOSLocale(ReadableMap params){
        //do nothing
    }
    @ReactMethod
    public void updateIOSTheme(ReadableMap params){
        //do nothing
    }

    @ReactMethod
    public void generateState(Promise p){
        p.resolve(IPConfiguration.getInstance().generateState());
    }
    
}
