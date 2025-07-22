package com.example.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sql.ui.theme.User

class DataHelper(context: Context) : SQLiteOpenHelper(context, "data.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val creatable =
            "create table user (id integer primary key autoincrement , name text , surname text , company text, mobile text , email text , address text , notes text , image TEXT)"
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
        notes: String,
        image: String
    ) {
        val query =
            "insert into user (name,surname,company,mobile,email,address,notes,image) values ('$name','$surname','$company','$mobile','$email','$address','$notes', '$image')"
        val db = this.writableDatabase
        db.execSQL(query)
    }

    fun updateUser(
        id: Int,
        name: String,
        surname: String,
        company: String,
        mobile: String,
        email: String,
        address: String,
        notes: String,
        image: String
    ) {

        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("surname", surname)
            put("company", company)
            put("mobile", mobile)
            put("email", email)
            put("address", address)
            put("notes", notes)
            put("image", image)
        }
        db.update("user", values, "id = ?", arrayOf(id.toString()))
    }
    /*
        fun updateUser(
        id: Int,
        name: String,
        surname: String,
        company: String,
        mobile: String,
        email: String,
        address: String,
        notes: String,
        image: String
    ) {
        val query =
            "update user set name = '$name', surname = '$surname', company = '$company', mobile = '$mobile', email = '$email', address = '$address',notes = '$notes' ,image =  '$image' where id = $id"
        val db = this.writableDatabase
        db.execSQL(query)
    }
     */

    fun viewdata(): MutableList<User> {
        val query = "select * from user"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        val list = mutableListOf<User>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val surname = cursor.getString(2)
            val company = cursor.getString(3)
            val mobile = cursor.getString(4)
            val email = cursor.getString(5)
            val address = cursor.getString(6)
            val notes = cursor.getString(7)
            val image = cursor.getString(8)

            val user = User(id, name, surname, company, mobile, email, address, notes, image)
            list.add(user)
        }
        cursor.close()
        return list
    }

    fun deleteUser(id: Int) {
        val query =
            "delete from user where id = $id"
        val db = this.writableDatabase
        db.execSQL(query)
    }

}