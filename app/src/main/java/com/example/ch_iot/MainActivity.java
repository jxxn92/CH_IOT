package com.example.ch_iot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String findName;

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Button loginBtn;
    private Button registerBtn;
    private EditText editName;
    private EditText editBirth;

    private String saveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase =  sqLiteHelper.getWritableDatabase();
        sqLiteHelper.onCreate(sqLiteDatabase);

        loginBtn = findViewById(R.id.btn_login);
        registerBtn = findViewById(R.id.btn_register);
        editName = findViewById(R.id.edit_name);
        editBirth = findViewById(R.id.edit_birth);

        loginBtn.setOnClickListener(v -> {

            String getName =  editName.getText().toString().trim();
            String getBirth = editBirth.getText().toString().trim();
            findName = getName;

            Intent intent = new Intent(MainActivity.this, AlcoholActivity.class);
            intent.putExtra("name", findName);


            List<Map<String, String>> maps = sqLiteHelper.selectName(getName);

            if (!maps.isEmpty() && maps.get(0).get("BirthPass").equals(getBirth)) {
                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", findName);
                editor.apply();

                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


    }
}