package com.example.aji.joblist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aji on 21/07/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG="DatabaseHelper";
    public static final String TABLE_NAME="Jobs_table";
    public static final String COL1="ID";
    public static final String COL2="Job";

    public DatabaseHelper (Context context){
        super(context,TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable = "CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL2 +" TEXT)";
        db.execSQL(createtable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXIST "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addData (String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,item);

        Log.d(TAG,"addData: Adding "+item+ "to "+TABLE_NAME);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result==-1){return false;}
        else {return true;}
    }
     public Cursor getData(){
         SQLiteDatabase db = this.getWritableDatabase();
         String query = "SELECT * FROM " + TABLE_NAME;
         Cursor data = db.rawQuery(query,null);
         return data;
     }

     public Cursor getItemID(String job){
         SQLiteDatabase db = this.getWritableDatabase();
         String query = "SELECT "+COL1+" FROM "+TABLE_NAME+" WHERE "+COL2+"= '"+job+"'";
         Cursor data = db.rawQuery(query,null);
         return data;
     }
     public void UpdateJob(String job,int id,String oldJob){
         SQLiteDatabase db = this.getWritableDatabase();
         String query = "UPDATE "+TABLE_NAME+" SET "+COL2+" ='"+job+"' WHERE "+ COL1+" ='"+id+"'"+" AND "+COL2+" ='"+oldJob+"'";
         db.execSQL(query);

     }
     public void DeleteJob(int ID, String job){
         SQLiteDatabase db = this.getWritableDatabase();
         String query ="DELETE FROM "+TABLE_NAME+" WHERE "+COL1+" ='"+ID+"'"+" AND "+COL2+" = '"+job+"'";
         db.execSQL(query);

     }
}
