import React, { useEffect } from "react";
import { AppRegistry, View, Text } from 'react-native';
import { NativeModules } from 'react-native';

const RNComp = props => {
    console.log('props from native', props);

    useEffect(()=>{
      NativeModules.RNBridge.sendDataToNative("RN Comp Rendered");
    }, []);

    return (
        <View style={ { flex: 1, backgroundColor: '#ffffff',  width: 100, height: 200 } }>
            <Text> RN SDK View </Text>
        </View>
    );
};

AppRegistry.registerComponent("RNModule", () => RNComp);
