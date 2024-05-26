package com.example.ch_iot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdditionalActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAlcohol;
    private TextView textViewInAlcohol;
    private TextView textViewCar1;
    private TextView textViewCar2;
    private TextView textViewHidden;
    private Button button1;
    private Button button2;

    private double measureAlcohol;
    private double calculateAlcohol;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_additional);

        textViewName = findViewById(R.id.add_text_name);
        textViewAlcohol = findViewById(R.id.add_text_alcohol);
        textViewInAlcohol = findViewById(R.id.add_text_inAlcohol);
        textViewCar1 = findViewById(R.id.add_text_car1);
        textViewCar2 = findViewById(R.id.add_text_car2);
        textViewHidden = findViewById(R.id.add_hidden);
        button1 = findViewById(R.id.add_btn1);
        button2 = findViewById(R.id.add_btn2);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        name = sharedPreferences1.getString("userName", null);

        SharedPreferences sharedPreferences2 = getSharedPreferences("AlcoholPrefs", MODE_PRIVATE);
        measureAlcohol = sharedPreferences2.getFloat("alcohol", 0);

        SharedPreferences sharedPreferences3 = getSharedPreferences("CalPrefs", MODE_PRIVATE);
        calculateAlcohol = sharedPreferences3.getFloat("cal", 0);

        Log.d("확인", "userName : " + name + "alcohol : " + measureAlcohol + "calculateAlcohol : " + calculateAlcohol);

        measureAlcohol = Math.round(measureAlcohol * 1000) / 1000.0;
        calculateAlcohol = Math.round(calculateAlcohol * 1000) / 1000.0;

        if (calculateAlcohol < 0.03) {
            textViewCar1.setTextColor(Color.rgb(51, 181, 229));
            textViewCar1.setText("이상없음");
        } else if (calculateAlcohol >= 0.03 && calculateAlcohol < 0.08) {
            textViewCar1.setText("1년 이하의 징역이나 500만원 이하의 벌금");
        } else if (calculateAlcohol >= 0.08 && calculateAlcohol < 0.2) {
            textViewCar1.setText("1년 이상 2년 이하의 징역");
            textViewHidden.setVisibility(View.VISIBLE);
            textViewCar2.setVisibility(View.VISIBLE);
            textViewCar2.setText("500만원 이상 1천만원 이하의 벌금");
        } else {
            textViewCar1.setText("2년 이상 5년 이하의 징역");
            textViewHidden.setVisibility(View.VISIBLE);
            textViewCar2.setVisibility(View.VISIBLE);
            textViewCar2.setText("1천만원 이상 2천만원 이하의 벌금");
        }


        textViewName.setText(name);
        textViewAlcohol.setText(String.valueOf(measureAlcohol));

        if (calculateAlcohol < 0) {
            textViewInAlcohol.setText("0.00");
        } else {
            textViewInAlcohol.setText(String.valueOf(calculateAlcohol));
        }

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(AdditionalActivity.this, ViewActivity1.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(AdditionalActivity.this, ViewActivity2.class);
            startActivity(intent);
        });

    }
}