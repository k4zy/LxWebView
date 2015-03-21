package com.kazy.lx.sample;

import com.kazy.lx.LxWebView;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RootActivity extends ActionBarActivity {

    @InjectView(R.id.webview_view)
    LxWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);
        webView.addUriFooker(new UnsupportedProtcolInterceptor(this));
        webView.loadUrl("http://google.com");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
