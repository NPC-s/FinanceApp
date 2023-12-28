package com.npcs.financehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransDataAdapter adapter;
    private ArrayList<Transaction> dataItems;
    private String[] Categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        recyclerView = findViewById(R.id.idRecyclerTransactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        FinanceDBHandler DBHandler = new FinanceDBHandler(MainActivity.this);

        PieChart pieChart = findViewById(R.id.pie_chart);

        Categories = DBHandler.getCategories(true).toArray(new String[0]);
        boolean[] checkedItems = new boolean[Categories.length];
        for (int i = 0; i < Categories.length; i++) {
            checkedItems[i] = true;
        }

        pieChart.setLabels(Categories, DBHandler);

        if (Arrays.asList(checkedItems).contains(false))
            dataItems = DBHandler.getTransactions();
        else{
            ArrayList<String> cats = new ArrayList<>();
            for (int i = 0; i < Categories.length; i++)
                if (checkedItems[i])
                    cats.add(Categories[i]);
            dataItems = DBHandler.getTransactions(cats.toArray(new String[0]), true);

        }

        adapter = new TransDataAdapter(dataItems);

        recyclerView.setAdapter(adapter);

        pieChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pieChart.Touch(motionEvent.getX(), motionEvent.getY());
                return true;
            }
        });

        Button addTrans = findViewById(R.id.addTransaction);
        addTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });

        Button filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Выберите категории");

                builder.setMultiChoiceItems(Categories, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Log.d("1213", String.valueOf(which));
                    }
                });

                builder.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> cats = new ArrayList<>();
                        for (int i = 1; i < Categories.length; i++)
                            if (checkedItems[i])
                                cats.add(Categories[i]);
                        dataItems = DBHandler.getTransactions(cats.toArray(new String[0]), (Categories[0] == "Пополнения" & checkedItems[0]));
                        recyclerView.setAdapter(new TransDataAdapter(dataItems));
                    }
                });

                builder.show();
            }
        });

        Button toGoals = findViewById(R.id.goToGoals);
        toGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });
    }

}