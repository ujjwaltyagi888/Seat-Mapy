package com.conquistador.seatmapy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
                List<Integer> list = new ArrayList<>();
                for (int i = 1; i <= 6; i++) {
                    list.add(Integer.parseInt(snapshot.child(key + " " + i).getValue().toString()));
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