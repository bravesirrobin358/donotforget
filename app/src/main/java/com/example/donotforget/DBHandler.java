package com.example.donotforget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.donotforget.databinding.FragmentFirstBinding;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "messages";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_DATE = "date";
    private static final String DATABASE_NAME = "messages.db";
    private static final int DATABASE_VERSION = 1;

    ArrayList<String> messageList;

    public DBHandler(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){
        String create_table_cmd = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY, " +
                COLUMN_MESSAGE + " TEXT, " +
                COLUMN_DATE + " DATE " + ")";

        db.execSQL(create_table_cmd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public void addMessage(Message message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Date date = message.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        values.put(COLUMN_MESSAGE, message.getMessage());
        values.put(COLUMN_DATE, sdf.format(date));
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public boolean deleteMessage(String message){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MESSAGE + " = \"" + message + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() == 0){
            db.close();
            cursor.close();
            return false;
        }
        if(cursor.moveToFirst()){
            String idStr = cursor.getString(1);
            db.delete(TABLE_NAME, "name=?", new String[]{idStr});
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }



}