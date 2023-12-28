package com.reactlibrarynativeipification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

import com.ipification.mobile.sdk.android.CellularService;
import com.ipification.mobile.sdk.android.IPConfiguration;
import com.ipification.mobile.sdk.android.callback.CellularCallback;
import com.ipification.mobile.sdk.android.exception.CellularException;
import com.ipification.mobile.sdk.android.response.CoverageResponse;

import org.jetbrains.annotations.NotNull;

public class RNCoverageServiceModule extends ReactContextBaseJavaModule {
    private final Context context;
    RNCoverageServiceModule(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

 
    @Override
    public String getName() {
        return "RNCoverageService";
    }

    @ReactMethod
    public void checkCoverage(final Callback callback) {
        Log.d("DEBUG", "library checkCoverage");
        checkCoverage(new CellularCallback<CoverageResponse>() {
            @Override
            public void onSuccess(CoverageResponse coverageResponse) {
                Log.d("DEBUG", "onSuccess " + coverageResponse.isAvailable());
                if(coverageResponse.isAvailable()){
                    callback.invoke("", coverageResponse.isAvailable(), coverageResponse.getOperatorCode());
                }else{
                    callback.invoke("unsupported telco", coverageResponse.isAvailable(), coverageResponse.getOperatorCode());
                }
            }

            @Override
            public void onError(CellularException e) {
                Log.d("DEBUG", "library onError " + e.getErrorMessage());
                callback.invoke(e.getErrorMessage(), false);
            }
            @Override
            public void onIMCancel() {
            }
        });


    }
    @ReactMethod
    public void checkCoverageWithPhoneNumber(String phoneNumber, final Callback callback) {
        Log.d("DEBUG", "library checkCoverage");
        if(phoneNumber.equals("")){
            callback.invoke("phone number cannot be empty", false);
            return;
        }
        checkCoverage(phoneNumber, new CellularCallback<CoverageResponse>() {
            @Override
            public void onSuccess(CoverageResponse coverageResponse) {
                Log.d("DEBUG", "onSuccess " + coverageResponse.isAvailable());
                if(coverageResponse.isAvailable()){
                    callback.invoke("", coverageResponse.isAvailable(), coverageResponse.getOperatorCode());
                }else{
                    callback.invoke("unsupported telco", coverageResponse.isAvailable(), coverageResponse.getOperatorCode());
                }
            }

            @Override
            public void onError(CellularException e) {
                Log.d("DEBUG", "library onError " + e.getErrorMessage());
                callback.invoke(e.getErrorMessage(), false);
            }
            @Override
            public void onIMCancel() {
            }
        });


    }

 
  

    private void checkCoverage(CellularCallback<CoverageResponse> callback) {
        CellularService<CoverageResponse> checkCoverageService = new CellularService<>(this.context);
        checkCoverageService.checkCoverage(callback);
    }

    private void checkCoverage(String phoneNumber, CellularCallback<CoverageResponse> callback) {
        CellularService<CoverageResponse> checkCoverageService = new CellularService<>(this.context);
        checkCoverageService.checkCoverage(phoneNumber, callback);
    }
    



}
