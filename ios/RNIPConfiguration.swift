import Foundation
import IPificationSDK
import UIKit
@objc(RNIPConfiguration) class RNIPConfiguration: NSObject {
    @objc static func requiresMainQueueSetup() -> Bool {return false}

    @objc func getClientId(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
        var result  = IPConfiguration.sharedInstance.CLIENT_ID
        resolve(result)
    }

    @objc func getCheckCoverageUrl(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
        var result  = IPConfiguration.sharedInstance.COVERAGE_URL
        resolve(result)
    }

    @objc func getAuthorizationUrl(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
        var result  = IPConfiguration.sharedInstance.AUTHORIZATION_URL
        resolve(result)
    }

    @objc func getRedirectUri(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
        var result  = IPConfiguration.sharedInstance.REDIRECT_URI
        resolve(result)
    }

    @objc func setClientId(_ value: String)-> Void{
        IPConfiguration.sharedInstance.CLIENT_ID = value
    }

    @objc func setRedirectUri(_ value: String)-> Void{
        IPConfiguration.sharedInstance.REDIRECT_URI = value
    }

    @objc func setCheckCoverageUrl(_ value: String)-> Void{
        IPConfiguration.sharedInstance.customUrls = true
        IPConfiguration.sharedInstance.COVERAGE_URL = value
    }

    @objc func setAuthorizationUrl(_ value: String)-> Void{
        IPConfiguration.sharedInstance.customUrls = true
        IPConfiguration.sharedInstance.AUTHORIZATION_URL = value
    }
    
    @objc func setENV(_ value: String)-> Void{
        if(value == "production"){
            IPConfiguration.sharedInstance.ENV = IPEnvironment.PRODUCTION
        }else{
            IPConfiguration.sharedInstance.ENV = IPEnvironment.SANDBOX
        }
    }
    @objc func getENV(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
        if(IPConfiguration.sharedInstance.ENV == IPEnvironment.PRODUCTION){
            resolve("production")
        }else{
            resolve("sandbox")
        }
    }

    @objc func enableValidateIMApps(_ value: Bool)-> Void{
        IPConfiguration.sharedInstance.validateIMApps = value
    }

    // do nothing , for android only
    @objc func setConfigFileName(_ name: String)-> Void{
    }
}
