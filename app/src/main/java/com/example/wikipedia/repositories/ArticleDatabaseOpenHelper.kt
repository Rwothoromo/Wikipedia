package com.example.wikipedia.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class ArticleDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "ArticlesDatabase.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        // define db tables
        db?.createTable(
            tableName = "Favorites",
            ifNotExists = true,
            columns = *arrayOf(
                "id" to INTEGER + PRIMARY_KEY,
                "title" to TEXT, "url" to TEXT,
                "thumbnailJson" to TEXT
            )
        )

        db?.createTable(
            tableName = "History",
            ifNotExists = true,
            columns = *arrayOf(
                "id" to INTEGER + PRIMARY_KEY,
                "title" to TEXT,
                "url" to TEXT,
                "thumbnailJson" to TEXT
            )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // upgrade table schema if need be
    }
}