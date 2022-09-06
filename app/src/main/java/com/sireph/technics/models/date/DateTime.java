package com.sireph.technics.models.date;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime implements Serializable {
    private ZonedDateTime dateTime;

    private DateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static DateTime parse(String text) {
        return new DateTime(ZonedDateTime.parse(text));
    }

    public static DateTime now() {
        return new DateTime(ZonedDateTime.now());
    }

    public static DateTime fromJson(JSONObject json, String key) {
        if (json.isNull(key)) {
            return null;
        } else {
            try {
                String text = json.getString(key);
                try {
                    return DateTime.parse(text);
                } catch (DateTimeParseException e) {
                    return null;
                }
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public void setTime(String time) {
        String[] split = time.split(":");
        this.dateTime.withHour(Integer.parseInt(split[0]));
        this.dateTime.withMinute(Integer.parseInt(split[1]));
        if (this.dateTime.isAfter(ZonedDateTime.now())) {
            this.dateTime = this.dateTime.minusDays(1);
        }
    }

    public String format(String pattern) {
        return this.dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    @NonNull
    public String toString() {
        return this.dateTime.toString();
    }
}
