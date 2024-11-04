package dev.shirako.reain.player;

import java.util.prefs.Preferences;

public class PlayerPrefs {
    private static final Preferences prefs = Preferences.userNodeForPackage(PlayerPrefs.class);

    public static void setString(String key, String value) {
        prefs.put(key, value);
    }

    public static void setInt(String key, int value) {
        prefs.putInt(key, value);
    }

    public static void setFloat(String key, float value) {
        prefs.putFloat(key, value);
    }

    public static void setBoolean(String key, boolean value) {
        prefs.putBoolean(key, value);
    }

    public static String getString(String key) {
        return prefs.get(key, null);
    }

    public static String getString(String key, String value) {
        return prefs.get(key, value);
    }

    public static int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    public static int getInt(String key, int value) {
        return prefs.getInt(key, value);
    }

    public static float getFloat(String key) {
        return prefs.getFloat(key, 0f);
    }

    public static float getFloat(String key, float value) {
        return prefs.getFloat(key, value);
    }

    public static boolean getBoolean(String key) {
        return prefs.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean value) {
        return prefs.getBoolean(key, value);
    }
}