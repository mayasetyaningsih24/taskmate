package com.example.taskmate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDBHelper(context: Context) : SQLiteOpenHelper(context, "taskmate.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, deadline TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS tasks")
        onCreate(db)
    }

    fun insertTask(title: String, desc: String, deadline: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("description", desc)
            put("deadline", deadline)
        }
        return db.insert("tasks", null, values)
    }


    fun getAllTasks(): ArrayList<Task> {
        val list = ArrayList<Task>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tasks", null)
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
                list.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun updateTask(id: Int, title: String, desc: String, deadline: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", title)
            put("description", desc)
            put("deadline", deadline)
        }
        db.update("tasks", values, "id=?", arrayOf(id.toString()))
        db.close()
    }



    fun deleteTask(id: Int): Int {
        val db = writableDatabase
        return db.delete("tasks", "id=?", arrayOf(id.toString()))
    }
}
