package com.example.wikipedia.shared

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.example.wikipedia.R


fun openUrlInBrowser(url: String?, context: Context) {

    // what is needed to launch the url
    val uri = Uri.parse(url)

    // create an intent builder
    val intentBuilder = CustomTabsIntent.Builder()

    // Begin customizing
    // set toolbar colors
    intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
    intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))

    // set start and exit animations
    intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
    intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)

    // build custom tabs intent
    val customTabsIntent = intentBuilder.build()

    // launch the url in a browser (chrome)
    customTabsIntent.launchUrl(context, uri)
}