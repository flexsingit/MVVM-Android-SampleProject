package com.mvvmsample.application;

import android.app.Application;

public class MyApplication extends Application {
    public static MyApplication getApplication() {
        return application;
    }

    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }
}
