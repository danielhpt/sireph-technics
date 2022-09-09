package com.sireph.technics.models.date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class Date extends DateTime {
    private Date(ZonedDateTime dateTime) {
        super(dateTime);
    }

    @NonNull
    @Contract("_ -> new")
    public static Date parse(String text) {
        return new Date(ZonedDateTime.parse(text + "T00:00:00Z"));
    }

    @NonNull
    @Contract(" -> new")
    public static Date now() {
        return new Date(ZonedDateTime.now());
    }

    @Nullable
    public static Date fromJson(@NonNull JSONObject json, String key) {
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
    @Override
    public String toString() {
        return format("yyyy-MM-dd");
    }
}
