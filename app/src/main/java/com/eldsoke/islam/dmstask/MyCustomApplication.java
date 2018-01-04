package com.eldsoke.islam.dmstask;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

/**
 * Created by islam on 1/4/2018.
 */

public class MyCustomApplication extends Application {

    private static Context context;

    public static File getPath() {
        File file = context.getCacheDir();
        if (!file.exists()) {
            boolean make = file.mkdir();
        }
        return file;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }
}
