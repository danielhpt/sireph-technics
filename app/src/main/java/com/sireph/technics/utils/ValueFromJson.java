package com.sireph.technics.utils;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class ValueFromJson {
    public static String stringFromJson(@NonNull JSONObject json, String key, String fallback) {
        if (json.isNull(key)) {
            return fallback;
        }
        return json.optString(key, fallback);
    }

    public static Integer intFromJson(@NonNull JSONObject json, String key, Integer fallback) {
        if (json.isNull(key)) {
            return fallback;
        }
        int i = json.optInt(key, -1);
        return i == -1 ? fallback : i;
    }

    public static Double doubleFromJson(@NonNull JSONObject json, String key, Double fallback) {
        if (json.isNull(key)) {
            return fallback;
        }
        double v = json.optDouble(key, -1);
        return v == -1 ? fallback : v;
    }

    public static Boolean boolFromJson(@NonNull JSONObject json, String key, Boolean fallback) {
        if (json.isNull(key)) {
            return fallback;
        }
        return json.optBoolean(key, fallback);
    }
}
