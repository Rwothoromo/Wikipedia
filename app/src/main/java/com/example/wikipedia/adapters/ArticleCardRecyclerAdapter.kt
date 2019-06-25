package com.example.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wikipedia.R
import com.example.wikipedia.holders.CardHolder
import com.example.wikipedia.models.WikiPage

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder>() {

    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val page = currentResults[position]
        holder.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        // create view holder here
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }
}