package com.example.guanzhuli.icart.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBHelper  extends SQLiteOpenHelper {
    public static final String DATABASENAME = "cart";
    public static String TABLENAME;
    public static final String ITEMID = "id";
    public static final String ITEMNAME = "name";
    public static final String IMAGEURL = "image_url";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final int VERSION = 1;

    public DBHelper(Context context, String table) {
        super(context, DATABASENAME, null, VERSION);
        TABLENAME = table;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = " CREATE TABLE " + TABLENAME + "("
                + ITEMID + " INTEGER PRIMARY KEY," + ITEMNAME + " TEXT,"
                + IMAGEURL + " TEXT," + QUANTITY + " INTEGER," + PRICE + " DECIMAL(10,2))";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.v(DBHelper.class.getName(),
                "upgrading database from version "+ i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
