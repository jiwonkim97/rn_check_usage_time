//
//  CalendarManagerBridge.m
//  usingNative
//
//  Created by mac on 2022/04/28.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(CalendarManager, NSObject)

//RCT_EXTERN_METHOD(addEvent: (NSString *)name location:(NSString *)location date:(nonnull NSNumber *)date)
RCT_EXTERN_METHOD(getNamee: (RCTResponseSenderBlock *)successCallback)

@end
