package com.example.guanzhuli.icart.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBManipulation {
    DBHelper mDBHelper;
    SQLiteDatabase mSQLiteDatabase;
    Context context;
    DBManipulation(Context context, String tablename) {
        this.context = context;
        mDBHelper = new DBHelper(context, tablename);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }
    public void insert(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.ITEMID, item.id);
        contentValues.put(mDBHelper.ITEMNAME, item.name);
        contentValues.put(mDBHelper.QUANTITY, item.quantity);
        contentValues.put(mDBHelper.PRICE,item.price);
        long i = mSQLiteDatabase.insert(mDBHelper.TABLENAME, null, contentValues);
        if (i > -1) {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.ITEMNAME, item.name);
        contentValues.put(mDBHelper.QUANTITY, item.quantity);
        contentValues.put(mDBHelper.PRICE,item.price);
        int res = mSQLiteDatabase.update(mDBHelper.TABLENAME,
                contentValues, mDBHelper.ITEMID + " = ?",
                new String[] {item.id});
        if (res > 0) {
            Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Update failed, this id does not exist", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(Item item) {
        int mark = mSQLiteDatabase.delete(mDBHelper.TABLENAME,mDBHelper.ITEMID + " = ?", new String[] {item.id});
        if(mark > 0) {
            Toast.makeText(context, "remove successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "remove failed", Toast.LENGTH_LONG).show();
        }
    }
}
