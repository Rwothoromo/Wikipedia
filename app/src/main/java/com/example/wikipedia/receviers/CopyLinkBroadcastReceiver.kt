package com.example.wikipedia.receviers

import android.content.*
import android.util.Log
import android.widget.Toast


class CopyLinkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val url = intent.dataString
        Toast.makeText(context, "Copy link pressed. URL = " + url!!, Toast.LENGTH_SHORT).show()

        val uri = intent.data
        if (uri != null) {

            val bundle = intent.extras
            if (bundle != null) {
                for (key in bundle.keySet()) {
                    val value = bundle.get(key)
                    Toast.makeText(context, value!!.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("Dummy", String.format("%s %s (%s)", key, value.toString(), value.javaClass.name))
                }
            }

            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newUri(null, uri.toString(), uri)
            clipboardManager.primaryClip = clipData
        }
    }

}