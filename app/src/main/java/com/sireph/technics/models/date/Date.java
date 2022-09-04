package com.sireph.technics.models.date;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date implements Serializable {
    private final ZonedDateTime dateTime;

    private Date(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static Date parse(String text) {
        return new Date(ZonedDateTime.parse(text + "T00:00:00Z"));
    }

    public static Date now() {
        return new Date(ZonedDateTime.now());
    }

    public static Date fromJson(JSONObject json, String key) {
        if (json.isNull(key)) {
            return null;
        } else {
            try {
                String text = json.getString(key);
                try {
                    return Date.parse(text);
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
        return format("yyyy-MM-dd");
    }
}
