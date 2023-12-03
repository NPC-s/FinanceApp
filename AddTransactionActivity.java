import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.npcs.financeapp.FinanceDBHandler;
import com.npcs.financeapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    private String[] categories = {"Продукты", "Развлечения", "Медицина", "Техника"};
    private FinanceDBHandler DBHandler;

    private EditText valueRaw;
    private Switch isAddRaw;
    private Spinner categoryTransRaw;
    private Button confirm;
    private TextView progressText;
    private int totalBudget = 1000; // Общий бюджет (замените на реальное значение)
    private int currentSpent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryTransRaw = findViewById(R.id.categoryOfTransaction);
        progressText = findViewById(R.id.progressText);

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
                boolean isAdd = isAddRaw.isChecked();  // Используйте isChecked() для Switch
                String categoryTrans = categoryTransRaw.getSelectedItem().toString();

                DBHandler = new FinanceDBHandler(AddTransactionActivity.this);
                DBHandler.AddTransactions(value, isAdd, categoryTrans);

                // Обновление значения currentSpent
                // Здесь вам нужно заменить "заглушку" на реальное значение из базы данных
                int transactionValue = Integer.parseInt(value);
                currentSpent += isAdd ? transactionValue : -transactionValue;

                // Обновление ProgressBar
                updateProgressBar();

                Intent intent = new Intent(AddTransactionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateProgressBar() {
        // Вычисление процента заполнения ProgressBar
        int progress = (currentSpent * 100) / totalBudget;

        // Обновление ProgressBar
        ProgressBar progressBar = findViewById(R.id.background_progressbar);
        progressBar.setProgress(progress);

        // Обновление текста для отображения прогресса
        progressText.setText("Прогресс: " + progress + "%");
    }
}
