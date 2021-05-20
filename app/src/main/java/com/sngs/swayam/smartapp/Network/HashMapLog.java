package com.sngs.swayam.smartapp.Network;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapLog {
    private static String TAG = HashMapLog.class.getSimpleName();

    public static void getHashMapLog(String tag, HashMap<String, String> mBodyMap) {


        Set<Map.Entry<String, String>> entrySet = mBodyMap.entrySet();
        ArrayList<Map.Entry<String, String>> listOfEntry = new ArrayList<Map.Entry<String, String>>(entrySet);

        for (Map.Entry<String, String> entry : listOfEntry) {
            Log.v(TAG, "  " + tag + " --- > " + entry.getKey() + " : " + entry.getValue());
        }
    }
}
