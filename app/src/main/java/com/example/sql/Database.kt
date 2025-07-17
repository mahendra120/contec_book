package com.example.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val creatable =
            "create table user (id integer primary key autoincrement , name text , surname text , company text, mobile text , email text , address text , notes text)"
        db.execSQL(creatable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {

    }

    fun insertData(
        name: String,
        surname: String,
        company: String,
        mobile: String,
        email: String,
        address: String,
        notes: String
    ) {
        val query =
            "insert into user (name,surname,company,mobile,email,address,notes) values ('$name','$surname','$company','$mobile','$email','$address','$notes')"
        val db = this.writableDatabase
        db.execSQL(query)
    }


}