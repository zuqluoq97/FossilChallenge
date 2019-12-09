package com.ltdung.fossilsofchallenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Dung Luong on 09/12/2019
 */
public final class NetworkUtils {

    private NetworkUtils(){

    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }
        return false;
    }
}
