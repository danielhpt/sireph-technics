package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
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

    @Nullable
    public static NonTransportReason fromJson(@NonNull JSONObject json) {
        if (json.isNull("non_transport_reason")) {
            return null;
        } else {
            String value;
            try {
                value = json.getString("non_transport_reason");
            } catch (JSONException e) {
                return null;
            }
            return fromValue(value);
        }
    }

    @Nullable
    @Contract(pure = true)
    private static NonTransportReason fromValue(@NonNull String value) {
        switch (value) {
            case "Abandonou o local":
                return ABANDONED;
            case "Decisão médica":
                return MEDIC;
            case "Morte":
                return DEATH;
            case "Recusou e assinou":
                return REFUSED_SIGNED;
            case "Recusou e não assinou":
                return REFUSED_NOT_SIGNED;
            case "Desativação":
                return DEACTIVATION;
            default:
                return null;
        }
    }

    @Nullable
    @Contract(pure = true)
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
                return null;
        }
    }

    @NonNull
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
