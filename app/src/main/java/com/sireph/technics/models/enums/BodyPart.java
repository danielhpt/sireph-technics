package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum BodyPart implements Serializable {
    EMPTY("", 0),
    SKULL("Crânio", 3),
    FACE("Face", 3),
    CERVICAL("R. Cervical", 3),
    LIMB_S_R("Membro Sup. Dto.", 9),
    LIMB_S_L("Membro Sup. Esq.", 9),
    LIMB_I_R("Membro Inf. Dto.", 18),
    LIMB_I_L("Membro Inf. Esq.", 18),
    THORAX_A_R("Tórax Ant. Dto.", 4.5),
    THORAX_A_L("Tórax Ant. Esq.", 4.5),
    THORAX_P("Tórax Posterior", 9),
    HYPOCHONDRIUM_R("Hipocôndrio Dto.", 1),
    HYPOCHONDRIUM_L("Hipocôndrio Esq.", 1),
    FLANK_R("Flanco Dto.", 1),
    FLANK_L("Flanco Esq.", 1),
    ILIAC_FOSSA_R("Fossa Ilíaca Dta.", 1),
    ILIAC_FOSSA_L("Fossa Ilíaca Esq.", 1),
    EPIGASTRIUM("Epigástrio", 1),
    MESOGASTRIUM("Mesogástrio", 1),
    HYPOGASTRIUM("Hipogástrio", 1),
    LUMBAR("Reg. Lombar", 4.5),
    SACRAL("Reg. Sagrada", 1),
    PELVIS("Bacia", 3.5),
    GENITALS("Genitália", 1);

    private final String value;
    private final double area;

    BodyPart(String value, double area) {
        this.value = value;
        this.area = area;
    }

    public static BodyPart fromJson(JSONObject json) {
        if (json.isNull("body_part")) {
            return EMPTY;
        } else {
            String value;
            try {
                value = json.getString("body_part");
            } catch (JSONException e) {
                return EMPTY;
            }
            return fromValue(value);
        }
    }

    public static BodyPart fromValue(String value) {
        if (value == null) {
            return EMPTY;
        }
        switch (value) {
            case "Crânio":
                return SKULL;
            case "Face":
                return FACE;
            case "R. Cervical":
                return CERVICAL;
            case "Membro Sup. Dto.":
                return LIMB_S_R;
            case "Membro Sup. Esq.":
                return LIMB_S_L;
            case "Membro Inf. Dto.":
                return LIMB_I_R;
            case "Membro Inf. Esq.":
                return LIMB_I_L;
            case "Tórax Ant. Dto.":
                return THORAX_A_R;
            case "Tórax Ant. Esq.":
                return THORAX_A_L;
            case "Tórax Posterior":
                return THORAX_P;
            case "Hipocôndrio Dto.":
                return HYPOCHONDRIUM_R;
            case "Hipocôndrio Esq.":
                return HYPOCHONDRIUM_L;
            case "Flanco Dto.":
                return FLANK_R;
            case "Flanco Esq.":
                return FLANK_L;
            case "Fossa Ilíaca Dta.":
                return ILIAC_FOSSA_R;
            case "Fossa Ilíaca Esq.":
                return ILIAC_FOSSA_L;
            case "Reg. Lombar":
                return LUMBAR;
            case "Reg. Sagrada":
                return SACRAL;
            case "Bacia":
                return PELVIS;
            case "Epigástrio":
                return EPIGASTRIUM;
            case "Mesogástrio":
                return MESOGASTRIUM;
            case "Hipogástrio":
                return HYPOGASTRIUM;
            case "Genitália":
                return GENITALS;
            default:
                return EMPTY;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }

    public double getArea() {
        return area;
    }
}
