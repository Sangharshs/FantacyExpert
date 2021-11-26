package com.crictone.teamexpert.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utility {
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static final String USER_NAME = "username";
    public static String updateTimeRemaining(long j) {
        if (j <= 0) {
            return "Time's up!!";
        }
        int i = ((int) (j / 1000)) % 60;
        int i2 = (int) ((j / 60000) % 60);
        int i3 = (int) ((j / 3600000) % 24);
        int i4 = (int) (j / 86400000);
        if (i4 > 0) {
            return String.format(Locale.getDefault(), "%02d days", new Object[]{Integer.valueOf(i4)});
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", new Object[]{Integer.valueOf(i3 + (i4 * 24)), Integer.valueOf(i2), Integer.valueOf(i)});
    }

    public static String MinitTimeRemaining(long j) {
        if (j <= 0) {
            return "Time's up!!";
        }
        int i = (int) ((j / 60000) % 60);
        return String.format(Locale.getDefault(), "%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(((int) (j / 1000)) % 60)});
    }

    public static long convertTimeInMillis(String str, String str2) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(str2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
