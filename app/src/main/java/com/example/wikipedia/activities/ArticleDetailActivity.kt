package com.example.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.wikipedia.R
import com.example.wikipedia.WikiApplication
import com.example.wikipedia.managers.WikiManager
import com.example.wikipedia.models.WikiPage
import com.example.wikipedia.shared.openUrlInBrowser
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    // get articles from the WikiManager
    var wikiManager: WikiManager? = null

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

        // download image and update articleDetailImageView correctly
        val articleDetailImageView = findViewById<ImageView>(R.id.article_detail_imageview)
        if (currentPage?.thumbnail != null) Picasso.get().load(currentPage?.thumbnail!!.source).into(
            articleDetailImageView
        )

        // add the viewed page to the user's history
        wikiManager?.addHistory(currentPage!!)

        // open page in browser
        openUrlInBrowser(currentPage!!.fullurl, this)
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