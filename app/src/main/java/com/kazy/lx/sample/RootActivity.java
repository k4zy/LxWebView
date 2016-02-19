package com.kazy.lx.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.kazy.lx.LxWebContainerView;
import com.kazy.lx.WebViewStateListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RootActivity extends ActionBarActivity {

    @InjectView(R.id.webview_view)
    LxWebContainerView webContainerView;
    @InjectView(R.id.toolBar)
    Toolbar toolbar;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        url = "http://yahoo.co.jp";
        toolbar.setTitle(url);
        webContainerView.addLoadingInterceptor(new UnsupportedProtocolInterceptor(this));
        webContainerView.loadUrl(url);
        webContainerView.addOnWebViewStateListener(new WebViewStateListener() {
            @Override
            public void onStartLoading(String url, Bitmap favicon) {

            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onFinishLoaded(String loadedUrl) {
                toolbar.setTitle(webContainerView.getTitle());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_close:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
