package com.example.wikipedia.providers

import com.example.wikipedia.models.Urls
import com.example.wikipedia.models.WikiResult
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.io.Reader

/**
 * Processes data from an external source
 */
class ArticleDataProvider {

    // Set up a user agent so that we don't spam the Wikipedia service!!!
    // We can make as many requests as we need!
    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Eli Pluralsight Wikipedia")
    }

    /**
     * Implementing the search function and adding an extra parameter
     * @param[responseHandler] takes in a `WikiResult`, this is the action to call back to
     */
    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getSearchUrl(term, skip, take)
            .httpGet() // Fuel's extension
            .responseObject(WikipediaDataDeserializer()) { _, response, result ->
                // request, _, _
                if (response.statusCode != 200) throw Exception("Unable to retrieve articles!")

                val (data, _) = result // (WikiResult, FuelError)
                responseHandler.invoke(data as WikiResult) // cast data to the WikiResult
            }
    }

    /**
     * Implementing the getRandomUrl function and adding an extra parameter
     * @param[responseHandler] takes in a `WikiResult`
     */
    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getRandomUrl(take)
            .httpGet()
            .responseObject(WikipediaDataDeserializer()) { _, response, result ->
                if (response.statusCode != 200) throw Exception("Unable to retrieve articles!")

                val (data, _) = result
                responseHandler.invoke(data as WikiResult)
            }
    }

    /**
     * For fuel to know what it is deserializing the data with and what it is using to do it.
     */
    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {

        /**
         * Use gson to parse the string down from the reader
         * down to the object we need (WikiResult model)
         * @param[reader]
         * @return deserialized JSON
         */
        override fun deserialize(reader: Reader): WikiResult? = Gson().fromJson(reader, WikiResult::class.java)

    }
}