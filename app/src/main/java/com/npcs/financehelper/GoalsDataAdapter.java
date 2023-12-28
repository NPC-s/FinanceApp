package com.npcs.financehelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GoalsDataAdapter extends RecyclerView.Adapter<GoalsDataAdapter.ViewHolder> {

    private final ArrayList<Goal> dataItemList;
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView value;
        private final TextView maxValue;
        private final Button btn;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textGoalName);
            value = itemView.findViewById(R.id.textCurVal);
            maxValue = itemView.findViewById(R.id.textMaxVal);
            btn = itemView.findViewById(R.id.changeGoal);
        }
    }

    public GoalsDataAdapter(ArrayList<Goal> dataItemList, Context ctx) {
        this.dataItemList = dataItemList;
        this.context = ctx;
    }

    @NonNull
    @Override
    public GoalsDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal, parent, false);
        return new GoalsDataAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goal curItem = dataItemList.get(position);
        holder.name.setText(curItem.name.toString());
        holder.value.setText(String.valueOf(curItem.value));
        holder.maxValue.setText(String.valueOf(curItem.maxValue));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click: ", "1");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }
}
