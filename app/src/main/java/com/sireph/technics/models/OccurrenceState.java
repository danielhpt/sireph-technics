package com.sireph.technics.models;

import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;

import android.location.Location;

import com.google.android.gms.tasks.Task;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.State;

import org.json.JSONException;
import org.json.JSONObject;

public class OccurrenceState extends _BaseModel {
    private State state;
    private Double longitude;
    private Double latitude;
    private DateTime date_time;
    private boolean waiting = false;

    public OccurrenceState(JSONObject json) {
        super(json);
        this.state = State.fromJson(json);
        this.longitude = doubleFromJson(json, "longitude", null);
        this.latitude = doubleFromJson(json, "latitude", null);
        this.date_time = DateTime.fromJson(json, "date_time");
    }

    public OccurrenceState(State state, Location location, DateTime date_time) {
        this.state = state;
        if (location != null) {
            this.longitude = location.getLongitude();
            this.latitude = location.getLatitude();
        }
        this.date_time = date_time;
    }

    public OccurrenceState(State state, Task<Location> locationTask, DateTime dateTime) {
        this.waiting = true;
        this.state = state;
        this.date_time = dateTime;
        locationTask.addOnSuccessListener(location -> {
            this.longitude = location.getLongitude();
            this.latitude = location.getLatitude();
        }).addOnCompleteListener(task -> this.waiting = false);
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

    public DateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(DateTime date_time) {
        this.date_time = date_time;
    }
}
