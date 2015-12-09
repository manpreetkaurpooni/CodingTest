package com.manpreet.westpactest.util;

import android.content.pm.PackageManager;

/**
 * Created by manpreet on 9/12/15.
 */
public class PermissionUtil {

    public static boolean permissionGranted(int[] grantResults) {
        if(grantResults.length < 1){
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
