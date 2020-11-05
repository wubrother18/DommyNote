package com.example.dummynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB {

    private static final String DATABASE_NAME="notes.db";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_TABLE="notes";
    private static final String CREATE_TABLE=
            "CREATE TABLE notes(_id INTEGER PRIMARY KEY,note TEXT,created INTEGER);";
    private static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(@Nullable Context context) {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }

    private Context mCtx;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DB(Context context){
        mCtx=context;
    }

    public  DB open() throws SQLException{
        dbHelper=new DBHelper(mCtx);

        db=dbHelper.getWritableDatabase();

        return  this;
    }

    public void close(){
        if(dbHelper!=null)
        dbHelper.close();
    }

    public Cursor getAll(){
        return  db.rawQuery("SELECT * FROM notes",null);
    }

    public void replace(int id,String note,int created){
        ContentValues cv = new ContentValues();
       // cv.put("_id",id);
        cv.put("note",note);
        cv.put("created",created);
        try{
            db.replace(DATABASE_TABLE,null,cv);
        } catch (Exception e) {
            Toast.makeText(mCtx,"建立失敗",Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(){
        db.delete(DATABASE_TABLE,"_id > 1",null);
    }
}
