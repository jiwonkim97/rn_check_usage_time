import React, {useState, useRef, useEffect} from 'react';
import {
  SafeAreaView,
  Text,
  Button,
  Platform,
} from 'react-native';

import { NativeModules, AppState } from 'react-native';
import BackgroundTimer from 'react-native-background-timer';

const UsageStat = NativeModules.UsageStatModule;
const CalendarModule = NativeModules.CalendarModule;
const CalendarManager = NativeModules.CalendarManager;

const App = () => {
  const appState = useRef(AppState.currentState);
  const [appStateVisible, setAppStateVisible] = useState(appState.current);
  
  useEffect(() => {
    const subscription = AppState.addEventListener('change', nextAppState => {
      if(appState.current.match(/inactive|background/) && nextAppState === "active"){
        console.log("App has come to the foreground!");
        StopBackgroundTimer();
      }
      else if(appState.current === "active" && nextAppState.match(/inactive|background/)){
        StartBackgroundTimer();
      }
      appState.current = nextAppState;
      setAppStateVisible(appState.current);
      console.log("AppState", appState.current);
    });

    return () => {
      subscription.remove();
    }
  }, []);

  componentDidMount = async () => {
    AppState.addEventListener('change', this.handleAppStateChange);
  }

  const androidOnPress = () => {
    UsageStat.getAppUsageStats().then(data => {
      let arr = data.split('#$%^&')
      arr = arr.map(c => c.split('&^%$#'))
      arr.sort((a, b) => b[1] - a[1])
      arr.forEach(c => {
        if (c[1] > 0){
          console.log('App Name   :',c[0], ' \nScreen Time:', c[1] / 1000,'Seconds')
        }
      })
    })
  }

  const iosOnPress = () => {
    console.log("iosTest")
    //CalendarModule.createCalendarEvent('Birthday Party', '4 Privet Drive, Surrey');
    // CalendarModule.createCalendarEventCallback(
    //   'testName',
    //   'testLocation',
    //   (error, eventId) => {
    //     if (error) {
    //       console.error(`Error found! ${error}`);
    //     }
    //     console.log(`event id ${eventId} returned`);
    //   }
    // );
    // CalendarModule.appUsageData(
    //   (error, eventId) => {
    //     if (error) {
    //       console.error(`Error found! ${error}`);
    //     }
    //     console.log(`event id ${eventId} returned`);
    //   }
    // );
    CalendarManager.getNamee(name => console.log(name))
    //CalendarManager.addEvent('Birthday Party', '4 Privet Drive, Surrey', Date.now())
  }

  const StartBackgroundTimer = () =>{
    console.log('===Start Background Timer===');
    BackgroundTimer.runBackgroundTimer(() => {
      CheckForegroundApp();
      UsageStat.isScreenOn().then(data =>{
        if(!data){
          console.log("screen off!")
          StopBackgroundTimer();
        }
      })
    }, 1000)
  }
  const StopBackgroundTimer = () => {
    console.log('===Stop Background Timer===');
    BackgroundTimer.stopBackgroundTimer();
  }

  const CheckForegroundApp = () => {
    UsageStat.getForegroundAppName().then(data => {
      console.log(data)
    })
  }

  const onPress = () => {
    Platform.OS === 'ios' ?
      iosOnPress()
      :
      androidOnPress()
  }
  return (
    <SafeAreaView >
      <Button
        title="Click to invoke your native module!"
        color="#841584"
        onPress={onPress}
      />
      <Button
        title="Test / Start BackgroundTimer"
        color="#1ff384"
        // onPress={StartBackgroundTimer}
      />
      <Button
        title="Test / Stop BackgroundTimer"
        color="#2ff348"
        // onPress={StopBackgroundTimer}
      />
      <Text>
        Current state is: {appStateVisible}
      </Text>
    </SafeAreaView>
  );
};

export default App;
