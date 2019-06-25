package com.example.wikipedia.holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wikipedia.R
import com.example.wikipedia.activities.ArticleDetailActivity
import com.example.wikipedia.models.WikiPage
import com.google.gson.Gson
import com.squareup.picasso.Picasso


class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView = itemView.findViewById<ImageView>(R.id.article_image)
    private val titleTextView = itemView.findViewById<TextView>(R.id.article_text)

    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener { view: View? ->
            val detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            val pageJson = Gson().toJson(currentPage)

            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity((detailPageIntent))
        }
    }

    fun updateWithPage(page: WikiPage) {
        currentPage = page

        titleTextView.text = page.title

        // download image and update articleImageView correctly
        if (page.thumbnail != null) Picasso.get().load(page.thumbnail!!.source).into(articleImageView)
    }
}