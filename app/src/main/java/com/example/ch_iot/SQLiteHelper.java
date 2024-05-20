package com.example.ch_iot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "iot.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "UID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, " +
                "BirthPass TEXT, " +
                "Age INTEGER, " +
                "Gender TEXT, " +
                "DrinkCnt INTEGER, " +
                "AlcoholContent REAL, " +
                "SaveTime TEXT)";

        db.execSQL(SQL);
//        String SQL = "CREATE TABLE users (UID integer primary key autoincrement, Name text);";


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void save(String name, String birthPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("BirthPass", birthPass);
        db.insert("users", null, values);
    }

    public void insertAll(String name, String birthPass, int age, String gender, int drinkCnt, double alcoholContent, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", name);
        values.put("BirthPass", birthPass);
        values.put("Age", age);
        values.put("Gender", gender);
        values.put("DrinkCnt", drinkCnt);
        values.put("AlcoholContent", alcoholContent);
        values.put("SaveTime", date);

        db.insert("users", null, values);
//        String SQL = "INSERT INTO users(Name, BirthPass, Age, Gender, DrinkCnt, AlcoholContent, SaveTime) VALUES('" + name + birthPass + age + gender + drinkCnt + alcoholContent + date + "')";

//        db.execSQL(SQL);
    }

//    public String selectName(String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String SQL = "SELECT * FROM users WHERE Name='" + name + "'";
//        Cursor result = db.rawQuery(SQL, null);
//
//        StringBuilder str = new StringBuilder();
//        try {
//            if (result != null) {
//                int uidIndex = result.getColumnIndex("UID");
//                int nameIndex = result.getColumnIndex("Name");
//                int birthPassIndex = result.getColumnIndex("BirthPass");
//                int ageIndex = result.getColumnIndex("Age");
//                int genderIndex = result.getColumnIndex("Gender");
//                int drinkCntIndex = result.getColumnIndex("DrinkCnt");
//                int alcoholContentIndex = result.getColumnIndex("AlcoholContent");
//                int dateIndex = result.getColumnIndex("SaveTime");
//
//                // 컬럼 인덱스가 유효한지 확인
//                if (uidIndex != -1 && nameIndex != -1) {
//                    while (result.moveToNext()) {
//                        str.append("번호 :").append(result.getString(uidIndex)).append("\n")
//                                .append("이름 :").append(result.getString(nameIndex)).append("\n")
//                                .append("생년 비밀 :").append(result.getString(birthPassIndex)).append("\n")
//                                .append("나이 :").append(result.getString(ageIndex)).append("\n")
//                                .append("성별 :").append(result.getString(genderIndex)).append("\n")
//                                .append("잔 수 :").append(result.getString(drinkCntIndex)).append("\n")
//                                .append("도수 :").append(result.getString(alcoholContentIndex)).append("\n")
//                                .append("날짜 :").append(result.getString(dateIndex));
//                    }
//                }
//            }
//        } finally {
//            // Cursor가 null이 아니면 닫기
//            if (result != null) {
//                result.close();
//            }
//        }
//
//        return str.toString();
//    }
    public List<Map<String, String>> selectName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "SELECT * FROM users WHERE Name='" + name + "'";
        Cursor result = db.rawQuery(SQL, null);

        List<Map<String, String>> rowDataList = new ArrayList<>();
        try {
            if (result != null && result.moveToFirst()) {
                do {
                    Map<String, String> rowData = new HashMap<>();
                    rowData.put("UID", result.getString(result.getColumnIndex("UID")));
                    rowData.put("Name", result.getString(result.getColumnIndex("Name")));
                    rowData.put("BirthPass", result.getString(result.getColumnIndex("BirthPass")));
                    rowData.put("Age", result.getString(result.getColumnIndex("Age")));
                    rowData.put("Gender", result.getString(result.getColumnIndex("Gender")));
                    rowData.put("DrinkCnt", result.getString(result.getColumnIndex("DrinkCnt")));
                    rowData.put("AlcoholContent", result.getString(result.getColumnIndex("AlcoholContent")));
                    rowData.put("SaveTime", result.getString(result.getColumnIndex("SaveTime")));

                    rowDataList.add(rowData);
                } while (result.moveToNext());
            }
        } finally {
            if (result != null) {
                result.close();
            }
        }

        return rowDataList;
}


}

