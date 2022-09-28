package com.sireph.technics.test;

import androidx.annotation.NonNull;

import java.io.Serializable;

public enum Action implements Serializable {
    START_TEST("start test"),
    END_TEST("end test"),

    START_SCENARIO_1("start scenario 1"),
    RESET_SCENARIO_1("reset scenario 1"),
    END_SCENARIO_1("end scenario 1"),

    START_SCENARIO_2("start scenario 2"),
    RESET_SCENARIO_2("reset scenario 2"),
    END_SCENARIO_2("end scenario 2"),

    START_SCENARIO_3("start scenario 3"),
    RESET_SCENARIO_3("reset scenario 3"),
    END_SCENARIO_3("end scenario 3");

    private final String action;

    Action(String action) {
        this.action = action;
    }

    @NonNull
    @Override
    public String toString() {
        return action;
    }
}
