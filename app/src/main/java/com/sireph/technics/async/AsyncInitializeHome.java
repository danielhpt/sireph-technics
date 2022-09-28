package com.sireph.technics.async;

import android.os.Handler;
import android.os.Looper;

import com.sireph.technics.models.Hospital;
import com.sireph.technics.models.Team;
import com.sireph.technics.models.Technician;
import com.sireph.technics.models.occurrence.Occurrence;
import com.sireph.technics.utils.RestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AsyncInitializeHome {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Listener listener;
    private final CountDownLatch techniciansLatch = new CountDownLatch(1), hospitalsLatch = new CountDownLatch(1), teamLatch = new CountDownLatch(1);

    public AsyncInitializeHome(Listener listener) {
        this.listener = listener;
    }

    public void execute(String token, Technician technician) {
        final AtomicReference<Team> team = new AtomicReference<>();
        executor.execute(() -> {
            List<Technician> technicians;
            try {
                technicians = RestApi.getTechnicians(token, technician.getId(), technician.getCentral());
            } catch (JSONException | IOException e) {
                technicians = new ArrayList<>();
            }
            techniciansLatch.countDown();
            List<Technician> finalTechnicians = technicians;
            handler.post(() -> listener.onResponseTechnicians(finalTechnicians));
        });
        executor.execute(() -> {
            List<Hospital> hospitals;
            try {
                hospitals = RestApi.getHospitals(token);
            } catch (JSONException | IOException e) {
                hospitals = new ArrayList<>();
            }
            hospitalsLatch.countDown();
            List<Hospital> finalHospitals = hospitals;
            handler.post(() -> listener.onResponseHospitals(finalHospitals));
        });
        executor.execute(() -> {
            try {
                team.set(RestApi.getTeam(token, technician));
            } catch (JSONException | IOException e) {
                team.set(null);
            }
            teamLatch.countDown();
            if (team.get() == null) {
                try {
                    techniciansLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.post(() -> listener.onResponseTeam(team.get()));
            try {
                techniciansLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(listener::onResponseTeamTechniciansDone);
        });
        executor.execute(() -> {
            List<Occurrence> allOccurrences;
            try {
                allOccurrences = RestApi.getOccurrences(token, technician);
            } catch (JSONException | IOException e) {
                allOccurrences = new ArrayList<>();
            }
            Occurrence activeOccurrence = null;
            List<Occurrence> teamOccurrences = new ArrayList<>();
            try {
                teamLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (team.get() != null) {
                for (Occurrence o : allOccurrences) {
                    if (Objects.equals(o.getTeam().getId(), team.get().getId())) {
                        if (o.isActive()) {
                            activeOccurrence = o;
                            allOccurrences.remove(o);
                        } else {
                            teamOccurrences.add(o);
                        }
                    }
                }
            }
            List<Occurrence> finalAllOccurrences = allOccurrences;
            handler.post(() -> listener.onResponseOccurrences(finalAllOccurrences, teamOccurrences));
            if (activeOccurrence != null) {
                try {
                    hospitalsLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Occurrence finalActiveOccurrence = activeOccurrence;
                handler.post(() -> listener.onResponseActiveOccurrence(finalActiveOccurrence));
            } else {
                handler.post(() -> listener.onResponseActiveOccurrence(null));
            }
        });
        executor.shutdown();
        try {
            boolean result = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handler.post(listener::onRefreshDone);
    }

    public interface Listener {
        void onResponseTechnicians(List<Technician> technicians);

        void onResponseHospitals(List<Hospital> hospitals);

        void onResponseTeam(Team team);

        void onResponseTeamTechniciansDone();

        void onResponseOccurrences(List<Occurrence> technicianOccurrences, List<Occurrence> teamOccurrences);

        void onResponseActiveOccurrence(Occurrence activeOccurrence);

        void onRefreshDone();
    }
}
