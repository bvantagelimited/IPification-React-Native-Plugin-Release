import Foundation
import IPificationSDK

@objc(RNCoverageService) class RNCoverageService: NSObject {
    @objc static func requiresMainQueueSetup() -> Bool {return false}
    

    
    @objc func checkCoverage(_ callback:  @escaping RCTResponseSenderBlock) {
        
        
        let coverageService = CoverageService()
        coverageService.callbackFailed = { (error) -> Void in
            //          print(error.localizedDescription)
            callback([error.localizedDescription, NSNull()])
        }
        coverageService.callbackSuccess = { (response) -> Void in
            //           print("check coverage result: ", response.isAvailable())
            callback([NSNull(), response.isAvailable(), response.getOperatorCode])
        }
        
        coverageService.checkCoverage()
    }
    
    @objc func checkCoverageWithPhoneNumber(_ params: NSDictionary, cb  callback:  @escaping RCTResponseSenderBlock) {
        var phoneNumber = ""
        do {
            guard let infoDictionary = params as? [String: Any] else {
                return
            }
            if(infoDictionary.index(forKey: "phoneNumber") != nil){
                phoneNumber = infoDictionary["phoneNumber"] as! String
            }
            let coverageService = CoverageService()
            coverageService.callbackFailed = { (error) -> Void in
                //          print(error.localizedDescription)
                callback([error.localizedDescription, NSNull()])
            }
            coverageService.callbackSuccess = { (response) -> Void in
                //           print("check coverage result: ", response.isAvailable())
                callback([NSNull(), response.isAvailable(), response.getOperatorCode()])
            }
            try coverageService.checkCoverage(phoneNumber: phoneNumber)
        } catch{
            print("Unexpected error: \(error).")
            callback([error.localizedDescription, NSNull()])
        }
    }
    
    
}
