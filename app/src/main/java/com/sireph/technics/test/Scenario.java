package com.sireph.technics.test;

import com.sireph.technics.models.Central;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.date.DateTime;
import com.sireph.technics.models.enums.Gender;
import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.models.occurrence.OccurrenceState;
import com.sireph.technics.models.victim.Victim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Scenario implements Serializable {
    public static final Scenario
            Scenario1 = new Scenario("Av. 25 de Abril, 20, 3º esq, 7080-075", "Vendas Novas", "Dor abdominal", new Victim(Gender.M, 20)),
            Scenario2 = new Scenario("Av. da República, VP\nEm frente ao Migalhas", "Vendas Novas", "Atropelamento", new Victim(Gender.F, 55)),
            Scenario3 = new Scenario("Rua catarina Eufémia, 12", "Landeira", "Dor torácica", new Victim(Gender.M, 70));
    private static final String municipality = "Vendas Novas";
    private static final String entity = "Bombeiros";
    private static final String mean_of_assistance = "Ambulância";
    private static final List<OccurrenceState> states = new ArrayList<>();
    private final List<Victim> victims = new ArrayList<>();
    private final String local;
    private final String parish;
    private final String motive;

    private Scenario(String local, String parish, String motive, Victim victim) {
        this.local = local;
        this.parish = parish;
        this.motive = motive;
        this.victims.add(victim);
    }

    public Occurrence occurrence(Team team, Central central) {
        return new Occurrence(team, central, new Random().nextInt(1000), 1, null, false, DateTime.now(),
                1, local, parish, municipality, entity, mean_of_assistance, motive, true, states, victims);
    }

    public Victim victim() {
        return victims.get(0);
    }
}
