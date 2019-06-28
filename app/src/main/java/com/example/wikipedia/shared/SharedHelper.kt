package com.example.wikipedia.shared

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.example.wikipedia.R
import com.example.wikipedia.models.WikiPage
import com.example.wikipedia.receviers.CopyLinkBroadcastReceiver
import com.example.wikipedia.receviers.FavoriteBroadcastReceiver
import com.google.gson.Gson


fun openUrlInBrowser(url: String?, context: Context, page: WikiPage?) {

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

    // add copy  option in the menu
    val copyIntent = Intent(context, CopyLinkBroadcastReceiver::class.java)
    val copyLabel = "Copy Link"
    val copyPendingIntent = PendingIntent.getBroadcast(context, 0, copyIntent, 0)
    intentBuilder.addMenuItem(copyLabel, copyPendingIntent)

    // add favorite option in the menu
    val favoriteIntent = Intent(context, FavoriteBroadcastReceiver::class.java)
    val pageJson = Gson().toJson(page)
    favoriteIntent.putExtra("page", pageJson)
    val favoriteLabel = "Favorite"
    val favoritePendingIntent =
        PendingIntent.getBroadcast(context, 0, favoriteIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    intentBuilder.addMenuItem(favoriteLabel, favoritePendingIntent)

    // build custom tabs intent
    val customTabsIntent = intentBuilder.build()

    // launch the url in a browser (chrome)
    customTabsIntent.launchUrl(context, uri)
}