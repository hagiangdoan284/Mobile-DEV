package com.example.bt2sqlite.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bt2sqlite.model.Nguoidung

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Bai2.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "bai2SQLite"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertContact(nguoidung: Nguoidung): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nguoidung.name)
            put(COLUMN_PHONE, nguoidung.phone)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun updateContact(nguoidung: Nguoidung): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nguoidung.name)
            put(COLUMN_PHONE, nguoidung.phone)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(nguoidung.id.toString()))
        db.close()
        return result > 0
    }

    fun deleteContact(contactId: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(contactId.toString()))
        db.close()
        return result > 0
    }

    fun getAllContacts(): List<Nguoidung> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val contacts = mutableListOf<Nguoidung>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val ten = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val sdt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                contacts.add(Nguoidung(id, ten, sdt))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contacts
    }
}
