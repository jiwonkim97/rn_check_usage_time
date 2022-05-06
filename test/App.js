import React from 'react';
import { //NativeModules, 
  SafeAreaView ,Button } from 'react-native';
//const { UsageStats } = NativeModules;

const NewModuleButton = () => {
  // const onPress = () => {
  //   ToastExample.show('testName', ToastExample.SHORT);
  // };
  // UsageStats.testToast(UsageStats.Short);
  return (
    <SafeAreaView>
      <Button
      title="Click to invoke your native module!"
      color="#841584"
      //onPress={onPress}
    />
    </SafeAreaView>
  );
};

export default NewModuleButton;