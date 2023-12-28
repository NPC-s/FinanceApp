package com.npcs.financehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class AddGoalActivity extends AppCompatActivity {
    private String[] categories = {"Продукты", "Развлечения", "Медицина", "Техника"};

    private FinanceDBHandler DBHandler;

    private EditText name;
    private EditText maxValue;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_goal);

        name = findViewById(R.id.goalText);
        maxValue = findViewById(R.id.goalMaxValue);

        confirm = findViewById(R.id.addGoal);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Goal g = new Goal(name.getText().toString(), 0, Integer.parseInt(maxValue.getText().toString()));

                DBHandler = new FinanceDBHandler(AddGoalActivity.this);
                DBHandler.addGoal(g);
                Intent intent = new Intent(AddGoalActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });
    }
}
