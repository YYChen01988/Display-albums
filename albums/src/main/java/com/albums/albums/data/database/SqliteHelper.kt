package com.albums.albums.data.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.albums.albums.data.model.AlbumItem

class SqliteHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "album.db"
        private const val DATABASE_VERSION = 1
         const val ALBUM_TABLE = "albums"
        private const val ID = "id"
        private const val USER_ID = "user_id"
        private const val TITLE = "title"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createAlbum = ("CREATE TABLE $ALBUM_TABLE (" +
                "$ID INTEGER PRIMARY KEY," +
                "$USER_ID INTEGER NOT NULL," +
                "$TITLE TEXT NOT NULL UNIQUE" +
                ");")
        db?.execSQL(createAlbum)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $ALBUM_TABLE")
        onCreate(db)
    }

    fun insertAlbums(albums: List<AlbumItem>): Long {
        val db = this.writableDatabase
        onUpgrade(db = db, 0,0)
        val contentValues = ContentValues()
        var success = 0L
        albums.forEach { albumItem ->
            contentValues.put(ID, albumItem.id)
            contentValues.put(USER_ID, albumItem.userId)
            contentValues.put(TITLE, albumItem.title)
            success = db.insert(ALBUM_TABLE, null, contentValues)
        }
        return success
    }

    fun getAllAlbums(): List<AlbumItem> {
        var albums: List<AlbumItem> = listOf()
        var result = mutableListOf<AlbumItem>()
        val sqlQuery = "SELECT * FROM $ALBUM_TABLE;"
        val db = this.writableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(sqlQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(sqlQuery)
            return albums
        }

        var id: Int
        var userId: Int
        var title: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(ID))
                userId = cursor.getInt(cursor.getColumnIndex(USER_ID))
                title = cursor.getString(cursor.getColumnIndex(TITLE))
                val album = AlbumItem(userId = userId, id = id, title = title)
                result.add(album)
            } while (cursor.moveToNext())
        }
        return result.toList()
    }
}