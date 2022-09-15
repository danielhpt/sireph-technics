package com.sireph.technics.models;

import androidx.annotation.NonNull;

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

    public _BaseModel(@NonNull JSONObject json) {
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

    public abstract JSONObject toJson(@NonNull TypeOfJson type) throws JSONException;

    public abstract ArrayList<Flag> update(@NonNull T updated);
}
