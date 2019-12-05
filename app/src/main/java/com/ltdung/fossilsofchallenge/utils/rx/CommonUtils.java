package com.ltdung.fossilsofchallenge.utils.rx;

import android.content.Context;
import android.widget.Toast;

public class CommonUtils {
    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static void showQuickToast(Context context, String mess){
        Toast.makeText(context, mess, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String mess){
        Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
    }
}
