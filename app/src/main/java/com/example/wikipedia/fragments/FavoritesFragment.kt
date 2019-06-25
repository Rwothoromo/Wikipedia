package com.example.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wikipedia.R
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.adapters.ArticleListItemRecyclerAdapter
import com.example.wikipedia.managers.WikiManager


/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesFragment : Fragment() {

    var favoritesRecycler: RecyclerView? = null

    // get articles from the WikiManager
    private var wikiManager: WikiManager? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // instantiate wikiManager
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        favoritesRecycler = view.findViewById<RecyclerView>(R.id.favorites_article_recycler)

        favoritesRecycler!!.layoutManager = LinearLayoutManager(context)
        favoritesRecycler!!.adapter = ArticleListItemRecyclerAdapter()

        return view
    }


}
