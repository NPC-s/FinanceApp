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

public class AddTransactionActivity extends AppCompatActivity {
    private String[] categories = {"Продукты", "Развлечения", "Медицина", "Техника"};

    private FinanceDBHandler DBHandler;

    private EditText valueRaw;
    private Switch isAddRaw;
    private Spinner categoryTransRaw;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryTransRaw = findViewById(R.id.categoryOfTransaction);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryTransRaw.setAdapter(adapter);

        confirm = findViewById(R.id.BtnAddTransaction);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueRaw = findViewById(R.id.idSetValue);
                isAddRaw = findViewById(R.id.isAdd);

                String value = valueRaw.getText().toString();
                boolean isAdd = isAddRaw.isChecked();
                String categoryTrans = categoryTransRaw.getSelectedItem().toString();

                DBHandler = new FinanceDBHandler(AddTransactionActivity.this);
                DBHandler.AddTransactions(value, isAdd, categoryTrans);
                Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
