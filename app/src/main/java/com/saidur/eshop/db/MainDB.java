package com.saidur.eshop.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saidur.eshop.model.ModelCart;
import com.saidur.eshop.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;


public class MainDB extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "saidur.eShop.db";
    private static final String TABLE_CART = "tbl_cart";
    private static final String TABLE_WISHLIST = "tbl_wishlist";
    // CART Table - column names
    private static final String KEY_CART_ID = "cart_id";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_QUANTITY = "cart_quantity";
    private static final String KEY_VARIATION = "cart_variation";
    private static final String KEY_VARIATION_ID = "cart_variation_id";
    private static final String KEY_PRODUCT = "cart_product";
    private static final String KEY_BUY_NOW = "is_buy_now";

    private static final String CREATE_TABLE_CART = "CREATE TABLE "
            + TABLE_CART + "(" + KEY_CART_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRODUCT_ID + " TEXT," + KEY_QUANTITY + " INTEGER,"
            + KEY_PRODUCT + " TEXT,"
            + KEY_BUY_NOW + " INTEGER" + ")";

    private static final String KEY_WISHLIST_ID = "whishlist_id";
    private static final String KEY_WISHLIST_PRODUCT = "wishlist_product";
    private static final String CREATE_TABLE_WISHLIST = "CREATE TABLE "
            + TABLE_WISHLIST + "(" + KEY_WISHLIST_ID + " INTEGER PRIMARY KEY," + KEY_WISHLIST_PRODUCT
            + " TEXT," + KEY_PRODUCT_ID
            + " TEXT" + ")";

    public MainDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String tbl_cart = "Create Table if not exists "
            + TABLE_CART + "(" + KEY_CART_ID + " Integer primary key, " +
            ""+KEY_PRODUCT_ID+" TEXT,"+ KEY_QUANTITY + " Integer," + KEY_PRODUCT + " TEXT," + KEY_BUY_NOW +" Integer)";

    String tblRegion = "Create Table if not exists tblRegion (pk Integer primary key, " +
            "RegionId Integer,RegionName varchar(500),GroupId Integer)";
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(tbl_cart);
        db.execSQL(CREATE_TABLE_WISHLIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        onCreate(db);
    }

    @SuppressLint("Range")
    public List<ModelCart> getFromCart(int buyNow) {

        List<ModelCart> cartList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CART + " Where " + KEY_BUY_NOW + " = " + buyNow;
        Log.e("LOG", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ModelCart cart = new ModelCart();
                cart.setProductid((c.getString(c.getColumnIndex(KEY_PRODUCT_ID))));
                cart.setQuantity((c.getInt(c.getColumnIndex(KEY_QUANTITY))));
                cart.setProduct((c.getString(c.getColumnIndex(KEY_PRODUCT))));
                cart.setBuyNow((c.getInt(c.getColumnIndex(KEY_BUY_NOW))));
                cart.setCartId((c.getInt(c.getColumnIndex(KEY_CART_ID))));
                cartList.add(cart);
            } while (c.moveToNext());
        }

        if (db.isOpen())
            db.close();
        return cartList;
    }




    @SuppressLint("Range")
    public List<ModelProduct> getProductFromCartById(String product_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CART + " WHERE "
                + KEY_PRODUCT_ID + " = '" + product_id + "'";

        Log.e("LOG", selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                ModelCart cart = new ModelCart();
                cart.setProductid((c.getString(c.getColumnIndex(KEY_PRODUCT_ID))));
                cart.setQuantity((c.getInt(c.getColumnIndex(KEY_QUANTITY))));
                cart.setProduct((c.getString(c.getColumnIndex(KEY_PRODUCT))));
                cart.setBuyNow((c.getInt(c.getColumnIndex(KEY_BUY_NOW))));
                try {
                    List<ModelProduct> plist = new Gson().fromJson(cart.getProduct(), new TypeToken<ModelProduct>() {
                    }.getType());
                    if (db.isOpen())
                        db.close();
                    return plist;
                } catch (Exception e) {
                    Log.e("Gson Exception", "in Recent Product Get" + e.getMessage());
                }
            } while (c.moveToNext());
        }

        if (db.isOpen())
            db.close();
        return null;
    }

    public int updateQuantity(int quantity, String product_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, product_id);
        values.put(KEY_QUANTITY, quantity);

        // updating row
        return db.update(TABLE_CART, values, KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(product_id)});
    }

    public void deleteFromBuyNow(String productid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_BUY_NOW + " = ?",
                new String[]{"1"});
        if (db.isOpen())
            db.close();
    }

    public void deleteFromCart(String productid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(productid)});
    }

    public long addToCart(ModelCart cart) {
        if (cart.getBuyNow() == 1) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_PRODUCT_ID, cart.getProductid());
            values.put(KEY_QUANTITY, cart.getQuantity());

            values.put(KEY_PRODUCT, cart.getProduct());
            values.put(KEY_BUY_NOW, cart.getBuyNow());

            // insert row
            long cart_id = db.insert(TABLE_CART, null, values);
            if (db.isOpen())
                db.close();
            return cart_id;
        } else {
            if (getProductFromCart(cart)) {
                return updateCart(cart);
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_PRODUCT_ID, cart.getProductid());
                values.put(KEY_QUANTITY, cart.getQuantity());
                values.put(KEY_PRODUCT, cart.getProduct());
                values.put(KEY_BUY_NOW, cart.getBuyNow());
                // insert row
                long cart_id = db.insert(TABLE_CART, null, values);
                if (db.isOpen())
                    db.close();
                return cart_id;
            }
        }
    }

    public boolean getProductFromCart(ModelCart cart) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CART + " WHERE "
                + KEY_PRODUCT_ID + " = '" + cart.getProductid() + "'";


        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            return c.moveToFirst();
        }

        if (db.isOpen())
            db.close();
        return false;
    }


    public int updateCart(ModelCart cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, cart.getProductid());
        values.put(KEY_PRODUCT, cart.getProduct());
        values.put(KEY_BUY_NOW, cart.getBuyNow());

        // updating row
        return db.update(TABLE_CART, values, KEY_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(cart.getProductid())});
    }
}
