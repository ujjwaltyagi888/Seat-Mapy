package com.conquistador.seatmapy;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class SeatViewHolder extends RecyclerView.ViewHolder {

    AppCompatImageView seatImage;
    AppCompatTextView seatStatus;

    public SeatViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        seatImage = itemView.findViewById(R.id.seat_image);
        seatStatus = itemView.findViewById(R.id.seat_status);
    }
}
