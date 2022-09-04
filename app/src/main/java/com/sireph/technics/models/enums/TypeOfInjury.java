package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum TypeOfInjury implements Serializable {
    FRACTURE("#"),
    CONTUSION("C"),
    WOUND("F"),
    HAEMORRHAGE("H"),
    BURN("Q"),
    PAIN("D");

    private final String type_of_injury;

    TypeOfInjury(String type_of_injury) {
        this.type_of_injury = type_of_injury;
    }

    public static TypeOfInjury fromJson(JSONObject json) {
        if (json.isNull("type_of_injury")) {
            return null;
        } else {
            String value;
            try {
                value = json.getString("type_of_injury");
            } catch (JSONException e) {
                return null;
            }
            return fromValue(value);
        }
    }

    public static TypeOfInjury fromValue(String value) {
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
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return type_of_injury;
    }
}
