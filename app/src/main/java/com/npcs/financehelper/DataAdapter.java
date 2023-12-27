package com.npcs.financehelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Transaction> dataItemList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView valueTrans;
        private final TextView categoryTrans;
        private final TextView isAdd;

        private final ImageView iconViewCategory;

        ViewHolder(View itemView) {
            super(itemView);
            valueTrans = itemView.findViewById(R.id.textViewValue);
            isAdd = itemView.findViewById(R.id.textViewIsAdd);
            categoryTrans = itemView.findViewById(R.id.textViewCategory);
            iconViewCategory = itemView.findViewById(R.id.iconViewCategory);
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
            switch (currentItem.category){
                case ("Продукты"):
                    holder.iconViewCategory.setImageResource(R.drawable.products);
                    break;
                case ("Медицина"):
                    holder.iconViewCategory.setImageResource(R.drawable.chemestry);
                    break;
                case ("Техника"):
                    holder.iconViewCategory.setImageResource(R.drawable.technique);
                    break;
                case ("Развлечения"):
                    holder.iconViewCategory.setImageResource(R.drawable.entertaiment);
                    break;
            }

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
