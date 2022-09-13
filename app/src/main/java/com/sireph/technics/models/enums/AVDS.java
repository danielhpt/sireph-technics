package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum AVDS implements Serializable {
    EMPTY(""),
    A("A"),
    V("V"),
    D("D"),
    S("S");

    private final String value;

    AVDS(String value) {
        this.value = value;
    }

    public static AVDS fromJson(JSONObject json) {
        if (json.isNull("avds")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("avds");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static AVDS fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "A":
                return A;
            case "V":
                return V;
            case "D":
                return D;
            case "S":
                return S;
            default:
                return EMPTY;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
