package com.example.ch_iot;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "Register";
    private boolean isBlank = false;

    private Button registerBtn;
    private EditText editName;
    private EditText editBirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        registerBtn = findViewById(R.id.reg_btn_register);
        editName = findViewById(R.id.reg_edit_name);
        editBirth = findViewById(R.id.reg_edit_birth);

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "회원가입 버튼 클릭");

                String name =  editName.getText().toString();
                String birth = editBirth.getText().toString();


                if (name.isEmpty() || birth.isEmpty()) {
                    isBlank = true;
                }

                if (!isBlank) {

                    boolean isEx = sqLiteHelper.selectName(name).isEmpty();

                    if (isEx) {
                        sqLiteHelper.save(name, birth);
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "이미 존재 하는 이름 입니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}