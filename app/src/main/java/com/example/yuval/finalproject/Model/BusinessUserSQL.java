package com.example.yuval.finalproject.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yuval on 14/06/2017.
 */

public class BusinessUserSQL {
    static final String USER_TABLE = "users";
    static final String USER_ID = "userId";
    static final String USER_NAME = "name";
    static final String USER_IMAGE_URL = "imageUrl";

    static List<BusinessUser> getAllStudents(SQLiteDatabase db) {
        Cursor cursor = db.query(USER_TABLE, null, null, null, null, null, null);
        List<BusinessUser> list = new LinkedList<BusinessUser>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int nameIndex = cursor.getColumnIndex(USER_NAME);
            int imageUrlIndex = cursor.getColumnIndex(USER_IMAGE_URL);

            do {
                BusinessUser user = new BusinessUser();
                user.setUserId( cursor.getString(idIndex));
                user.setfirstName(cursor.getString(nameIndex));
                user.setImages(cursor.getString(imageUrlIndex));
                list.add(user);
            } while (cursor.moveToNext());
        }
        return list;
    }

    static void addUser(SQLiteDatabase db, BusinessUser user) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(USER_NAME, user.getfirstName());
        values.put(USER_IMAGE_URL, user.getImages());
        db.insert(USER_TABLE, USER_ID, values);
    }

    static BusinessUser getUser(SQLiteDatabase db, String stId) {
        String[] whereArgs = new String[] {
                stId
        };
        Cursor cursor = db.query(USER_TABLE, null,USER_ID+"=?",whereArgs,null,null,null,"1");
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int nameIndex = cursor.getColumnIndex(USER_NAME);
            int imageUrlIndex = cursor.getColumnIndex(USER_IMAGE_URL);

            BusinessUser user = new BusinessUser();
            user.setUserId( cursor.getString(idIndex));
            user.setfirstName(cursor.getString(nameIndex));
            user.setImages(cursor.getString(imageUrlIndex));

            return user;
        }
        return null;
    }
    static void deleteUser(SQLiteDatabase db,BusinessUser user) {
        String[] whereArgs = new String[] {
                user.getUserId()
        };
        db.delete(USER_TABLE,USER_ID+"=?",whereArgs);
    }

    public static void addUsersToDB(SQLiteDatabase db, List<BusinessUser> userList){
        for(BusinessUser user : userList)
            addUser(db, user);
    }

    public static void updateUser(SQLiteDatabase db,BusinessUser user){
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(USER_NAME, user.getfirstName());
        values.put(USER_IMAGE_URL, user.getImages());

        String[] whereArgs = new String[] {
                user.getUserId()
        };
        db.update(USER_TABLE, values,USER_ID+"=?",whereArgs);
    }

    static public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE +
                " (" +
                USER_ID + " TEXT PRIMARY KEY, " +
                USER_NAME + " TEXT, " +
                USER_IMAGE_URL + " TEXT);");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + USER_TABLE + ";");
        onCreate(db);
    }
}
