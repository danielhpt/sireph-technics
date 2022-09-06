package com.sireph.technics.models.enums;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public enum BodyPart implements Serializable {
    SKULL("Crânio"),
    FACE("Face"),
    CERVICAL("R. Cervical"),
    LIMB_S_R("Membro Sup. Dto."),
    LIMB_S_L("Membro Sup. Esq."),
    LIMB_I_R("Membro Inf. Dto."),
    LIMB_I_L("Membro Inf. Esq."),
    THORAX_A_R("Tórax Ant. Dto."),
    THORAX_A_L("Tórax Ant. Esq."),
    THORAX_P("Tórax Posterior"),
    HYPOCHONDRIUM_R("Hipocôndrio Dto."),
    HYPOCHONDRIUM_L("Hipocôndrio Esq."),
    FLANK_R("Flanco Dto."),
    FLANK_L("Flanco Esq."),
    ILIAC_FOSSA_R("Fossa Ilíaca Dta."),
    ILIAC_FOSSA_L("Fossa Ilíaca Esq."),
    LUMBAR("Reg. Lombar"),
    SACRAL("Reg. Sagrada"),
    PELVIS("Bacia"),
    EPIGASTRIUM("Epigástrio"),
    MESOGASTRIUM("Mesogástrio"),
    HYPOGASTRIUM("Hipogástrio"),
    GENITALS("Genitália"),
    EMPTY("");

    private final String body_part;

    BodyPart(String body_part) {
        this.body_part = body_part;
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
            case "":
            case "null":
                return EMPTY;
            default:
                throw new IllegalArgumentException();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return body_part;
    }
}
