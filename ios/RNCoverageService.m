#import <Foundation/Foundation.h>

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

@interface RCT_EXTERN_MODULE(RNCoverageService,NSObject)

RCT_EXTERN_METHOD(checkCoverage: (RCTResponseSenderBlock)callback)
RCT_EXTERN_METHOD(checkCoverageWithPhoneNumber: (NSDictionary *)params cb:(RCTResponseSenderBlock)callback)

@end
