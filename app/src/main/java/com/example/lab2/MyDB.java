package com.example.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }

    //    public MyDB(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }
    public static final String TableName = "ContactTable";
    public static final String id = "Id";
    public static final String name = "Fullname";
    public static final String phone = "PhoneNumber";
    public static final String email = "Email";
    public static final String image_path = "Image";
    public static final String status = "Status";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "(" +
                id + " Integer Primary key, "
                + name + " Text, "
                + phone + " Text, "
                + email + " Text, "
                + status + " Integer, "
                + image_path + " Text)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xoa bang da co
        db.execSQL("Drop table if exists " + TableName);
        //tao lai
        onCreate(db);
    }

    // them 1 contact vao table
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, contact.getId());
        values.put(name, contact.getName());
        values.put(phone, contact.getPhonenumber());
        values.put(email, contact.getEmail());
        values.put(status, contact.isStatus()==true?1:0);
        values.put(image_path, contact.getImagePath());
        db.insert(TableName, null, values);
        db.close();

    }

    // lay tat ca cac dông cua bang TableContact tra ve dang arraylisst
    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> list = new ArrayList<>();
        // cau truy van
        String sql = "Select * from " + TableName;
        //Lay doi tuong csdl sqlLite
        SQLiteDatabase db = this.getReadableDatabase();
        // Chay cau truy van tra ve cursor
        Cursor cursor = db.rawQuery(sql, null);
        //Tao ArrayList<Contact> de tra ve
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Contact c = new Contact(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)==1?true:false, cursor.getString(5));
                list.add(c);
            }
        }
        return list;
    }
    public void updateContact(int Id, Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(id, contact.getId());
        values.put(name, contact.getName());
        values.put(phone, contact.getPhonenumber());
        values.put(email, contact.getEmail());
        values.put(status, contact.isStatus()==true?1:0);
        values.put(image_path, contact.getImagePath());
        db.update(TableName, values, id + "=?", new String[]{String.valueOf(Id)});
        db.close();
        Log.d("k", "updateContact: "+Id);

    }
    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="Delete from " + TableName+ " Where Id = " + id;
        db.execSQL(sql);
        db.close();
    }
    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, null, null);
        db.close();
    }

}
