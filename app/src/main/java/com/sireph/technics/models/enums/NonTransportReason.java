package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum NonTransportReason implements Serializable {
    ABANDONED(1, "Abandonou o local"),
    MEDIC(2, "Decisão médica"),
    DEATH(3, "Morte"),
    REFUSED_SIGNED(4, "Recusou e assinou"),
    REFUSED_NOT_SIGNED(5, "Recusou e não assinou"),
    DEACTIVATION(6, "Desativação");

    private final int id;
    private final String value;

    NonTransportReason(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static NonTransportReason fromJson(JSONObject json) {
        if (json.isNull("non_transport_reason")) {
            return null;
        } else {
            int id;
            try {
                id = json.getInt("non_transport_reason");
            } catch (JSONException e) {
                try {
                    id = json.getJSONObject("non_transport_reason").getInt("id");
                } catch (JSONException ex) {
                    return null;
                }
            }
            return fromId(id);
        }
    }

    public static NonTransportReason fromId(int id) {
        switch (id) {
            case 1:
                return ABANDONED;
            case 2:
                return MEDIC;
            case 3:
                return DEATH;
            case 4:
                return REFUSED_SIGNED;
            case 5:
                return REFUSED_NOT_SIGNED;
            case 6:
                return DEACTIVATION;
            default:
                throw new IllegalArgumentException();
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("non_transport_reason", this.value);
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
