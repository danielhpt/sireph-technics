package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum Pupils implements Serializable {
    EMPTY(""),
    NORMAL("Normais"),
    MIDRIASIS("Midríase"),
    MIOSIS("Miose"),
    ANISOCORIA("Anisocoria");

    private final String value;

    Pupils(String value) {
        this.value = value;
    }

    public static Pupils fromJson(JSONObject json) {
        if (json.isNull("pupils")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("pupils");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static Pupils fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "Normais":
                return NORMAL;
            case "Midríase":
                return MIDRIASIS;
            case "Miose":
                return MIOSIS;
            case "Anisocoria":
                return ANISOCORIA;
            case "":
            case "null":
                return EMPTY;
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
