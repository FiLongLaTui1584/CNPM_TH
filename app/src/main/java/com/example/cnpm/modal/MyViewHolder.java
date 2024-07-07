package com.example.cnpm.modal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnpm.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView nameView,emailView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView=itemView.findViewById(R.id.name);
        emailView=itemView.findViewById(R.id.email);
    }
}
