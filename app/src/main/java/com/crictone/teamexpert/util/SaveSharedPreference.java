package com.crictone.teamexpert.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.crictone.teamexpert.util.Utility.LOGGED_IN_PREF;
import static com.crictone.teamexpert.util.Utility.USER_NAME;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     *
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }
    public static void saveUserName(Context context,String username) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(USER_NAME, username);
        editor.apply();
    }
    public static String getUserName(Context context, String username) {
        return getPreferences(context).getString(USER_NAME,username);
    }

    /**
     * Get the Login Status
     *
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}
