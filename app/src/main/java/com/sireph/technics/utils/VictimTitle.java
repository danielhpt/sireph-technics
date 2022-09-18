package com.sireph.technics.utils;

import android.content.Context;

import com.sireph.technics.R;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.victim.Victim;

import java.util.Objects;

public class VictimTitle {
    public static String createTitle(Victim victim, int n, Context context) {
        String s;
        if (victim.getName() != null && !Objects.equals(victim.getName(), "")) {
            s = victim.getName();
        } else {
            s = context.getString(R.string.victim) + " " + n;
        }
        if (victim.getGender() != Gender.EMPTY || victim.getAge() != null) {
            s += " (";
            if (victim.getGender() != Gender.EMPTY) {
                s += victim.getGender();
            }
            if (victim.getGender() != Gender.EMPTY && victim.getAge() != null) {
                s += " - ";
            }
            if (victim.getAge() != null) {
                s += victim.getAge();
            }
            s += ")";
        }
        return s;
    }
}
