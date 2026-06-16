package com.app.SIAKAD.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.app.SIAKAD.data.model.Mahasiswa

class MahasiswaDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "siakad_mahasiswa.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_MAHASISWA = "mahasiswa"
        private const val COL_ID = "id"
        private const val COL_NIM = "nim"
        private const val COL_NAMA = "nama"
        private const val COL_PRODI = "prodi"
        private const val COL_SEMESTER = "semester"
        private const val COL_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_MAHASISWA (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NIM TEXT NOT NULL UNIQUE,
                $COL_NAMA TEXT NOT NULL,
                $COL_PRODI TEXT NOT NULL,
                $COL_SEMESTER INTEGER NOT NULL,
                $COL_EMAIL TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MAHASISWA")
        onCreate(db)
    }

    fun insertMahasiswa(mahasiswa: Mahasiswa): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NIM, mahasiswa.nim)
            put(COL_NAMA, mahasiswa.nama)
            put(COL_PRODI, mahasiswa.prodi)
            put(COL_SEMESTER, mahasiswa.semester)
            put(COL_EMAIL, mahasiswa.email)
        }
        val result = db.insert(TABLE_MAHASISWA, null, values)
        db.close()
        return result != -1L
    }

    fun getAllMahasiswa(): List<Mahasiswa> {
        val list = mutableListOf<Mahasiswa>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_MAHASISWA ORDER BY $COL_ID DESC",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Mahasiswa(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        nim = cursor.getString(cursor.getColumnIndexOrThrow(COL_NIM)),
                        nama = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)),
                        prodi = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODI)),
                        semester = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SEMESTER)),
                        email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun deleteMahasiswa(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(
            TABLE_MAHASISWA,
            "$COL_ID = ?",
            arrayOf(id.toString())
        )
        db.close()
        return result > 0
    }

    fun getMahasiswaByNim(nim: String): Mahasiswa? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_MAHASISWA WHERE $COL_NIM = ?",
            arrayOf(nim)
        )

        var mahasiswa: Mahasiswa? = null

        if (cursor.moveToFirst()) {
            mahasiswa = Mahasiswa(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                nim = cursor.getString(cursor.getColumnIndexOrThrow(COL_NIM)),
                nama = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)),
                prodi = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODI)),
                semester = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SEMESTER)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
            )
        }

        cursor.close()
        db.close()
        return mahasiswa
    }

    fun getMahasiswaById(id: Int): Mahasiswa? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_MAHASISWA WHERE $COL_ID = ?",
            arrayOf(id.toString())
        )

        var mahasiswa: Mahasiswa? = null

        if (cursor.moveToFirst()) {
            mahasiswa = Mahasiswa(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                nim = cursor.getString(cursor.getColumnIndexOrThrow(COL_NIM)),
                nama = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)),
                prodi = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODI)),
                semester = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SEMESTER)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
            )
        }

        cursor.close()
        db.close()
        return mahasiswa
    }
}