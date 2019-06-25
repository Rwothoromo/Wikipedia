package com.example.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.wikipedia.R
import com.example.wikipedia.holders.ListItemHolder
import com.example.wikipedia.models.WikiPage

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListItemHolder>() {

    val currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val page = currentResults[position]
        holder.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        // create view holder here
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }
}