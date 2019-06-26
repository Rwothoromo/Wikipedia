package com.example.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.wikipedia.R
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.managers.WikiManager
import com.example.wikipedia.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    // get articles from the WikiManager
    private var wikiManager: WikiManager? = null

    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        // instantiate wikiManager
        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // get the intended page from the extras
        val wikiPageJson = intent.getStringExtra("page")

        // deserialize the string above into a proper WikiPage model
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        // the title of course
        supportActionBar?.title = currentPage?.title

        // handler to ensure we properly load the url rather than calling over to the webview
        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = true
        }
        article_detail_webview.loadUrl(currentPage!!.fullurl)

        // Add the viewed page to the user's history
        wikiManager?.addHistory(currentPage!!)
    }

    /**
     * Tell this activity to use the menu created for it!
     * @param[menu] the Menu to be used
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {
            android.R.id.home -> finish()
            R.id.action_favorite ->
                try {
                    // determine if the article is already a favorite or not
                    if (wikiManager!!.getIsFavorite(currentPage!!.pageid!!)) {
                        wikiManager!!.removeFavorite(currentPage!!.pageid!!)
                        toast("Article removed from Favorites!")
                    } else {
                        wikiManager!!.addFavorite(currentPage!!)
                        toast("Article added to Favorites!")
                    }
                } catch (exception: Exception) {
                    toast("Unable to Favorite this Article!")
                }
        }
        return true
    }
}