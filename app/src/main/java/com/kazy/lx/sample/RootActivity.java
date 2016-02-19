package com.kazy.lx.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kazy.lx.LxWebContainerView;
import com.kazy.lx.WebViewStateListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RootActivity extends AppCompatActivity {

    @InjectView(R.id.webview_view)
    LxWebContainerView webContainerView;
    @InjectView(R.id.toolBar)
    Toolbar toolbar;
    @InjectView(R.id.title)
    TextView titleTextView;
    @InjectView(R.id.actionButton)
    ImageButton actionButton;
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
                actionButton.setImageResource(R.drawable.ic_close);
            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onFinishLoaded(String loadedUrl) {
                titleTextView.setText(webContainerView.getTitle());
                actionButton.setImageResource(R.drawable.ic_refresh);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {

            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webContainerView.isLoading()) {
                    webContainerView.stopLoading();
                    actionButton.setImageResource(R.drawable.ic_refresh);
                } else {
                    webContainerView.relaod();
                }
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
