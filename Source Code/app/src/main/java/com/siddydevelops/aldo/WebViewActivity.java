package com.siddydevelops.aldo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.ramotion.circlemenu.CircleMenuView;

import ir.alirezabdn.wp7progress.WP10ProgressBar;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String URL;
    Intent intent;

    WP10ProgressBar progressBar;
    CircleMenuView circleMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.translucent));

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#FFFFFF");
        window.setStatusBarColor(colorCodeDark);

        Toast.makeText(this, "Hold Up!", Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBarWebView);
        progressBar.showProgressBar();

        circleMenuView = findViewById(R.id.circular_menu_webView);

        GlobalMenu myMenu;
        myMenu = new GlobalMenu(WebViewActivity.this);
        myMenu.menuClickListners(circleMenuView);

        webView = findViewById(R.id.webView);
        intent = getIntent();

        URL = intent.getStringExtra("Link");

        Log.i("Linl-->",URL);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(URL);

        webView.getSettings().setBuiltInZoomControls(true);
        //webView.getSettings().setUseWideViewPort(true);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.hideProgressBar();
            }
        }, 4000);

    }
}