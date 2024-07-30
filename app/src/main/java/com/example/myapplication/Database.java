package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "CREATE TABLE USERS(username text, email text, password text)";
        db.execSQL(qry1);

        String qry2 = "CREATE TABLE CART(username text, product text, price float, otype text)";
        db.execSQL(qry2);

        String qry3 = "CREATE TABLE ORDERPLACE(username text, name text, address text, contactno text, pinCode int, date text, time text, price float, otype text)";
        db.execSQL(qry3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register(String username, String email, String password){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }
    public int login(String username, String password){
        int res=0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if (c.moveToFirst()){
            res=1;
        }
        return res;
    }

    public void addToCart(String username, String product, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }
    public int checkCart(String username, String product){
        int result=0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = product;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where username = ? and product = ?", str);
        if(c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;
    }
    public void removeCart(String username, String otype){
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase db = getReadableDatabase();
        db.delete("cart", "username=? and otype=?", str);
        db.close();
    }

    public ArrayList getCartData(String username, String otype ){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?", str);
        if (c.moveToFirst()){
            do{
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product+"$"+price);
            }while (c.moveToNext());
        }
        db.close();;
        return arr;

    }
    public void addOrder(String username, String name, String address, String contact, int pinCode, String date, String time, float price, String ordertype){
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("name", name);
        cv.put("address", address);
        cv.put("contact", contact);
        cv.put("pinCode", pinCode);
        cv.put("date",date);
        cv.put("time", time);
        cv.put("price",price);
        cv.put("otype", ordertype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace", null, cv);
        db.close();
    }
    public ArrayList<String> getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();
            String[] str = new String[]{username};
            c = db.rawQuery("SELECT * FROM orderplace WHERE username = ?", str);
            if (c.moveToFirst()) {
                do {
                    // Ensure the indices match your table schema
                    String orderData = c.getString(1) + "$" + // Adjust index if necessary
                            c.getString(2) + "$" +
                            c.getString(3) + "$" +
                            c.getString(4) + "$" +
                            c.getString(5) + "$" +
                            c.getString(6) + "$" +
                            c.getString(7) + "$" +
                            c.getString(8);
                    arr.add(orderData);
                    Log.d("Database", "Order data retrieved: " + orderData);
                } while (c.moveToNext());
            } else {
                Log.d("Database", "No orders found for user: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Database", "Error retrieving order data: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return arr;
    }

    public int checkAppointmentExists(String username, String name, String address, String contact, String date, String time){
        int result = 0;
        String str[]  = new String[6];
        str[0] = username;
        str[1] = name;
        str[2] = address;
        str[3] = contact;
        str[4] = date;
        str[5] = time;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from orderplace where username = ? and name = ? and address = ? and contactno = ? and date = ? and time = ?", str);
        if (c.moveToFirst()){
            result=1;
        }
        db.close();
        return result;
    }

}
