package com.example.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.wikipedia.R
import com.example.wikipedia.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : AppCompatActivity() {

    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // get the intended page from the extras
        val wikiPageJson = intent.getStringExtra("page")

        // deserialize the string above into a proper WikiPage model
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        // handler to ensure we properly load the url rather than calling over to the webview
        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = true
        }
        article_detail_webview.loadUrl(currentPage!!.fullurl)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}