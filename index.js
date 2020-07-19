import { NativeModules } from 'react-native';

const { AndroidRepeatNotificationSound } = NativeModules;

export default (title = "", message = "", channelID = "") => {
    AndroidRepeatNotificationSound.createRepeatNotificationWithRepeatSound(title, message, channelID);
}