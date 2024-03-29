package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum TypeOfInjury implements Serializable {
    EMPTY(""),
    FRACTURE("#"),
    CONTUSION("C"),
    WOUND("F"),
    HAEMORRHAGE("H"),
    BURN("Q"),
    PAIN("D");

    private final String value;

    TypeOfInjury(String value) {
        this.value = value;
    }

    public static TypeOfInjury fromJson(JSONObject json) {
        if (json.isNull("type_of_injury")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("type_of_injury");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static TypeOfInjury fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "#":
                return FRACTURE;
            case "C":
                return CONTUSION;
            case "F":
                return WOUND;
            case "H":
                return HAEMORRHAGE;
            case "Q":
                return BURN;
            case "D":
                return PAIN;
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
