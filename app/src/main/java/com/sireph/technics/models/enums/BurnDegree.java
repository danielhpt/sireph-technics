package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum BurnDegree implements Serializable {
    EMPTY(""),
    G1("G1"),
    G2("G2"),
    G3("G3");

    private final String value;

    BurnDegree(String value) {
        this.value = value;
    }

    public static BurnDegree fromJson(JSONObject json) {
        if (json.isNull("burn_degree")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("burn_degree");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static BurnDegree fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "G1":
                return G1;
            case "G2":
                return G2;
            case "G3":
                return G3;
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
