package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app.db", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE user(id INTEGER PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues().apply {
            put("email", user.email)
            put("pass", user.pass)
        }

        val db = writableDatabase
        db.insert("user", null, values)
        db.close()
    }

    fun getUser(email: String, pass: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM user WHERE email = '$email' AND pass = '$pass'"
        val result = db.rawQuery(query, null)
        val exists = result.moveToFirst()
        result.close()
        return exists
    }
}
