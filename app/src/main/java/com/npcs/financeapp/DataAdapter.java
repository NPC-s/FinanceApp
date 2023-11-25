package com.npcs.financeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Transaction> dataItemList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView valueTrans;
        private final TextView categoryTrans;
        private final TextView isAdd;

        ViewHolder(View itemView) {
            super(itemView);
            valueTrans = itemView.findViewById(R.id.textViewValue);
            isAdd = itemView.findViewById(R.id.textViewIsAdd);
            categoryTrans = itemView.findViewById(R.id.textViewCategory);
        }
    }

    public DataAdapter(ArrayList<Transaction> dataItemList) {
        this.dataItemList = dataItemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction currentItem = dataItemList.get(position);
        String isAddText;
        if (!currentItem.isAdd) {
            isAddText = "Трата";
            holder.categoryTrans.setText(currentItem.category);
        }
        else {
            isAddText = "Получение";
        }
        holder.valueTrans.setText(currentItem.value);
        holder.isAdd.setText(isAddText);
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }
}
