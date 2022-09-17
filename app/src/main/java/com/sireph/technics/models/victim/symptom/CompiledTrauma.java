package com.sireph.technics.models.victim.symptom;

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

public class CompiledTrauma extends _BaseTableModel<CompiledTrauma> {
    private BodyPart bodyPart;
    private boolean isPenetrating;
    private boolean isFracture, isContusion, isWound, isHaemorrhage, isBurn, isPain;
    private BurnDegree burnDegree;

    public CompiledTrauma(BodyPart bodyPart, boolean isPenetrating, boolean isFracture, boolean isContusion, boolean isWound, boolean isHaemorrhage,
                          boolean isBurn, boolean isPain, BurnDegree burnDegree) {
        this.bodyPart = bodyPart;
        this.isPenetrating = isPenetrating;
        this.isFracture = isFracture;
        this.isContusion = isContusion;
        this.isWound = isWound;
        this.isHaemorrhage = isHaemorrhage;
        this.isBurn = isBurn;
        this.isPain = isPain;
        this.burnDegree = burnDegree;
    }

    @NonNull
    public static List<CompiledTrauma> compileTraumas(@NonNull List<Trauma> traumas) {
        List<CompiledTrauma> compiledTraumas = new ArrayList<>();
        addTraumas(compiledTraumas, traumas);
        return compiledTraumas;
    }

    public static void addTraumas(List<CompiledTrauma> compiledTraumas, @NonNull List<Trauma> traumas) {
        for (Trauma t : traumas) {
            addTrauma(compiledTraumas, t);
        }
    }

    public static void addTrauma(List<CompiledTrauma> compiledTraumas, @NonNull Trauma trauma) {
        if (trauma.getBody_part() == BodyPart.EMPTY) {
            return;
        }
        CompiledTrauma compiledTrauma = null;
        for (CompiledTrauma c : compiledTraumas) {
            if (trauma.getBody_part() == c.bodyPart && trauma.getClosed() == !c.isPenetrating) {
                compiledTrauma = c;
                break;
            }
        }
        if (compiledTrauma == null) {
            compiledTrauma = new CompiledTrauma(trauma.getBody_part(), !trauma.getClosed(), false, false, false, false, false, false, BurnDegree.EMPTY);
            compiledTrauma.addComponent(trauma);
            compiledTraumas.add(compiledTrauma);
        } else {
            compiledTrauma.addComponent(trauma);
        }
    }

    public static void addCompiledTrauma(List<CompiledTrauma> compiledTraumas, @NonNull CompiledTrauma compiledTrauma) {
        addTraumas(compiledTraumas, compiledTrauma.genComponents());
    }

    public List<Trauma> genComponents() {
        List<Trauma> traumas = new ArrayList<>();
        if (isFracture) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.FRACTURE, BurnDegree.EMPTY));
        }
        if (isContusion) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.CONTUSION, BurnDegree.EMPTY));
        }
        if (isWound) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.WOUND, BurnDegree.EMPTY));
        }
        if (isHaemorrhage) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.HAEMORRHAGE, BurnDegree.EMPTY));
        }
        if (isBurn) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.BURN, burnDegree));
        }
        if (isPain) {
            traumas.add(new Trauma(!isPenetrating, bodyPart, TypeOfInjury.PAIN, BurnDegree.EMPTY));
        }
        return traumas;
    }

    public void addComponent(@NonNull Trauma trauma) {
        if (trauma.getBody_part() != bodyPart && trauma.getClosed() == isPenetrating) {
            throw new IllegalArgumentException();
        }
        switch (trauma.getType_of_injury()) {
            case FRACTURE:
                isFracture = true;
                break;
            case PAIN:
                isPain = true;
                break;
            case CONTUSION:
                isContusion = true;
                break;
            case HAEMORRHAGE:
                isHaemorrhage = true;
                break;
            case WOUND:
                isWound = true;
                break;
            case BURN:
                isBurn = true;
                burnDegree = trauma.getBurn_degree();
                break;
        }
    }

    @NonNull
    @Override
    public List<Cell> toCellList() {
        List<Cell> cells = new ArrayList<>();
        String mark = isPenetrating ? "X" : "O";
        cells.add(new Cell(bodyPart.toString()));
        cells.add(new Cell(isFracture ? mark : ""));
        cells.add(new Cell(isContusion ? mark : ""));
        cells.add(new Cell(isWound ? mark : ""));
        cells.add(new Cell(isHaemorrhage ? mark : ""));
        if (isBurn) {
            switch (burnDegree) {
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
        cells.add(new Cell(isPain ? mark : ""));
        return cells;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }

    public boolean isPenetrating() {
        return isPenetrating;
    }

    public void setPenetrating(boolean penetrating) {
        isPenetrating = penetrating;
    }

    public boolean isFracture() {
        return isFracture;
    }

    public void setFracture(boolean fracture) {
        isFracture = fracture;
    }

    public boolean isContusion() {
        return isContusion;
    }

    public void setContusion(boolean contusion) {
        isContusion = contusion;
    }

    public boolean isWound() {
        return isWound;
    }

    public void setWound(boolean wound) {
        isWound = wound;
    }

    public boolean isHaemorrhage() {
        return isHaemorrhage;
    }

    public void setHaemorrhage(boolean haemorrhage) {
        isHaemorrhage = haemorrhage;
    }

    public boolean isBurn() {
        return isBurn;
    }

    public void setBurn(boolean burn) {
        isBurn = burn;
    }

    public boolean isPain() {
        return isPain;
    }

    public void setPain(boolean pain) {
        isPain = pain;
    }

    public BurnDegree getBurnDegree() {
        return burnDegree;
    }

    public void setBurnDegree(BurnDegree burnDegree) {
        this.burnDegree = burnDegree;
    }

    @Override
    public JSONObject toJson(@NonNull TypeOfJson type) throws JSONException {
        return null;
    }

    @Override
    public ArrayList<Flag> update(@NonNull CompiledTrauma updated) {
        return null;
    }
}
