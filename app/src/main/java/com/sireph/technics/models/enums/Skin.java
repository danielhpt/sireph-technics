package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum Skin implements Serializable {
    EMPTY(""),
    NORMAL("Normal"),
    PALE("Pálida"),
    CYANOSED("Cianosada"),
    DRY("Seca"),
    ;

    private final String value;

    Skin(String value) {
        this.value = value;
    }

    public static Skin fromJson(JSONObject json) {
        if (json.isNull("skin")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("skin");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static Skin fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "Normal":
                return NORMAL;
            case "Pálida":
                return PALE;
            case "Cianosada":
                return CYANOSED;
            case "Seca":
                return DRY;
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
