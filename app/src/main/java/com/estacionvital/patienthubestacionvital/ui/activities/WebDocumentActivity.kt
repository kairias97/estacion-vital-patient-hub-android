package com.estacionvital.patienthubestacionvital.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.model.EVUserSession

class WebDocumentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_document)
        val webview: WebView = findViewById(R.id.web_document)
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true

        val headers = HashMap<String, String>()
        headers.put("Authorization", "Token token=${EVUserSession.instance.authToken}")
        webview.loadUrl("https://dev.estacionvital.com/api/user/document/138",headers)
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=https://dev.estacionvital.com/api/user/document/138")
    }
}
