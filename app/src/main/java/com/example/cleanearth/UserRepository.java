package com.example.cleanearth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {
    private final SQLiteDatabase db;

    public UserRepository(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean signUp(String email, String password, String name, String gender, String birthDate) {
        Cursor cursor = db.query("users", null, "email=?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return false; // 이미 가입됨
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        values.put("name", name);
        values.put("gender", gender);
        values.put("birthDate", birthDate);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public boolean login(String email, String password) {
        Cursor cursor = db.query("users", null, "email=? AND password=?", new String[]{email, password}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public Cursor getProfile(String email) {
        return db.query("users", null, "email=?", new String[]{email}, null, null, null);
    }
}