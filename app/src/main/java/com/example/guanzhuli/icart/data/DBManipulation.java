package com.example.guanzhuli.icart.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class DBManipulation {
    DBHelper mDBHelper;
    SQLiteDatabase mSQLiteDatabase;
    Context context;
    public DBManipulation(Context context, String tablename) {
        this.context = context;
        mDBHelper = new DBHelper(context, tablename);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }
    public void insert(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.ITEMID, item.id);
        contentValues.put(mDBHelper.ITEMNAME, item.name);
        contentValues.put(mDBHelper.IMAGEURL, item.imageUrl);
        contentValues.put(mDBHelper.QUANTITY, item.quantity);
        contentValues.put(mDBHelper.PRICE,item.price);
        long i = mSQLiteDatabase.insert(mDBHelper.TABLENAME, null, contentValues);
        if (i > -1) {
            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add Failed. Already existed", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(String id, int quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(mDBHelper.QUANTITY, quantity);
        int res = mSQLiteDatabase.update(mDBHelper.TABLENAME,
                contentValues, mDBHelper.ITEMID + " = ?",
                new String[] {id});
        if (res > 0) {
            Toast.makeText(context, "Update successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Update failed, this id does not exist", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(String id) {
        int mark = mSQLiteDatabase.delete(mDBHelper.TABLENAME,mDBHelper.ITEMID + " = ?", new String[] {id});
        if(mark > 0) {
            Toast.makeText(context, "remove successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "remove failed", Toast.LENGTH_LONG).show();
        }
    }

    public List<Item> selectAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.rawQuery("select * from " + mDBHelper.TABLENAME, null);
        // make sure it start the first, before traverse the table
        cursor.moveToFirst();
        // Cursor: like iteration, it has pointer!
        while (true) {
            String id = cursor.getString(cursor.getColumnIndex(mDBHelper.ITEMID));
            String name = cursor.getString(cursor.getColumnIndex(mDBHelper.ITEMNAME));
            String imageUrl = cursor.getString(cursor.getColumnIndex(mDBHelper.IMAGEURL));
            int quant = cursor.getInt(cursor.getColumnIndex(mDBHelper.QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndex(mDBHelper.PRICE));
            Item temp = new Item(id, name,imageUrl, price, quant);
            result.add(temp);
            if (cursor.moveToNext()) {
                continue;
            } else {
                break;
            }
        }
        return result;
    }
}
