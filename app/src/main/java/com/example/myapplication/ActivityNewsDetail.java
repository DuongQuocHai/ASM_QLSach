package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class ActivityNewsDetail extends AppCompatActivity {
WebView webView;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        imageButton = findViewById(R.id.btn_backnewsdetail);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView = findViewById(R.id.webviewTinTuc);

        Intent intent = getIntent();
        String  link =intent.getStringExtra("link");
        Toast.makeText(this, link, Toast.LENGTH_SHORT).show();

        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

    }
}
