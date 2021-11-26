package com.crictone.teamexpert;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        webView = findViewById(R.id.webView_news);

        webView.getSettings().setJavaScriptEnabled(true);



        final Intent intent = getIntent();
        String ss =  intent.getStringExtra("content");

        webView.loadDataWithBaseURL((String) null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Roboto-Regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>" + ss + "</body></html>", "text/html", "UTF-8", (String) null);

    }
}