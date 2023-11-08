package com.example.sqltest.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConnect extends SQLiteOpenHelper {

    public SQLiteConnect(@Nullable Context context,
                         @Nullable String name,
                         @Nullable SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }
    // Truy vấn không trả kết quả : Create, Delete, update, insert
    public void queryData(String query){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(query);
    }

    // Truy vấn trả về kết quả : SELECT, ....
    public Cursor getData(String query){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
