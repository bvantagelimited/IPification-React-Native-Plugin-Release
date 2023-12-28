#import <Foundation/Foundation.h>

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

@interface RCT_EXTERN_MODULE(RNIPConfiguration,NSObject)

RCT_EXTERN_METHOD(setConfigFileName:(NSString *)name)

RCT_EXTERN_METHOD(getClientId: (RCTPromiseResolveBlock)resolve
                            rejecter:(RCTPromiseRejectBlock)reject
                 )                   
RCT_EXTERN_METHOD(getRedirectUri: (RCTPromiseResolveBlock)resolve
                            rejecter:(RCTPromiseRejectBlock)reject
                )
RCT_EXTERN_METHOD(getCheckCoverageUrl: (RCTPromiseResolveBlock)resolve
            rejecter:(RCTPromiseRejectBlock)reject
)
RCT_EXTERN_METHOD(getAuthorizationUrl: (RCTPromiseResolveBlock)resolve
                            rejecter:(RCTPromiseRejectBlock)reject
)

RCT_EXTERN_METHOD(setClientId: (NSString *)value)
RCT_EXTERN_METHOD(setRedirectUri: (NSString *)value)
RCT_EXTERN_METHOD(setCheckCoverageUrl: (NSString *)value)
RCT_EXTERN_METHOD(setAuthorizationUrl: (NSString *)value)
RCT_EXTERN_METHOD(setENV: (NSString *)value)
RCT_EXTERN_METHOD(getENV: (RCTPromiseResolveBlock)resolve
            rejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(enableValidateIMApps: (BOOL)value)

@end
