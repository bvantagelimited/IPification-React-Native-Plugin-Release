import Foundation
import IPificationSDK
import UIKit
@objc(RNAuthenticationService) class RNAuthenticationService: NSObject {
    @objc static func requiresMainQueueSetup() -> Bool {return false}
    
    @objc func unregisterNetwork(){
        
    }
    @objc func startAuthorizationNoParam(_ callback:  @escaping RCTResponseSenderBlock) {
        
            let authorizationService = AuthorizationService()
            
            authorizationService.callbackSuccess = { (response) -> Void in
                callback([NSNull(), response.getCode(), response.getState(), response.getPlainResponse()])
            }
            authorizationService.callbackFailed = { (error) -> Void in
                callback([error.localizedDescription, NSNull()])
            }
            authorizationService.callbackIMCanceled = { () -> Void in
                callback([NSNull(), NSNull(), NSNull(), NSNull(), true])
            }
            let requestBuilder = AuthorizationRequest.Builder()
            requestBuilder.setScope(value: "openid")
            authorizationService.startAuthorization(requestBuilder.build())
            
    }
    
    // @objc func setAuthorizationServiceConfiguration(_ name: String){
    //     // do nothing, just sync with Android function
    // }
    
    @objc func startAuthorization(_ params: NSDictionary, cb callback:  @escaping RCTResponseSenderBlock) {
        
       
        guard let infoDictionary = params as? [String: Any] else {
            return
        }
        let keys = params.allKeys as? [String]
        //    print(keys)
        let authorizationService = AuthorizationService()
        let builder =  AuthorizationRequest.Builder()
        
        var channel = ""
        if(keys != nil){
            for key in keys! {
                if(key != "state" && key != "state" && key != "scope"){
                    if("channel" == key){
                        channel = infoDictionary[key] as! String
                    }
                    builder.addQueryParam(key: key, value: infoDictionary[key] as! String)
                }
            }
        }
        
        
        if(infoDictionary.index(forKey: "state") != nil){
            builder.setState(value: infoDictionary["state"] as! String)
        }
        if(infoDictionary.index(forKey: "scope") != nil){
            builder.setScope(value: infoDictionary["scope"] as! String)
        }
        authorizationService.callbackSuccess = { (response) -> Void in
            callback([NSNull(), response.getCode(), response.getState(), response.getPlainResponse()])
        }
        authorizationService.callbackIMCanceled = { () -> Void in
           callback([NSNull(), NSNull(), NSNull(), NSNull(), true])
        }
        authorizationService.callbackFailed = { (error) -> Void in
            callback([error.localizedDescription, NSNull()])
        }
        
        
        if(channel != "" && UIWindow.key?.rootViewController != nil){
            print("channel + viewcontroller")
            authorizationService.startAuthorization(viewController: UIWindow.key!.rootViewController!, builder.build())
        }
        else if(channel != "" && UIApplication.shared.windows.last?.rootViewController != nil){
            print("channel + viewcontroller")
            authorizationService.startAuthorization(viewController: (UIApplication.shared.windows.last?.rootViewController)!, builder.build())
        }else{
            authorizationService.startAuthorization(builder.build())
        }
        
        
    }
//    @objc func getConfigurationByName(_ name: String, resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock)-> Void{
//        var result  = infoForKey(name.uppercased())
//        resolve(result)
//    }
//    func infoForKey(_ key: String) -> String? {
//        let result = (Bundle.main.infoDictionary?[key] as? String)?
//            .replacingOccurrences(of: "\\", with: "")
//        return result
//    }
    
    @objc func updateIOSTheme(_ params: NSDictionary) {
        let toolbarTitleColor = params.value(forKey: "toolbarTitleColor") ?? ""
        let cancelBtnColor = params.value(forKey:"cancelBtnColor") ?? ""
        let titleColor = params.value(forKey:"titleColor") ?? ""
        let descColor = params.value(forKey:"descColor") ?? ""
        let backgroundColor = params.value(forKey:"backgroundColor") ?? ""
        
        IPificationTheme.sharedInstance.updateScreen(
            toolbarTitleColor: hexStringToUIColor(hex: toolbarTitleColor as! String),
            cancelBtnColor: hexStringToUIColor(hex: cancelBtnColor as! String),
            titleColor: hexStringToUIColor(hex: titleColor as! String),
            descColor: hexStringToUIColor(hex: descColor as! String),
            backgroundColor: hexStringToUIColor(hex: backgroundColor as! String))
    }
    
    @objc func updateIOSLocale(_ params: NSDictionary) {
        let titleBar = params.value(forKey: "titleBar") ?? ""
        let title = params.value(forKey: "title") ?? ""
        let description = params.value(forKey: "description") ?? ""
        let whatsappBtnText = params.value(forKey: "whatsappBtnText") ?? ""
        let viberBtnText = params.value(forKey: "viberBtnText") ?? ""
        let telegramBtnText = params.value(forKey: "telegramBtnText") ?? ""
        let cancelBtnText = params.value(forKey: "cancelBtnText") ?? ""

        IPificationLocale.sharedInstance.updateScreen(
            titleBar: titleBar as! String,
            title: title as! String,
            description: description as! String,
            whatsappBtnText: whatsappBtnText as! String,
            viberBtnText : viberBtnText as! String,
            telegramBtnText : telegramBtnText as! String,
            cancelBtnText: cancelBtnText as! String)
    }

    @objc func updateAndroidTheme(_ params: NSDictionary) {
        // do nothing
    }
    @objc func updateAndroidLocale(_ params: NSDictionary) {
        // do nothing
    }
    @objc func generateState(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) -> Void {
        resolve(IPConfiguration.sharedInstance.generateState())
    }
    
    @objc func hexStringToUIColor (hex:String) -> UIColor {
        var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()

        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }

        if ((cString.count) != 6) {
            return UIColor.gray
        }

        var rgbValue:UInt64 = 0
        Scanner(string: cString).scanHexInt64(&rgbValue)

        return UIColor(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
}

extension UIWindow {
    static var key: UIWindow? {
        if #available(iOS 13, *) {
            return UIApplication.shared.windows.first { $0.isKeyWindow }
        } else {
            return UIApplication.shared.keyWindow
        }
    }
}
