package com.example.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wikipedia.R
import com.example.wikipedia.holders.CardHolder

class ArticleCardRecyclerAdapter : RecyclerView.Adapter<CardHolder>() {

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        // update view from here
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        // create view holder here
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)
        return CardHolder(cardItem)
    }
}