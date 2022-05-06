//
//  RCTCalendarModule.m
//  usingNative
//
//  Created by mac on 2022/04/28.
//

#import <Foundation/Foundation.h>
#import "RCTCalendarModule.h"
#import <React/RCTLog.h>

@implementation RCTCalendarModule

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(createCalendarEventCallback:(NSString *)title location:(NSString *)location myCallback:(RCTResponseSenderBlock)callback)
{
  NSNumber *eventId = [NSNumber numberWithInt:123];
  callback(@[[NSNull null], eventId]);
  
  RCTLogInfo(@"Pretending to create an event %@ at %@", title, location);
}

RCT_EXPORT_METHOD(appUsageData: (RCTResponseSenderBlock)callback)
{
  callback(@[[NSNull null],[NSNumber numberWithInt:1234]]);
}

@end
