package com.dk.agriculture;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoActivity extends AppCompatActivity {
    private WebView mWebView;
    //https://www.youtube.com/results?search_query=pulses+crop+cultivation+techniques
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();

        String crop = intent.getStringExtra("crop");

        String cropUrl = "https://www.youtube.com/results?search_query=" + crop + "+crop+cultivation+techniques";
        mWebView = findViewById(R.id.videoView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.loadUrl(cropUrl);


    }
}
