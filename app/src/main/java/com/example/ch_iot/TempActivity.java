package com.example.ch_iot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TempActivity extends AppCompatActivity {

    private EditText editTextFrequency;
    private EditText editTextAverage;
    private RadioGroup tempRadioGroup;
    private RadioButton tempRadioMale, tempRadioFemale;
    private Button tempBtn;

    private int frequency;
    private int average;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp);

        editTextFrequency = findViewById(R.id.temp_edit_text_frequency);
        editTextAverage = findViewById(R.id.temp_edit_text_average);
        tempRadioGroup = findViewById(R.id.tempRadioGroup);
        tempRadioMale = findViewById(R.id.temp_radioMale);
        tempRadioFemale = findViewById(R.id.temp_radioFemale);
        tempBtn = findViewById(R.id.temp_button);

        SharedPreferences sharedPreferences = getSharedPreferences("TempPrefs", MODE_PRIVATE);

        tempRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.temp_radioMale) {
                    gender = "남";
                } else if (checkedId == R.id.temp_radioFemale) {
                    gender = "여";
                }
            }
        });


        tempBtn.setOnClickListener(v -> {

            if (isValid()) {

                frequency = Integer.parseInt(editTextFrequency.getText().toString());
                average = Integer.parseInt(editTextAverage.getText().toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("fre", frequency);
                editor.putInt("aver", average);
                editor.putString("gender", gender);
                editor.apply();

                Intent intent = new Intent(TempActivity.this, StatisActivity.class);
                startActivity(intent);

            }
        });

    }

    private boolean isValid() {

        if (editTextFrequency.getText().toString().isEmpty()) {
            Toast.makeText(this, "빈도 수를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editTextAverage.getText().toString().isEmpty()) {
            Toast.makeText(this, "평균 주량을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gender == null) {
            Toast.makeText(this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}