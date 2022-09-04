package com.sireph.technics.models.date;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time implements Serializable {
    private final ZonedDateTime dateTime;

    private Time(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static Time parse(String text) {
        return new Time(ZonedDateTime.parse(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddT")) + text + "Z"));
    }

    public static Time now() {
        return new Time(ZonedDateTime.now());
    }

    public static Time fromJson(JSONObject json, String key) {
        if (json.isNull(key)) {
            return null;
        } else {
            try {
                String text = json.getString(key);
                try {
                    return Time.parse(text);
                } catch (DateTimeParseException e) {
                    return null;
                }
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public String format(String pattern) {
        return this.dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    @NonNull
    public String toString() {
        return format("hh:mm:ss");
    }
}
