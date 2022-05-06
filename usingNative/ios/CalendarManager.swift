//
//  CalendatManager.swift
//  usingNative
//
//  Created by mac on 2022/04/28.
//

import Foundation
import DeviceActivity
import ScreenTime

@objc(CalendarManager)
class CalendarManager: STWebpageController{
//  @objc(addEvent:location:date:)
//  func addEvent(name: String, location: String, date: NSNumber) -> Void {
//    NSLog(name, location, date)
//  }
//
//  @objc
//  func constantsToExport() -> [String: Any]!{
//    return ["someKey": "someValue"]
//  }
  
  @objc func getNamee(_ successCallback:
  RCTResponseSenderBlock) { // Assume name comes from the any native API side
      successCallback(["SWIFT native Module"])
  }

  
}
