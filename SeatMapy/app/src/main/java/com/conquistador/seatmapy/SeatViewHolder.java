package com.conquistador.seatmapy;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SeatViewHolder extends RecyclerView.ViewHolder {

    AppCompatImageView seatImage;

    public SeatViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        seatImage = itemView.findViewById(R.id.seat_image);
    }
}
