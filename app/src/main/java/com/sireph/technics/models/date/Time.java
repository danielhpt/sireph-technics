package com.sireph.technics.models.date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Time extends DateTime {
    protected Time(ZonedDateTime dateTime) {
        super(dateTime);
    }

    @NonNull
    @Contract("_ -> new")
    public static Time parse(String text) {
        return new Time(ZonedDateTime.parse(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-ddT")) + text + "Z"));
    }

    @NonNull
    @Contract(" -> new")
    public static Time now() {
        return new Time(ZonedDateTime.now());
    }

    @Nullable
    public static Time fromJson(@NonNull JSONObject json, String key) {
        if (json.isNull(key)) {
            return null;
        } else {
            try {
                String text = json.getString(key);
                try {
                    return parse(text);
                } catch (DateTimeParseException e) {
                    return null;
                }
            } catch (JSONException e) {
                return null;
            }
        }
    }

    @NonNull
    public String toString() {
        return format("hh:mm:ss");
    }
}
