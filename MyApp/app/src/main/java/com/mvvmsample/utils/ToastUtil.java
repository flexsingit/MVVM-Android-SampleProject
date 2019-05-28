package com.mvvmsample.utils;

import android.content.Context;
import android.widget.Toast;



public class ToastUtil {

    /**
     * The purpose of this method to show short toast message
     * @param context
     * @param message
     */
    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
