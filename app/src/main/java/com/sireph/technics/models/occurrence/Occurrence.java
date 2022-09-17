package com.sireph.technics.models.occurrence;

import static com.sireph.technics.utils.ValueFromJson.intFromJson;
import static com.sireph.technics.utils.ValueFromJson.stringFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models.Central;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.victim.Victim;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Occurrence extends _BaseModel<Occurrence> {
    private final Team team;
    private final Central central;
    private final int occurrence_number;
    private final Integer created_by;
    private final String gps_coordinates;
    private final boolean alert_mode;
    private final DateTime created_on;
    private int number_of_victims;
    private String local;
    private String parish;
    private String municipality;
    private String entity;
    private String mean_of_assistance;
    private String motive;
    private boolean active;
    private List<OccurrenceState> states;
    private List<Victim> victims;

    public Occurrence(JSONObject json, Team team, Technician technician) throws JSONException {
        super(json);

        this.occurrence_number = intFromJson(json, "occurrence_number", null);
        this.motive = stringFromJson(json, "motive", "");
        this.number_of_victims = intFromJson(json, "number_of_victims", null);
        this.local = stringFromJson(json, "local", "");
        this.entity = stringFromJson(json, "entity", "");
        this.mean_of_assistance = stringFromJson(json, "mean_of_assistance", "");
        this.gps_coordinates = stringFromJson(json, "gps_coordinates", "");
        this.parish = stringFromJson(json, "parish", "");
        this.municipality = stringFromJson(json, "municipality", "");
        this.active = json.optBoolean("active", false);
        this.alert_mode = json.optBoolean("alert_mode", false);
        this.created_by = intFromJson(json, "created_by", null);
        this.created_on = DateTime.fromJson(json, "created_on");

        this.states = new ArrayList<>();
        try {
            JSONArray states = json.getJSONArray("states");
            for (int i = 0; i < states.length(); i++) {
                this.states.add(new OccurrenceState(states.getJSONObject(i)));
            }
        } catch (JSONException e) {
            this.states = new ArrayList<>();
        }

        this.victims = new ArrayList<>();
        try {
            JSONArray victims = json.getJSONArray("victims");
            for (int i = 0; i < victims.length(); i++) {
                this.victims.add(new Victim(victims.getJSONObject(i)));
            }
        } catch (JSONException e) {
            this.victims = new ArrayList<>();
        }

        if (team != null && team.getId() == json.getJSONObject("team").getInt("id")) {
            this.team = team;
        } else {
            this.team = new Team(json.getJSONObject("team"), technician);
        }
        if (this.team.getCentral().getId() == json.getJSONObject("central").getInt("id")) {
            this.central = this.team.getCentral();
        } else {
            this.central = new Central(json.getJSONObject("central"));
        }
    }

    @Override
    public ArrayList<Flag> update(@NonNull Occurrence updated) {
        ArrayList<Flag> flags = new ArrayList<>();
        if (this.id == null && updated.id != null) {
            this.id = updated.id;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.number_of_victims, updated.number_of_victims)) {
            this.number_of_victims = updated.number_of_victims;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.local, updated.local)) {
            this.local = updated.local;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.parish, updated.parish)) {
            this.parish = updated.parish;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.municipality, updated.municipality)) {
            this.municipality = updated.municipality;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.entity, updated.entity)) {
            this.entity = updated.entity;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.mean_of_assistance, updated.mean_of_assistance)) {
            this.mean_of_assistance = updated.mean_of_assistance;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (!Objects.equals(this.motive, updated.motive)) {
            this.motive = updated.motive;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (this.active != updated.active) {
            this.active = updated.active;
            flags.add(Flag.UPDATED_OCCURRENCE);
        }
        if (this.states.size() < updated.states.size()) {
            this.states = updated.states;
            flags.add(Flag.ADDED_STATE);
        }
        if (this.victims.size() < updated.victims.size()) {
            flags.add(Flag.ADDED_VICTIM);
            if (!Objects.equals(this.number_of_victims, this.victims.size())) {
                this.number_of_victims = this.victims.size();
                flags.add(Flag.UPDATED_OCCURRENCE);
            }
        }
        this.victims = updated.victims;
        return flags;
    }

    @NonNull
    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("occurrence_number", this.occurrence_number);
        json.put("entity", Objects.equals(entity, "") ? JSONObject.NULL : entity);
        json.put("mean_of_assistance", Objects.equals(mean_of_assistance, "") ? JSONObject.NULL : mean_of_assistance);
        json.put("motive", Objects.equals(motive, "") ? JSONObject.NULL : motive);
        json.put("number_of_victims", this.number_of_victims);
        json.put("local", Objects.equals(local, "") ? JSONObject.NULL : local);
        json.put("gps_coordinates", Objects.equals(gps_coordinates, "") ? JSONObject.NULL : gps_coordinates);
        json.put("parish", Objects.equals(parish, "") ? JSONObject.NULL : parish);
        json.put("municipality", Objects.equals(municipality, "") ? JSONObject.NULL : municipality);
        json.put("active", this.active);
        json.put("alert_mode", this.alert_mode);
        json.put("created_on", created_on == null ? JSONObject.NULL : created_on.toString());
        switch (type) {
            case NORMAL:
                json.put("created_by", this.created_by == null ? JSONObject.NULL : created_by);
                json.put("team", team.getId());
                json.put("central", central.getId());
                break;
            case DETAIL:
                json.put("id", id == null ? JSONObject.NULL : id);
                json.put("team", team.toJson(type));
                json.put("central", central.toJson(type));
        }
        return json;
    }

    public int getOccurrence_number() {
        return occurrence_number;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getMean_of_assistance() {
        return mean_of_assistance;
    }

    public void setMean_of_assistance(String mean_of_assistance) {
        this.mean_of_assistance = mean_of_assistance;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public int getNumber_of_victims() {
        return number_of_victims;
    }

    public void setNumber_of_victims(int number_of_victims) {
        this.number_of_victims = number_of_victims;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DateTime getCreated_on() {
        return created_on;
    }

    public List<OccurrenceState> getStates() {
        return states;
    }

    public void addState(OccurrenceState state) {
        this.states.add(state);
    }

    public List<Victim> getVictims() {
        return victims;
    }

    public void addVictim(Victim victim) {
        this.victims.add(victim);
        this.number_of_victims++;
    }

    public Team getTeam() {
        return this.team;
    }
}
