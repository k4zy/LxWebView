package com.kazy.lx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

public class LxWebView extends WebView {

    private WebViewStateCallback stateCallback;

    private List<LoadingInterceptor> loadingInterceptors = new ArrayList<>();

    public LxWebView(Context context) {
        super(context);
        setupWebview();
    }

    public LxWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupWebview(attrs);
    }

    public LxWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupWebview(attrs);
    }

    public void setStateCallback(WebViewStateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebview(AttributeSet attrs) {
        TypedArray args = getContext().obtainStyledAttributes(attrs, R.styleable.Lx);
        boolean domStorageEnabled = args.getBoolean(R.styleable.Lx_dom_storage_enabled, false);
        boolean jsEnabled = args.getBoolean(R.styleable.Lx_java_script_enabled, false);
        boolean databaseEnabled = args.getBoolean(R.styleable.Lx_database_enabled, false);
        boolean appCacheEnabled = args.getBoolean(R.styleable.Lx_app_cache_enabled, false);
        boolean saveFormEnabled = args.getBoolean(R.styleable.Lx_save_form_data, true);
        boolean needInitialFocus = args.getBoolean(R.styleable.Lx_need_initial_focus, false);
        boolean builtInZoomControls = args.getBoolean(R.styleable.Lx_built_in_zoom_controls, false);
        boolean displayZoomControls = args.getBoolean(R.styleable.Lx_display_zoom_controls, false);
        boolean supportZoom = args.getBoolean(R.styleable.Lx_display_zoom_controls, true);
        boolean loadWithOverviewMode = args
                .getBoolean(R.styleable.Lx_load_with_overview_mode, false);
        boolean useWideViewPort = args.getBoolean(R.styleable.Lx_use_wide_view_port, true);
        int cacheMode = args.getInt(R.styleable.Lx_cache_mode, WebSettings.LOAD_DEFAULT);
        WebSettings setting = getSettings();
        setting.setJavaScriptEnabled(jsEnabled);
        setting.setDomStorageEnabled(domStorageEnabled);
        setting.setDatabaseEnabled(databaseEnabled);
        setting.setAppCacheEnabled(appCacheEnabled);
        setting.setSaveFormData(saveFormEnabled);
        setting.setCacheMode(cacheMode);
        setting.setNeedInitialFocus(needInitialFocus);
        setting.setBuiltInZoomControls(builtInZoomControls);
        setting.setDisplayZoomControls(displayZoomControls);
        setting.setSupportZoom(supportZoom);
        setting.setLoadWithOverviewMode(loadWithOverviewMode);
        setting.setUseWideViewPort(useWideViewPort);
        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        setWebViewClient(new WebServiceViewClient());
        args.recycle();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebview() {
        WebSettings setting = getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(false);
        setting.setAppCacheEnabled(false);
        setting.setSaveFormData(false);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setNeedInitialFocus(false);
        setting.setBuiltInZoomControls(false);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);
        setting.setLoadWithOverviewMode(false);
        setting.setUseWideViewPort(true);
        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        setWebViewClient(new WebServiceViewClient());
    }

    public void addLoadingInterceptor(LoadingInterceptor loadingInterceptor) {
        this.loadingInterceptors.add(loadingInterceptor);
    }

    private class WebServiceViewClient extends WebViewClient {

        private WebViewState state = WebViewState.STOP;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (state == WebViewState.STOP) {
                state = WebViewState.START;
                if (stateCallback != null) {
                    stateCallback.onStartLoading();
                }
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            state = WebViewState.ERROR;
            if (stateCallback != null) {
                stateCallback.onError();
            }
        }

        @Override
        public void onPageFinished(WebView view, String loadedUrl) {
            super.onPageFinished(view, loadedUrl);
            if (state == WebViewState.START) {
                if (stateCallback != null) {
                    stateCallback.onFinishLoaded();
                }
            }
            state = WebViewState.STOP;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String loadingUrl) {
            if (loadingUrl == null || loadingInterceptors == null) {
                return false;
            }
            return intercepted(Uri.parse(loadingUrl));
        }

    }

    @Override
    public void loadUrl(String url) {
        if (intercepted(Uri.parse(url))) {
            return;
        }
        super.loadUrl(url);
    }

    private boolean intercepted(Uri uri) {
        for (LoadingInterceptor hooker : loadingInterceptors) {
            if (hooker.validate(uri)) {
                hooker.exec(uri);
                return true;
            }
        }
        return false;
    }
}
