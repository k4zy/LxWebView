package com.kazy.lx.sample;

import com.kazy.lx.LxWebContainerView;
import com.kazy.lx.WebViewStateListener;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RootActivity extends ActionBarActivity {

    @InjectView(R.id.webview_view)
    LxWebContainerView webContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);
        webContainerView.addLoadingInterceptor(new UnsupportedProtcolInterceptor(this));
        webContainerView.loadUrl("http://yahoo.co.jp");
        webContainerView.addOnWebViewStateListener(new WebViewStateListener() {
            @Override
            public void onStartLoading(String url, Bitmap favicon) {

            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onFinishLoaded(String loadedUrl) {

            }

            @Override
            public void onProgressChanged(WebView view, int progress) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webContainerView.canGoBack()) {
            webContainerView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
