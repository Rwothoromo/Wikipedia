package com.example.wikipedia.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.wikipedia.R

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView = itemView.findViewById<ImageView>(R.id.result_icon)
    private val titleTextView = itemView.findViewById<TextView>(R.id.result_title)
}