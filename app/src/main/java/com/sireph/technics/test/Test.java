package com.sireph.technics.test;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Test implements Serializable {
    public final String type = "technics";
    public int id;

    public Test() {
        id = -1;
    }

    public Test(JSONObject json) {
        id = json.optInt("test_id", -1);
    }

    public JSONObject toJson(@Nullable Action action, @Nullable Integer occurrenceId) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", type);
        if (id != -1) {
            json.put("test_id", id);
        }
        if (action != null) {
            json.put("action", action.toString());
        }
        if (occurrenceId != null) {
            json.put("occurrence_id", occurrenceId);
        }
        return json;
    }
}
