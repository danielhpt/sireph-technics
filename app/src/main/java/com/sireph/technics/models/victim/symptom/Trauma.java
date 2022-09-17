package com.sireph.technics.models.victim.symptom;

import static com.sireph.technics.utils.ValueFromJson.boolFromJson;

import androidx.annotation.NonNull;

import com.sireph.technics.models._BaseTableModel;
import com.sireph.technics.models.enums.BodyPart;
import com.sireph.technics.models.enums.BurnDegree;
import com.sireph.technics.models.enums.TypeOfInjury;
import com.sireph.technics.table.components.Cell;
import com.sireph.technics.utils.statics.Flag;
import com.sireph.technics.utils.statics.TypeOfJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Trauma extends _BaseTableModel<Trauma> {
    private boolean closed;
    private BodyPart body_part;
    private TypeOfInjury type_of_injury;
    private BurnDegree burn_degree;

    public Trauma(JSONObject json) {
        super(json);
        this.closed = boolFromJson(json, "closed", false);
        this.body_part = BodyPart.fromJson(json);
        this.type_of_injury = TypeOfInjury.fromJson(json);
        this.burn_degree = BurnDegree.fromJson(json);
    }

    public Trauma(boolean closed, BodyPart body_part, TypeOfInjury type_of_injury, BurnDegree burn_degree) {
        this.closed = closed;
        this.body_part = body_part;
        this.type_of_injury = type_of_injury;
        this.burn_degree = burn_degree;
    }

    @NonNull
    @Override
    public List<Cell> toCellList() {
        List<Cell> cells = new ArrayList<>();
        String mark = closed ? "O" : "X";
        cells.add(new Cell(body_part.toString()));
        cells.add(new Cell(type_of_injury == TypeOfInjury.FRACTURE ? mark : ""));
        cells.add(new Cell(type_of_injury == TypeOfInjury.CONTUSION ? mark : ""));
        cells.add(new Cell(type_of_injury == TypeOfInjury.WOUND ? mark : ""));
        cells.add(new Cell(type_of_injury == TypeOfInjury.HAEMORRHAGE ? mark : ""));
        cells.add(new Cell(type_of_injury == TypeOfInjury.BURN ? mark : ""));
        cells.add(new Cell(type_of_injury == TypeOfInjury.PAIN ? mark : ""));
        if (type_of_injury == TypeOfInjury.BURN) {
            switch (burn_degree) {
                case G1:
                    cells.add(new Cell("1"));
                    break;
                case G2:
                    cells.add(new Cell("2"));
                    break;
                case G3:
                    cells.add(new Cell("3"));
            }
        } else {
            cells.add(new Cell(""));
        }
        return cells;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("body_part", this.body_part.toString());
        json.put("type_of_injury", this.type_of_injury.toString());
        json.put("closed", this.closed);
        json.put("burn_degree", (burn_degree == BurnDegree.EMPTY || burn_degree == null) ? JSONObject.NULL : this.burn_degree.toString());
        return json;
    }

    @Override
    public ArrayList<Flag> update(@NonNull Trauma updated) {
        return null;
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

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public BurnDegree getBurn_degree() {
        return burn_degree;
    }

    public void setBurn_degree(BurnDegree burn_degree) {
        this.burn_degree = burn_degree;
    }
}
