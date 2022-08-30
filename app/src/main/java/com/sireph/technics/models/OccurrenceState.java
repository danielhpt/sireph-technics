package com.sireph.technics.models;

import android.location.Location;

import com.sireph.technics.models.enums.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class OccurrenceState extends _BaseModel {
    private State state;
    private double longitude;
    private double latitude;
    private LocalDateTime date_time;

    public OccurrenceState(JSONObject json) throws JSONException {
        super(json);
        int state_id;
        try {
            state_id = json.getJSONObject("state").getInt("id");
        } catch (JSONException e) {
            state_id = json.getInt("state");
        }
        this.state = State.fromId(state_id);
        this.longitude = (float) json.getDouble("longitude");
        this.latitude = (float) json.getDouble("latitude");
        this.date_time = LocalDateTime.parse(json.getString("date_time"));
    }

    public OccurrenceState(State state, Location location, LocalDateTime date_time) {
        this.state = state;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.date_time = date_time;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("state", this.state.toJson());
        json.put("longitude", this.longitude);
        json.put("latitude", this.latitude);
        json.put("date_time", this.date_time.toString());
        return json;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }
}
