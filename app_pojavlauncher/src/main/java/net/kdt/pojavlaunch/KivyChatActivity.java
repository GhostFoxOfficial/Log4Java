package net.kdt.pojavlaunch;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;


import t.artdeell.mojo.Tools;

public class KivyChatActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        String packageName = getPackageName();
        int layoutId = getResources().getIdentifier("activity_kivy_chat", "layout", packageName);
        setContentView(layoutId);

        
        int webViewId = getResources().getIdentifier("kivy_webview", "id", packageName);
        mWebView = findViewById(webViewId);

        
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        
        mWebView.loadUrl(Tools.URL_HOME);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
