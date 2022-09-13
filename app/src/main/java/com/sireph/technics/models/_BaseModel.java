package com.sireph.technics.models;

import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class _BaseModel<T> implements Serializable {
    protected Integer id;

    public _BaseModel() {
        this.id = null;
    }

/*
    public _BaseModel(int id) {
        this.id = id;
    }
*/

    public _BaseModel(JSONObject json) {
        try {
            this.id = json.getInt("id");
        } catch (JSONException e) {
            this.id = null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract JSONObject toJson(TypeOfJson type) throws JSONException;
    /*
    {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("", this.);
        return json;
    }
     */

    public abstract ArrayList<Flag> update(T updated);
}
