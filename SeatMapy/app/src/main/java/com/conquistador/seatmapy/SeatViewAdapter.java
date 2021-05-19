package com.conquistador.seatmapy;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SeatViewAdapter extends RecyclerView.Adapter<SeatViewHolder> {

    List<Integer> seats;

    public SeatViewAdapter(List<Integer> seats) {
        this.seats = seats;
    }

    @NonNull
    @NotNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SeatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SeatViewHolder holder, int position) {
        int occupied = seats.get(position);
        if (occupied == 1) {
            holder.seatImage.setImageTintList(ColorStateList.valueOf(Color.RED));
        } else {
            holder.seatImage.setImageTintList(ColorStateList.valueOf(Color.GREEN));
        }
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }
}
