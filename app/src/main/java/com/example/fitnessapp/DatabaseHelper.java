package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME = "distance_database";
    private static  final String TABLE_NAME = "distance_table";

    private static  final String COL_2 = "stepstaken";

    //creates database when constructor gets called
    public DatabaseHelper(Context context){
        super(context,TABLE_NAME, null, 1);
    }


    //creates table and rows
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public boolean addDate(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //checks to see if data was inserted correctly
        if(result == -1){
            return  false;

        }else{
            return true;
        }


    }

    //uses cursor to get data from table.
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}

