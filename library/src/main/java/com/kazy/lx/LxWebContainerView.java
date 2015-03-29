package com.kazy.lx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class LxWebContainerView extends RelativeLayout {

    private LxWebView lxWebView;

    private ProgressBar progressBar;

    private ViewGroup errorView;

    private Button reloadButton;

    public LxWebContainerView(Context context) {
        super(context);
        initialize();
    }

    public LxWebContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public LxWebContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        bindViews();
        bindWebViewState();
        TypedArray args = getContext().obtainStyledAttributes(attrs, R.styleable.Lx);
        lxWebView.setupWebSettings(args);
        args.recycle();
    }

    private void initialize() {
        bindViews();
        bindWebViewState();
    }

    private void bindWebViewState() {
        lxWebView.addOnWebViewStateListener(new WebViewStateListener() {
            @Override
            public void onStartLoading(String url, Bitmap favicon) {
                progressBar.clearAnimation();
                progressBar.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
            }

            @Override
            public void onError(int errorCode, String description, String failingUrl) {
                progressBar.setVisibility(View.GONE);
                lxWebView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinishLoaded(String loadedUrl) {
                Animation animation = new AlphaAnimation(1f, 0f);
                animation.setDuration(1000);
                progressBar.startAnimation(animation);
                progressBar.setVisibility(View.GONE);
                lxWebView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (lxWebView.getVisibility() != View.VISIBLE && progress > 80) {
                    lxWebView.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(progress);
            }
        });
    }

    private void bindViews() {
        View.inflate(getContext(), R.layout.view_lx_web_container, this);
        lxWebView = (LxWebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorView = (ViewGroup) findViewById(R.id.error_view);
        reloadButton = (Button) findViewById(R.id.reload_button);
        reloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lxWebView != null) {
                    lxWebView.reload();
                }
            }
        });
    }

    public void addOnWebViewStateListener(WebViewStateListener webViewStateListener) {
        lxWebView.addOnWebViewStateListener(webViewStateListener);
    }

    public void addLoadingInterceptor(LoadingInterceptor loadingInterceptor) {
        lxWebView.addLoadingInterceptor(loadingInterceptor);
    }

    public void loadUrl(String url) {
        lxWebView.loadUrl(url);
    }

    public boolean canGoBack() {
        return lxWebView.canGoBack();
    }

    public void goBack() {
        lxWebView.goBack();
    }

    public String getTitle() {
        return lxWebView.getTitle();
    }

    public String getUrl() {
        return lxWebView.getUrl();
    }

    public String getUserAgentString() {
        return lxWebView.getSettings().getUserAgentString();
    }

    public void setUserAgentString(String ua) {
        lxWebView.getSettings().setUserAgentString(ua);

    }

    public android.webkit.WebSettings getSettings() {
        return lxWebView.getSettings();
    }
}
