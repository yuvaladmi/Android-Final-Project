package com.example.yuval.finalproject.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yuval on 10/06/2017.
 */

public class ModelSQL  extends SQLiteOpenHelper {
    ModelSQL(Context context) {

        super(context, "database.db", null, 1);
        Log.d("TAG","context =="+context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BusinessUserSQL.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BusinessUserSQL.onUpgrade(db, oldVersion, newVersion);
    }
}
