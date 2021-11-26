package com.crictone.teamexpert;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class NavigationItemsActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_items);

        webView = findViewById(R.id.webview_for_navigations_item);

        Intent getPrivacy = getIntent();
        String pri =  getPrivacy.getStringExtra("privacy_policy");

        Intent getTerms = getIntent();
        String terms =  getTerms.getStringExtra("terms");

        Intent getTips = getIntent();
        String tips =  getTips.getStringExtra("read_me");


        if(pri != null) {
            webView.loadDataWithBaseURL((String) null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Roboto-Regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>" + pri + "</body></html>", "text/html", "UTF-8", (String) null);
        }else if(terms != null){
            webView.loadDataWithBaseURL((String) null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Roboto-Regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>" + terms + "</body></html>", "text/html", "UTF-8", (String) null);
        }else if(tips != null){
            webView.loadDataWithBaseURL((String) null, "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/Roboto-Regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>" + tips + "</body></html>", "text/html", "UTF-8", (String) null);
        }

    }
}