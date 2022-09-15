package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Hospital extends _BaseModel<Hospital> {
    private final String name;
    private final String address;
    private final Integer capacity;
    private final Integer current_capacity;
    private final Integer contact;
    private final String image_url;

    public Hospital(JSONObject json) {
        super(json);
        this.name = stringFromJson(json, "name", "");
        this.address = stringFromJson(json, "address", "");
        this.capacity = intFromJson(json, "capacity", null);
        this.current_capacity = intFromJson(json, "current_capacity", null);
        this.contact = intFromJson(json, "contact", null);
        this.image_url = stringFromJson(json, "image_url", "");
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("address", this.address);
        json.put("capacity", this.capacity == null ? JSONObject.NULL : capacity);
        json.put("current_capacity", this.current_capacity == null ? JSONObject.NULL : current_capacity);
        json.put("contact", this.contact == null ? JSONObject.NULL : contact);
        json.put("image_url", this.image_url);
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull Hospital updated) {
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
