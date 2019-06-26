package com.example.wikipedia.repositories

import com.example.wikipedia.models.WikiPage
import com.example.wikipedia.models.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class FavoritesRepository(val databaseHelper: ArticleDatabaseOpenHelper) {

    private val TABLE_NAME = "Favorites"

    fun addFavorite(page: WikiPage) {
        databaseHelper.use {
            insert(
                TABLE_NAME,
                "id" to page.pageid,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail)
            )
        }
    }

    fun removeFavoriteById(pageId: Int) {
        databaseHelper.use {
            delete(
                TABLE_NAME,
                "id = {pageId}",
                "pageId" to pageId
            )
        }
    }

    fun isArticleFavorite(pageId: Int): Boolean {
        val pages = getAllFavorites()
        return pages.any { page ->
            page.pageid == pageId
        }
    }

    fun getAllFavorites(): ArrayList<WikiPage> {
        val pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser { id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()

            page.pageid = id
            page.title = title
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        databaseHelper.use {
            select(TABLE_NAME).parseList(articleRowParser)
        }

        return pages
    }
}