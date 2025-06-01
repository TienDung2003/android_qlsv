package com.example.quan_ly_sv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "StudentDB", null, 1) {

    private val TABLE_NAME = "students"

    override fun onCreate(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                mssv TEXT,
                email TEXT,
                phone TEXT
            )
        """.trimIndent()
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateStudent(student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.update(TABLE_NAME, values, "id=?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
    }

    fun getAllStudents(): MutableList<Student> {
        val list = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            list.add(
                Student(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    mssv = cursor.getString(2),
                    email = cursor.getString(3),
                    phone = cursor.getString(4)
                )
            )
        }
        cursor.close()
        return list
    }
}
