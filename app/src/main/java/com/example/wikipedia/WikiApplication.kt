package com.example.wikipedia

import android.app.Application
import android.content.Context
import com.example.wikipedia.managers.WikiManager
import com.example.wikipedia.providers.ArticleDataProvider
import com.example.wikipedia.repositories.ArticleDatabaseOpenHelper
import com.example.wikipedia.repositories.FavoritesRepository
import com.example.wikipedia.repositories.HistoryRepository

/**
 * Inherits from the Android app Application()
 * It can be used to manage some of the lifecycle of the entire application
 * outside of the scope of the activities and fragments
 */
class WikiApplication : Application() {

    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null

    var wikiManager: WikiManager? = null
        // ensure that no other classes are changing wikiManager
        private set

    init {
        instance = this
    }

    companion object {
        private var instance: WikiApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        // This order matters!!!
        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)
    }

}