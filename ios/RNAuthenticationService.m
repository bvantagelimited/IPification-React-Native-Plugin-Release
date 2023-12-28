#import <Foundation/Foundation.h>

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

@interface RCT_EXTERN_MODULE(RNAuthenticationService,NSObject)
RCT_EXTERN_METHOD(unregisterNetwork)
RCT_EXTERN_METHOD(startAuthorizationNoParam: (RCTResponseSenderBlock)callback)
RCT_EXTERN_METHOD(startAuthorization: (NSDictionary *)params cb:(RCTResponseSenderBlock)callback )


RCT_EXTERN_METHOD(updateIOSLocale: (NSDictionary *)params)
RCT_EXTERN_METHOD(updateIOSTheme: (NSDictionary *)params)

RCT_EXTERN_METHOD(updateAndroidLocale: (NSDictionary *)params)
RCT_EXTERN_METHOD(updateAndroidTheme: (NSDictionary *)params)

RCT_EXTERN_METHOD(generateState: (RCTPromiseResolveBlock)resolve
                            rejecter:(RCTPromiseRejectBlock)reject)


@end
