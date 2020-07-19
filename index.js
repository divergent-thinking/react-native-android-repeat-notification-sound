import { NativeModules } from 'react-native';

const { AndroidRepeatNotificationSound } = NativeModules;

export default (title = "", message = "") => {
    AndroidRepeatNotificationSound.createRepeatNotificationWithRepeatSound(title, message);
}