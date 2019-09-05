package com.example.dream.retrofitrxjavaokhttpdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by lipeng on 6/8/15.
 */
public class SharedPreferencesUtil {

    private static final ThreadLocal<SharedPreferences> threadLocal = new ThreadLocal<SharedPreferences>();

    private static final Object object = new Object();

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        SharedPreferences sharedPreferences = threadLocal.get();
        if (sharedPreferences == null) {
            synchronized (object) {
                if (sharedPreferences == null) {
                    sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
                    threadLocal.set(sharedPreferences);
                }
            }
        }
        return sharedPreferences;
    }

    public static SharedPreferences.Editor getEditor(Context context, String name) {
        return getSharedPreferences(context, name).edit();
    }

    public static void remove(Context context, String name, String key) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.remove(key);
        editor.commit();
    }

    public static void putSerializable(Context context, String name, String key, Serializable serializable) {
        SharedPreferences.Editor editor = getEditor(context, name);
        try {
            editor.putString(key, SerializableUtil.serialize(serializable));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    public static Serializable getSerializable(Context context, String name, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(context, name);
        String serializableStr = sharedPreferences.getString(key, null);
        try {
            return SerializableUtil.deSerialization(serializableStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context, name);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String name, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context, name);
        boolean value = sharedPreferences.getBoolean(key, defValue);
        return value;
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context, "wenlin");
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context, "wenlin");
        String value = sharedPreferences.getString(key, defValue);
        return value;
    }

    public static int getInt(Context context, String name, String key, int defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context, name);
        int value = sharedPreferences.getInt(key, defValue);
        return value;
    }

}
