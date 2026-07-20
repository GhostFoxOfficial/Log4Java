package net.kdt.pojavlaunch;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import net.kdt.pojavlaunch.utils.Tools;
import net.kdt.pojavlaunch.R;

public class KivyChatActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kivy_chat);

        mWebView = findViewById(R.id.kivy_webview);

        
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        
        mWebView.setWebViewClient(new WebViewClient());

        
        mWebView.loadUrl(Tools.URL_HOME);
    }

    
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

