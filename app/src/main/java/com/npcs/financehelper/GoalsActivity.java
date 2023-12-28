package com.npcs.financehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GoalsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GoalsDataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goals);

        recyclerView = findViewById(R.id.Goals);
        recyclerView.setLayoutManager(new LinearLayoutManager(GoalsActivity.this));

        FinanceDBHandler DBHandler = new FinanceDBHandler(GoalsActivity.this);

        ArrayList<Goal> dataItems = DBHandler.getGoals();

        adapter = new GoalsDataAdapter(dataItems, GoalsActivity.this);
        recyclerView.setAdapter(adapter);

        Button addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalsActivity.this, AddGoalActivity.class);
                startActivity(intent);
            }
        });
    }
}