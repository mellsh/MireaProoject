package com.example.cleanearth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {
    private DBHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    // 회원가입 - 이메일/비번/개인정보 모두 저장
    public boolean signUp(String email, String password, String name, String gender, String birthDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("name", name);
        values.put("gender", gender);
        values.put("birthDate", birthDate);
        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    // 로그인
    public boolean login(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean loggedIn = cursor.moveToFirst();
        cursor.close();
        db.close();
        return loggedIn;
    }

    // 이메일로 개인정보 조회
    public Cursor getProfile(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query("users", null, "email=?", new String[]{email}, null, null, null);
    }
}