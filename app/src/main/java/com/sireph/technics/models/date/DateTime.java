package com.sireph.technics.models.date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime implements Serializable {
    protected ZonedDateTime dateTime;

    protected DateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @NonNull
    @Contract("_ -> new")
    public static DateTime parse(String text) {
        return new DateTime(ZonedDateTime.parse(text));
    }

    @NonNull
    @Contract(" -> new")
    public static DateTime now() {
        return new DateTime(ZonedDateTime.now());
    }

    @Nullable
    public static DateTime fromJson(@NonNull JSONObject json, String key) {
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DateTime) {
            return dateTime.isEqual((((DateTime) obj).dateTime));
        }
        return false;
    }

    public void setTime(@NonNull String time) {
        String[] split = time.split(":");
        this.dateTime = this.dateTime.withHour(Integer.parseInt(split[0])).withMinute(Integer.parseInt(split[1]));
        if (this.dateTime.isAfter(ZonedDateTime.now())) {
            this.dateTime = this.dateTime.minusDays(1);
        }
    }

    public String format(String pattern) {
        return this.dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    @NonNull
    public String toString() {
        return format("yyyy-MM-dd") + "T" + format("HH:mm:ss") + "Z";
    }
}
