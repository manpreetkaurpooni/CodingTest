package com.manpreet.westpactest.util;

import android.content.Context;


/**
 * Created by manpreet on 9/12/15.
 */
public class AppUtils {

    private static Context context;

    public static void setContext(Context ctx) {
        context = ctx;
    }

    public static Context getContext() {
        return context;
    }
}
