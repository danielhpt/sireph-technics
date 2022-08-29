package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Symptom extends _BaseModel {
    private String comments;
    private String image_path;

    public Symptom(JSONObject json) throws JSONException {
        super(json);
        this.comments = json.getString("comments");
        this.image_path = json.getString("image_path");
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("comments", this.comments);
        json.put("image_path", this.image_path);
        return json;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
