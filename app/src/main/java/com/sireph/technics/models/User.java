package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class User extends _BaseModel {
    private final String username;
    private final String first_name;
    private final String last_name;
    private final String email;

    public User(JSONObject json) {
        super(json);
        this.username = stringFromJson(json, "username", "");
        this.first_name = stringFromJson(json, "first_name", "");
        this.last_name = stringFromJson(json, "last_name", "");
        this.email = stringFromJson(json, "email", "");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("username", this.username);
        json.put("first_name", this.first_name);
        json.put("last_name", this.last_name);
        json.put("email", this.email);
        return json;
    }

    public String getFullName() {
        if (Objects.equals(first_name, "")) {
            return username;
        } else if (Objects.equals(last_name, "")) {
            return first_name;
        } else {
            return first_name + " " + last_name;
        }
    }

    public String getEmail() {
        if (email == null || email.equals("")) {
            return username;
        }
        return email;
    }

    public String getUsername() {
        return username;
    }
}
