package com.example.ch_iot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    private static double ALCOHOL_CONSTANT = 0.010;
    private String userName;
    private int age;
    private int weight;
    private int cnt;
    private int timePass;
    private String gender;
    private double maxAlcohol;
    private EditText editTextAge;
    private EditText editTextWeight;
    private EditText editTextCnt;
    private EditText editTextTimePass;
    private RadioGroup genderRadioGroup;
    private RadioButton radioMale, radioFemale;
    private Button infoButton;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);

        sqLiteHelper = new SQLiteHelper(this);

        Intent intent = getIntent();
        maxAlcohol = intent.getDoubleExtra("maxAlcohol", 0.00);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);

        editTextAge = findViewById(R.id.info_edit_text_age);
        editTextWeight = findViewById(R.id.info_edit_text_weight);
        editTextCnt = findViewById(R.id.info_edit_text_cnt);
        editTextTimePass = findViewById(R.id.info_edit_text_timePass);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        infoButton = findViewById(R.id.info_button);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMale) {
                    gender = "남";
                } else if (checkedId == R.id.radioFemale) {
                    gender = "여";
                }
            }
        });


        infoButton.setOnClickListener(v -> {

            if (isInputValid()) {
                age = Integer.parseInt(editTextAge.getText().toString());
                weight = Integer.parseInt(editTextWeight.getText().toString());
                cnt = Integer.parseInt(editTextCnt.getText().toString());
                timePass = Integer.parseInt(editTextTimePass.getText().toString());


                Map<String, String> stringStringMap = sqLiteHelper.selectName(userName).get(0);

                String id = stringStringMap.get("UID");
                String birth = stringStringMap.get("BirthPass");

                sqLiteHelper.updateData(id, userName, birth, age, gender, cnt, weight, maxAlcohol);

                Log.d("확인", "UID: " + id + ", maxAlcohol: " + maxAlcohol + ", userName: " + userName + ", gender: " + gender + ", age: " + age + ", weight: " + weight + ", cnt: " + cnt + ", timePass: " + timePass);

                Map<Integer, Double> integerDoubleMap = alcoholCalculate(cnt, gender, weight, timePass);
                Double maxAlcoholCon = integerDoubleMap.get(1);
                Double nowAlcoholCon = integerDoubleMap.get(2);
                Double hours1 = integerDoubleMap.get(3);
                Double minuit1 = integerDoubleMap.get(4);

                int hours = hours1.intValue();
                int minuit = minuit1.intValue();

                Log.d("확인", "maxCon : " + maxAlcoholCon + ", con : " + nowAlcoholCon);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                TextView pass = dialogView.findViewById(R.id.alert_text_time);
                TextView nowAlcohol = dialogView.findViewById(R.id.alert_text_alcohol_level);
                TextView maxAlcohol = dialogView.findViewById(R.id.alert_text_alcohol_level_max);
                TextView breakTime = dialogView.findViewById(R.id.alert_text_breakdown_time);
                TextView breakMinuit = dialogView.findViewById(R.id.alert_text_breakdown_minutes);
                Button noBtn = dialogView.findViewById(R.id.alert_noButton);
                Button yesBtn = dialogView.findViewById(R.id.alert_yesButton);

                pass.setText(String.valueOf(timePass));
                maxAlcohol.setText(String.valueOf(maxAlcoholCon));

                if (nowAlcoholCon < 0.00) {
                    nowAlcohol.setText("0.00");
                    breakTime.setText("0 시간 ");
                    breakMinuit.setText("0분");
                } else {
                    nowAlcohol.setText(String.valueOf(nowAlcoholCon));
                    breakTime.setText(hours + "시간 ");
                    breakMinuit.setText(minuit + "분");
                }

                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setView(dialogView);
                builder.setPositiveButton(null, null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private boolean isInputValid() {
        if (editTextAge.getText().toString().isEmpty()) {
            Toast.makeText(this, "나이를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextWeight.getText().toString().isEmpty()) {
            Toast.makeText(this, "몸무게를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextCnt.getText().toString().isEmpty()) {
            Toast.makeText(this, "음주 횟수를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextTimePass.getText().toString().isEmpty()) {
            Toast.makeText(this, "경과 시간을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender == null) {
            Toast.makeText(this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Map<Integer, Double> alcoholCalculate(int cnt, String gender, int weight, int timePass) {

        Map<Integer, Double> map = new HashMap<>();

        double drinkAlcohol = (cnt * 48) * 0.165 * 0.7894 * 0.7;
        double weightVal = 0.00;

        if (gender.equals("남")) {
            weightVal = weight * 10 * 0.86;
        } else if (gender.equals("여")) {
            weightVal = weight * 10 * 0.64;
        }

        double maxAlcoholCon = Math.round((drinkAlcohol / weightVal) * 1000) / 1000.0;
        // 상수 고민 0.03 0.015 0.008 => 0.010
        double nowAlcoholCon = Math.round((maxAlcoholCon - (ALCOHOL_CONSTANT * timePass)) * 1000) / 1000.0;

        SharedPreferences sharedPreferences = getSharedPreferences("CalPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("cal", (float) nowAlcoholCon);
        editor.apply();

        int hours = (int) (nowAlcoholCon / ALCOHOL_CONSTANT);
        int minutes = (int) (((nowAlcoholCon / ALCOHOL_CONSTANT) - hours) * 60);

        Log.d("tag" , "maxAlcoholCon : " + maxAlcoholCon + " , nowAlcoholCon : " + nowAlcoholCon);
        Log.d("tag" , "hours : " + hours + " , minutes : " + minutes);

        map.put(1, maxAlcoholCon);
        map.put(2, nowAlcoholCon);
        map.put(3, (double) hours);
        map.put(4, (double) minutes);

        return map;
    }
}
