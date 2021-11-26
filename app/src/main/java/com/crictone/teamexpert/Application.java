package com.crictone.teamexpert;

import android.content.Context;

public class Application extends android.app.Application {
    public static Context mContext;
    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext=this;
//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .setNotificationOpenedHandler(new NotificationOpenedHandler())
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
    }

//    public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
//    {
//        @Override
//        public void notificationOpened(OSNotificationOpenResult result)
//        {
//           startActivity(new Intent(mContext,MainActivity.class));
//        }
//    }
}
