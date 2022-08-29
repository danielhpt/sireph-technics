package com.sireph.technics.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends _BaseModel {
    private final String username;
    private final String first_name;
    private final String last_name;
    private final String email;

    public User(JSONObject json) throws JSONException {
        super(json);
        this.username = json.getString("username");
        this.first_name = json.getString("first_name");
        this.last_name = json.getString("last_name");
        this.email = json.getString("email");
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

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }

    public String getEmail() {
        return email;
    }
}
