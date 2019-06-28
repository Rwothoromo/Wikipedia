package com.example.wikipedia.receviers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.managers.WikiManager
import com.example.wikipedia.models.WikiPage
import com.google.gson.Gson


class FavoriteBroadcastReceiver : BroadcastReceiver() {

    // get articles from the WikiManager
    var wikiManager: WikiManager? = null

    override fun onReceive(context: Context, intent: Intent) {

        val uri = intent.data
        if (uri != null) {

            // instantiate wikiManager
            wikiManager = (context.applicationContext as WikiApplication).wikiManager

            // get the intended page from the extras
            val wikiPageJson = intent.getStringExtra("page")

            // deserialize the string above into a proper WikiPage model
            val page = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

            try {
                // determine if the article is already a favorite or not
                if (wikiManager!!.getIsFavorite(page!!.pageid!!)) {
                    wikiManager!!.removeFavorite(page.pageid!!)
                    Toast.makeText(context, "Article removed from Favorites!", Toast.LENGTH_SHORT).show()
                } else {
                    wikiManager!!.addFavorite(page)
                    Toast.makeText(context, "Article added to Favorites!", Toast.LENGTH_SHORT).show()
                }
            } catch (exception: Exception) {
                Toast.makeText(context, "Unable to Favorite this Article!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}