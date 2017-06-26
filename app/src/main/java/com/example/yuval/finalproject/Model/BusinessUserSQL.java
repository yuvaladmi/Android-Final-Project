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
    static final String USER_IMAGE_BITMAP = "imageBitMap";
    static final String USER_IS_BUSINESS = "isBusiness";
    static final String USER_GEL_NAIL = "GelNail";
    static final String USER_LASER_HAIR = "LaserHair";

    static List<BusinessUser> getAllStudents(SQLiteDatabase db) {
        Cursor cursor = db.query(USER_TABLE, null, null, null, null, null, null);
        List<BusinessUser> list = new LinkedList<BusinessUser>();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_ID);
            int nameIndex = cursor.getColumnIndex(USER_NAME);
            int imageUrlIndex = cursor.getColumnIndex(USER_IMAGE_URL);
            int imageBitMapIndex = cursor.getColumnIndex(USER_IMAGE_BITMAP);
            int isBusinessIndex=cursor.getColumnIndex(USER_IS_BUSINESS);
            int GelNailIndex =cursor.getColumnIndex(USER_GEL_NAIL);
            int LaserHairIndex =cursor.getColumnIndex(USER_LASER_HAIR);

            do {
                BusinessUser user = new BusinessUser();
                user.setUserId( cursor.getString(idIndex));
                user.setfirstName(cursor.getString(nameIndex));
                user.setImages(cursor.getString(imageUrlIndex));
                user.setImageBitMap(cursor.getBlob(imageBitMapIndex));
                if(cursor.getInt(isBusinessIndex)==1)
                    user.setBusiness(true);
                else
                    user.setBusiness(false);

                if(cursor.getInt(GelNailIndex)==1)
                    user.setGelNail(true);
                else
                    user.setGelNail(false);

                if(cursor.getInt(LaserHairIndex)==1)
                    user.setLaserHair(true);
                else
                    user.setLaserHair(false);

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
        values.put(USER_IS_BUSINESS,user.getBusiness());
        values.put(USER_GEL_NAIL,user.getGelNail());
        values.put(USER_LASER_HAIR,user.getLaserHair());
        if(user.getImageBitMap() != null)
            values.put(USER_IMAGE_BITMAP, user.getImageBitMap().toString());
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
            int imageBitMapIndex = cursor.getColumnIndex(USER_IMAGE_BITMAP);

            int isBusinessIndex=cursor.getColumnIndex(USER_IS_BUSINESS);
            int GelNailIndex =cursor.getColumnIndex(USER_GEL_NAIL);
            int LaserHairIndex =cursor.getColumnIndex(USER_LASER_HAIR);

            BusinessUser user = new BusinessUser();
            user.setUserId( cursor.getString(idIndex));
            user.setfirstName(cursor.getString(nameIndex));
            user.setImages(cursor.getString(imageUrlIndex));
            user.setImageBitMap(cursor.getBlob(imageBitMapIndex));
            if(cursor.getInt(isBusinessIndex)==1)
                user.setBusiness(true);
            else
                user.setBusiness(false);

            if(cursor.getInt(GelNailIndex)==1)
                user.setGelNail(true);
            else
                user.setGelNail(false);

            if(cursor.getInt(LaserHairIndex)==1)
                user.setLaserHair(true);
            else
                user.setLaserHair(false);

            return user;
        }
        return null;
    }

    public static boolean CheckIsDataAlreadyInDBorNot(SQLiteDatabase db, String fieldValue) {
        String Query = "Select "+USER_ID+" from " + USER_TABLE + " where " + USER_ID + " = ?";
        Cursor cursor = db.rawQuery(Query, new String[]{fieldValue});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public static void updateUser(SQLiteDatabase db,BusinessUser user){
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(USER_NAME, user.getfirstName());
        values.put(USER_IMAGE_URL, user.getImages());
        if (user.getBusiness())
            values.put(USER_IS_BUSINESS,1);
        else
            values.put(USER_IS_BUSINESS,0);

        if (user.getGelNail())
            values.put(USER_GEL_NAIL,1);
        else
            values.put(USER_GEL_NAIL,0);

        if (user.getLaserHair())
            values.put(USER_LASER_HAIR,1);
        else
            values.put(USER_LASER_HAIR,0);

        if(user.getImageBitMap() != null)
            values.put(USER_IMAGE_BITMAP, user.getImageBitMap());
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
                USER_IMAGE_BITMAP + " BLOB, " +
                USER_IS_BUSINESS +" INTEGER, "+
                USER_GEL_NAIL +" INTEGER, "+
                USER_LASER_HAIR +" INTEGER, "+
                USER_IMAGE_URL + " TEXT);");
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop " + USER_TABLE + ";");
        onCreate(db);
    }
}
