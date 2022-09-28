package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum State implements Serializable {
    WAY_VICTIM(1, "A caminho da vítima"),
    ARRIVAL_VICTIM(2, "Chegada à vítima"),
    WAY_HOSPITAL(3, "A caminho do hospital"),
    ARRIVAL_HOSPITAL(4, "Chegada ao hospital"),
    END(5, "Fim da ocorrência");

    private final int id;
    private final String value;

    State(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static State fromJson(JSONObject json) {
        if (json.isNull("state")) {
            return null;
        } else {
            int id;
            try {
                id = json.getInt("state");
            } catch (JSONException e) {
                try {
                    id = json.getJSONObject("state").getInt("id");
                } catch (JSONException ex) {
                    return null;
                }
            }
            return fromId(id);
        }
    }

    public static State fromId(int id) {
        switch (id) {
            case 1:
                return WAY_VICTIM;
            case 2:
                return ARRIVAL_VICTIM;
            case 3:
                return WAY_HOSPITAL;
            case 4:
                return ARRIVAL_HOSPITAL;
            case 5:
                return END;
            default:
                throw new IllegalArgumentException();
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("state", this.value);
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
