package com.sireph.technics.models.procedures;

import com.sireph.technics.models._BaseModel;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.enums.BurnDegree;
import com.sireph.technics.models.enums.TypeOfInjury;

import org.json.JSONException;
import org.json.JSONObject;

public class Trauma extends _BaseModel {
    private Boolean closed;
    private BodyPart body_part;
    private TypeOfInjury type_of_injury;
    private BurnDegree burn_degree;

    public Trauma(JSONObject json) {
        super(json);
        this.closed = json.optBoolean("closed", false);
        this.body_part = BodyPart.fromJson(json);
        this.type_of_injury = TypeOfInjury.fromJson(json);
        this.burn_degree = BurnDegree.fromJson(json);
    }

    public Trauma(Boolean closed, BodyPart body_part, TypeOfInjury type_of_injury, BurnDegree burn_degree) {
        this.closed = closed;
        this.body_part = body_part;
        this.type_of_injury = type_of_injury;
        this.burn_degree = burn_degree;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("body_part", this.body_part.toString());
        json.put("type_of_injury", this.type_of_injury.toString());
        json.put("closed", this.closed);
        json.put("burn_degree", this.burn_degree.toString());
        return json;
    }

    public BodyPart getBody_part() {
        return body_part;
    }

    public void setBody_part(BodyPart body_part) {
        this.body_part = body_part;
    }

    public TypeOfInjury getType_of_injury() {
        return type_of_injury;
    }

    public void setType_of_injury(TypeOfInjury type_of_injury) {
        this.type_of_injury = type_of_injury;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public BurnDegree getBurn_degree() {
        return burn_degree;
    }

    public void setBurn_degree(BurnDegree burn_degree) {
        this.burn_degree = burn_degree;
    }
}
