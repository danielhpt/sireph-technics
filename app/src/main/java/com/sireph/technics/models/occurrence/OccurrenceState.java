package com.sireph.technics.models.occurrence;

import static com.sireph.technics.utils.ValueFromJson.doubleFromJson;

import android.location.Location;

import androidx.annotation.NonNull;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.State;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OccurrenceState extends _BaseModel<OccurrenceState> {
    private State state;
    private Double longitude;
    private Double latitude;
    private DateTime date_time;

    public OccurrenceState(JSONObject json) {
        super(json);
        this.state = State.fromJson(json);
        this.longitude = doubleFromJson(json, "longitude", 0.0);
        this.latitude = doubleFromJson(json, "latitude", 0.0);
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

    @Override
    public ArrayList<Flag> update(@NonNull OccurrenceState updated) {
        return null;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("state", this.state.toJson());
        json.put("longitude", longitude == null ? 0.0 : longitude);
        json.put("latitude", latitude == null ? 0.0 : latitude);
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
