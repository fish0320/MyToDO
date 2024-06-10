package com.example.mytd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "mytodo.db";
    public static final String TB_NAME = "tb_tasklist";
    public static final String TB_NAME_2 = "tb_detail";
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,DETAIL TEXT,START TEXT,TIMES INTEGER,STATE TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME_2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,CONTENT TEXT,DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
