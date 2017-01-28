package com.inflexionlabs.mobilejrrealcaseboilerplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by diegonunezguzman on 26/01/17.
 */

public class User {

    public static final String USERKEY="cve";
    public static final String USERNAME="usuario";
    public static final String PASSWORD="password";

    public static final String DATABASENAME="test";
    public static final String TABLENAME="user";

    public static final int DATABASEVERSION=1;

    private SQLiteDatabase sqlLite;
    private final Context context;
    private DBHelper helper;

    public static class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context,DATABASENAME,null,DATABASEVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql=" CREATE TABLE "+TABLENAME+"("+USERKEY+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +USERNAME+"TEXT NOT NULL,"
                    +PASSWORD+"TEXT NOT NULL);";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
            onCreate(db);
        }
    }

    public User(Context context){
        this.context=context;
    }

    public User openDb()throws Exception{
        helper= new DBHelper(this.context);
        sqlLite=helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }

    public long insertUser(String userName,String password){
        ContentValues cv=new ContentValues();
        cv.put(USERNAME,userName);
        cv.put(PASSWORD,password);

        return sqlLite.insert(TABLENAME,null,cv);
    }

    public String getUser(){
        String []cols=new String[] {USERKEY,USERNAME,PASSWORD};

        Cursor c=sqlLite.query(TABLENAME,cols,null,null,null,null,null);
        String row="";

        int userNameInd=c.getColumnIndex(USERNAME);
        int userPasswdInd=c.getColumnIndex(PASSWORD);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            row+="Usuario: "+c.getString(userNameInd)+" \n "+"Password: "+c.getString(userPasswdInd);
        }

        return row;
    }
}

