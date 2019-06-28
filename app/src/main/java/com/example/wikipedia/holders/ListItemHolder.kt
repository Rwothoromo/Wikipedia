package com.example.wikipedia.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wikipedia.R
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.managers.WikiManager
import com.example.wikipedia.models.WikiPage
import com.example.wikipedia.shared.openUrlInBrowser
import com.squareup.picasso.Picasso

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // get articles from the WikiManager
    var wikiManager: WikiManager? = null

    var context = WikiApplication.applicationContext()

    private val articleImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView = itemView.findViewById<TextView>(R.id.result_title)

    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener {

            // instantiate wikiManager
            wikiManager = (context as WikiApplication).wikiManager

            // determine if the article is already in History before adding it
            if (!wikiManager!!.getIsHistory(currentPage!!.pageid!!)) {
                // add the viewed page to the user's history
                wikiManager?.addHistory(currentPage!!)
            }

            // open page in browser
            openUrlInBrowser(currentPage!!.fullurl, itemView.context, currentPage)
        }
    }

    fun updateWithPage(page: WikiPage) {
        currentPage = page

        titleTextView.text = page.title

        // download image and update articleImageView correctly
        if (page.thumbnail != null) Picasso.get().load(page.thumbnail!!.source).into(articleImageView)
    }

}