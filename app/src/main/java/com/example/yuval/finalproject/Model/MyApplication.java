package com.example.yuval.finalproject.Model;

import android.app.Application;
import android.content.Context;

/**
 * Created by Yuval on 14/06/2017.
 */

public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getMyContext(){return context;}
}
