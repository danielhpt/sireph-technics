package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum AVDS implements Serializable {
    A("A"),
    V("V"),
    D("D"),
    S("S"),
    EMPTY("");

    private final String avds;

    AVDS(String avds) {
        this.avds = avds;
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
        return avds;
    }
}
