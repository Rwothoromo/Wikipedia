package com.example.wikipedia.managers

import com.example.wikipedia.models.WikiPage
import com.example.wikipedia.models.WikiResult
import com.example.wikipedia.providers.ArticleDataProvider
import com.example.wikipedia.repositories.FavoritesRepository
import com.example.wikipedia.repositories.HistoryRepository

class WikiManager(
    private val provider: ArticleDataProvider,
    private val favoritesRepository: FavoritesRepository,
    private val historyRepository: HistoryRepository
) {

    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        return provider.search(term, skip, take, responseHandler)
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        return provider.getRandom(take, responseHandler)
    }

    fun getFavorites(): ArrayList<WikiPage>? {
        if (favoritesCache == null) favoritesCache = favoritesRepository.getAllFavorites()
        return favoritesCache
    }

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null) historyCache = historyRepository.getAllHistory()
        return historyCache
    }

    fun addFavorite(page: WikiPage) {
        favoritesCache?.add(page)
        favoritesRepository.addFavorite(page)
    }

    fun removeFavorite(pageId: Int) {
        favoritesRepository.removeFavoriteById(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int): Boolean {
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addHistory(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        val history = historyRepository.getAllHistory()
        history.forEach { historyRepository.removePageById(it.pageid!!) }
    }

    fun getIsHistory(pageId: Int): Boolean {
        return historyRepository.isArticleInHistory(pageId)
    }
}