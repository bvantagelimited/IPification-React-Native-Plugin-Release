import Foundation
import IPificationSDK

@objc(RNCoverageService) class RNCoverageService: NSObject {
    @objc static func requiresMainQueueSetup() -> Bool {return false}
    

    
    @objc func checkCoverage(_ callback:  @escaping RCTResponseSenderBlock) {
        
        do {
            let coverageService = CoverageService()
            coverageService.callbackFailed = { (error) -> Void in
                //  print(error.localizedDescription)
                callback([error.localizedDescription, false])
            }
            coverageService.callbackSuccess = { (response) -> Void in
                //  print("check coverage result: ", response.isAvailable())
                if(response.isAvailable()){
                    callback(["", response.isAvailable() , response.getOperatorCode()])
                } else{
                    callback(["unsupported telco", response.isAvailable() , response.getOperatorCode()])
                }
            }
            try coverageService.checkCoverage()
        } catch{
            print("Unexpected error: \(error).")
            callback([error.localizedDescription, false])
        }
    }
    
    @objc func checkCoverageWithPhoneNumber(_ params: NSDictionary, cb  callback:  @escaping RCTResponseSenderBlock) {
        var phoneNumber = ""
        do {
            guard let infoDictionary = params as? [String: Any] else {
                callback(["phoneNumber cannot be empty", false])
                return
            }
            if(infoDictionary.index(forKey: "phoneNumber") != nil){
                phoneNumber = infoDictionary["phoneNumber"] as! String
            } else{
                callback(["phoneNumber cannot be empty", false])
                return
            }
            let coverageService = CoverageService()
            coverageService.callbackFailed = { (error) -> Void in
                //  print(error.localizedDescription)
                callback([error.localizedDescription, false])
            }
            coverageService.callbackSuccess = { (response) -> Void in
                //  print("check coverage result: ", response.isAvailable())
                if(response.isAvailable()){
                    callback(["", response.isAvailable() , response.getOperatorCode()])
                } else{
                    callback(["unsupported telco", response.isAvailable() , response.getOperatorCode()])
                }
            }
            try coverageService.checkCoverage(phoneNumber: phoneNumber)
        } catch{
            print("Unexpected error: \(error).")
            callback([error.localizedDescription, false])
        }
    }
    
    
}
