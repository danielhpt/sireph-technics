package com.sireph.technics.models;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class _BaseModel {
    protected Integer id;

//    public _BaseModel() {
//        this.id = null;
//    }
//
//    public _BaseModel(int id) {
//        this.id = id;
//    }

    public _BaseModel(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract JSONObject toJson() throws JSONException;
    /*
    {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("", this.);
        return json;
    }
     */
}