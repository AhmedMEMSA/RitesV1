package com.example.ahmed.rites.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cisco on 12/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Data.DATABASE_NAME, null, Data.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //create table tablename (column1 dattype ,......)
        String sql = "create table "+Data.FRIENDS_TABLE_NAME +" ("+Data.FKEY_NAME +" text , "+ Data.FKEY_ID +" integer primary key ) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String sql = "drop  table "+Data.FRIENDS_TABLE_NAME;
     db.execSQL(sql);
     onCreate(db);
    }
}
