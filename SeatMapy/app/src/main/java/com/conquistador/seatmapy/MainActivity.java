package com.conquistador.seatmapy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    ValueEventListener databaseListener;

    RecyclerView seatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = FirebaseDatabase.getInstance().getReference("Seats").child("Bus");

        seatView = findViewById(R.id.seat_list_view);

        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String key = "seat";
                List<Pair<Integer, String>> list = new ArrayList<>();
                for (int i = 1; i <= 6; i++) {
                    int status = Integer.parseInt(snapshot.child(key + " " + i).child("status").getValue().toString());

                    Object obj_start_time = snapshot.child(key + " " + i).child("start_time").getValue();
                    Object obj_stop_time = snapshot.child(key + " " + i).child("stop_time").getValue();
                    String durationText;

                    if (obj_start_time != null && obj_stop_time != null) {
                        long start_time = Long.parseLong(obj_start_time.toString());
                        long stop_time = Long.parseLong(obj_stop_time.toString());

                        if (start_time == -1 && stop_time != -1) {
                            durationText = "UNOCCUPIED";
                        } else if (start_time != -1 && stop_time != -1) {
                            durationText = getDuration(stop_time - start_time);
                        } else {
                            durationText = "N/A";
                        }
                    } else {
                        durationText = "N/A";
                    }

                    list.add(new Pair<>(status, durationText));
                }

                seatView.setHasFixedSize(true);
                seatView.setLayoutManager(new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
                seatView.setAdapter(new SeatViewAdapter(list));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        ref.addValueEventListener(databaseListener);
    }

    private String getDuration(long duration) {
        String out = "";

        duration /= 1000;   // seconds
        if (duration >= 60) {
            int minutes = (int) duration / 60;
            out += minutes + " min(s)";
            out += " " + (duration % 60) + " sec";
        } else {
            out += duration + " seconds";
        }

        return out;
    }

    @Override
    protected void onPause() {
        ref.removeEventListener(databaseListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ref.addValueEventListener(databaseListener);
    }
}