package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum TypeOfTransport implements Serializable {
    PRIMARY(1, "Primário"),
    SECONDARY(2, "Secundário"),
    NO_TRANSPORT(3, "Não Transporte");

    private final int id;
    private final String value;

    TypeOfTransport(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Nullable
    public static TypeOfTransport fromJson(@NonNull JSONObject json) {
        if (json.isNull("type_of_transport")) {
            return null;
        } else {
            String value;
            try {
                value = json.getString("type_of_transport");
            } catch (JSONException e) {
                return null;
            }
            return fromValue(value);
        }
    }

    @Nullable
    @Contract(pure = true)
    private static TypeOfTransport fromValue(@NonNull String value) {
        switch (value) {
            case "Primário":
                return PRIMARY;
            case "Secundário":
                return SECONDARY;
            case "Não Transporte":
                return NO_TRANSPORT;
            default:
                return null;
        }
    }

    @Nullable
    @Contract(pure = true)
    public static TypeOfTransport fromId(int id) {
        switch (id) {
            case 1:
                return PRIMARY;
            case 2:
                return SECONDARY;
            case 3:
                return NO_TRANSPORT;
            default:
                return null;
        }
    }

    @NonNull
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("type_of_transport", this.value);
        return json;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
