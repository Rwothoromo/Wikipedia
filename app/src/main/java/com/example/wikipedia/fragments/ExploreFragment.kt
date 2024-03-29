package com.example.wikipedia.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wikipedia.R
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.activities.SearchActivity
import com.example.wikipedia.adapters.ArticleCardRecyclerAdapter
import com.example.wikipedia.managers.WikiManager


/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

    private var searchCardView: CardView? = null
    private var exploreRecycler: RecyclerView? = null
    private var refresher: SwipeRefreshLayout? = null

    // get articles from the WikiManager
    private var wikiManager: WikiManager? = null

    private var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()

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
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) // span 2
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        getRandomArticles()

        return view
    }

    private fun getRandomArticles() {
        refresher?.isRefreshing = true

        try {
            wikiManager?.getRandom(15) { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)

                // when we update the dataset on the adapter, we need to call it on the ui thread
                // let the view know there is new data using notifyDataSetChanged()
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
            }
        } catch (exception: Exception) {
            // alert user
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(exception.message).setTitle("Surprise! Something's wrong!")
            val dialog = builder.create()
            dialog.show()
        }
    }
}
