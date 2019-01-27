package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ideas_DB";
    public static final String TABLE_IDEAS = "Ideas";
    public static final String KEY_ID = "ID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SHORT = "shortdesc";
    public static final String KEY_LONG = "longdesc";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_DISLIKES = "dislikes";
    private String TAG = "DataBaseHelper";

    public DataBaseHelper(Context context, String from) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        if (from == "login" || from == "regin") {
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='MyUser';", null);
            if (cursor.getCount() != 0) {
                db.execSQL("drop table MyUser;");
                db.execSQL("drop table Ideas;");
            }
        }
        this.onCreate(db);
    }

    void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table MyUser;");
        db.execSQL("drop table Ideas;");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Ideas (ID INTEGER PRIMARY KEY AUTOINCREMENT, title text, shortdesc text, longdesc text, author integer, image text, likes integer, dislikes integer);");
        db.execSQL("create table if not exists MyUser (ID INTEGER PRIMARY KEY AUTOINCREMENT, token text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    boolean insertIdea(Idea idea) {
        Log.e(TAG, "IN");
        List<Idea> ideas = this.getAll();
        Log.e(TAG, "GotList");
        for (Idea x : ideas) {
            if (idea.equals(x)) return false;
        }
        Log.e(TAG, "Runned");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Ideas", null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, idea.getId() + cursor.getCount() + 1);
        contentValues.put(KEY_TITLE, idea.getTitle());
        contentValues.put(KEY_SHORT, idea.getShortdesc());
        contentValues.put(KEY_LONG, idea.getLongdesc());
        contentValues.put(KEY_AUTHOR, idea.getAuthor());
        contentValues.put(KEY_IMAGE, idea.getImage());
        contentValues.put(KEY_LIKES, idea.getLikes());
        contentValues.put(KEY_DISLIKES, idea.getDislikes());
        db.insert(TABLE_IDEAS, null, contentValues);
        db.close();
        return true;
    }

    boolean insertToken(String token) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, 1);
        contentValues.put("token", token);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select ID from MyUser where ID = 1;", null);
        if (cursor.getCount() == 0) {
            db.insert("MyUser", null, contentValues);
        }
        return true;
    }
    String getToken(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from MyUser where ID = 1", null);
        res.moveToFirst();
        return res.getString(res.getColumnIndex("token"));
    }
    boolean isToken() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select ID from MyUser where ID = 1;", null);
        if (cursor.getCount() == 0) {
            return false;

        }
        return true;
    }
    Idea parse(Cursor cursor) {
        cursor.moveToFirst();
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID)));
        String title = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
        String shortdesc = cursor.getString(cursor.getColumnIndex(KEY_SHORT));
        String longdesc = cursor.getString(cursor.getColumnIndex(KEY_LONG));
        int author = Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_AUTHOR)));
        String image = cursor.getString(cursor.getColumnIndex(KEY_IMAGE));
        return new Idea(this.getToken(), id, title, shortdesc, longdesc, author, image);
    }

    Idea getIdeaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Ideas where ID = " + id, null);
        return parse(res);
    }

    List<Idea> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.e(TAG, "Opened");
        ArrayList<Idea> ideas = new ArrayList<Idea>();
        Cursor cursor = db.rawQuery("select * from Ideas", null);
        cursor.getCount();
        for (int i = 1; i <= cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            ideas.add(parse(cursor));
        }
        return ideas;
    }
}
