package com.kazy.lx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

public class LxWebView extends WebView {

    private WebViewState state = WebViewState.STOP;

    private List<WebViewStateListener> webViewStateListeners = new ArrayList<>();

    private List<LoadingInterceptor> loadingInterceptors = new ArrayList<>();

    public LxWebView(Context context) {
        super(context);
        initialize();
    }

    public LxWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public LxWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(attrs);
    }

    public void addOnWebViewStateListener(WebViewStateListener webViewStateListener) {
        webViewStateListeners.add(webViewStateListener);
    }

    private void initialize() {
        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        setWebViewClient(new WebServiceViewClient());
        setWebChromeClient(new WebServiceChromeClient());
    }

    private void initialize(AttributeSet attrs) {
        TypedArray args = getContext().obtainStyledAttributes(attrs, R.styleable.Lx);
        setupWebSettings(args);
        args.recycle();
        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        setWebViewClient(new WebServiceViewClient());
        setWebChromeClient(new WebServiceChromeClient());
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void setupWebSettings(TypedArray args) {
        boolean allowContentAccess = args.getBoolean(R.styleable.Lx_allow_content_access, true);
        boolean allowFileAccess = args.getBoolean(R.styleable.Lx_allow_file_access, true);
        boolean allowFileAccessFromFileURLs = args.getBoolean(R.styleable.Lx_allow_file_access_from_file_urls, true);
        boolean allowUniversalAccessFromFileURLs = args.getBoolean(R.styleable.Lx_allow_universal_access_from_file_urls, false);
        boolean appCacheEnabled = args.getBoolean(R.styleable.Lx_app_cache_enabled, false);
        boolean blockNetworkImage = args.getBoolean(R.styleable.Lx_block_network_image, false);
        boolean blockBlockNetworkLoads = args.getBoolean(R.styleable.Lx_block_network_loads, false);
        boolean builtInZoomControls = args.getBoolean(R.styleable.Lx_built_in_zoom_controls, false);
        int cacheMode = args.getInt(R.styleable.Lx_cache_mode, WebSettings.LOAD_DEFAULT);
        boolean databaseEnabled = args.getBoolean(R.styleable.Lx_database_enabled, false);
        boolean displayZoomControls = args.getBoolean(R.styleable.Lx_display_zoom_controls, false);
        boolean domStorageEnabled = args.getBoolean(R.styleable.Lx_dom_storage_enabled, false);
        boolean geolocationEnabled = args.getBoolean(R.styleable.Lx_geolocation_enabled, true);
        boolean javaScriptCanOpenWindowsAutomatically = args.getBoolean(R.styleable.Lx_java_script_can_open_windows_automatically, false);
        boolean jsEnabled = args.getBoolean(R.styleable.Lx_java_script_enabled, false);
        boolean loadWithOverviewMode = args.getBoolean(R.styleable.Lx_load_with_overview_mode, false);
        boolean loadsImagesAutomatically  = args.getBoolean(R.styleable.Lx_loads_images_automatically, true);
        boolean needInitialFocus = args.getBoolean(R.styleable.Lx_need_initial_focus, false);
        boolean saveFormEnabled = args.getBoolean(R.styleable.Lx_save_form_data, true);
        boolean supportMultipleWindows = args.getBoolean(R.styleable.Lx_support_multiple_windows,false);
        boolean supportZoom = args.getBoolean(R.styleable.Lx_support_zoom, true);
        boolean useWideViewPort = args.getBoolean(R.styleable.Lx_use_wide_view_port, true);

        WebSettings setting = getSettings();
        setting.setAllowContentAccess(allowContentAccess);
        setting.setAllowFileAccess(allowFileAccess);
        setting.setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs);
        setting.setAllowUniversalAccessFromFileURLs(allowUniversalAccessFromFileURLs);
        setting.setAppCacheEnabled(appCacheEnabled);
        setting.setBlockNetworkImage(blockNetworkImage);
        setting.setBlockNetworkLoads(blockBlockNetworkLoads);
        setting.setBuiltInZoomControls(builtInZoomControls);
        setting.setCacheMode(cacheMode);
        setting.setDatabaseEnabled(databaseEnabled);
        setting.setDisplayZoomControls(displayZoomControls);
        setting.setDomStorageEnabled(domStorageEnabled);
        setting.setGeolocationEnabled(geolocationEnabled);
        setting.setJavaScriptCanOpenWindowsAutomatically(javaScriptCanOpenWindowsAutomatically);
        setting.setJavaScriptEnabled(jsEnabled);
        setting.setLoadWithOverviewMode(loadWithOverviewMode);
        setting.setLoadsImagesAutomatically(loadsImagesAutomatically);
        setting.setNeedInitialFocus(needInitialFocus);
        setting.setSaveFormData(saveFormEnabled);
        setting.setSupportMultipleWindows(supportMultipleWindows);
        setting.setSupportZoom(supportZoom);
        setting.setUseWideViewPort(useWideViewPort);
    }

    public void addLoadingInterceptor(LoadingInterceptor loadingInterceptor) {
        this.loadingInterceptors.add(loadingInterceptor);
    }

    private class WebServiceChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
            if(state == WebViewState.START){
                for(WebViewStateListener listener : webViewStateListeners){
                    listener.onProgressChanged(view, progress);
                }
            }
        }

    }

    private class WebServiceViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (state == WebViewState.STOP) {
                state = WebViewState.START;
                for(WebViewStateListener listener : webViewStateListeners){
                    listener.onStartLoading(url, favicon);
                }
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            state = WebViewState.ERROR;
            for(WebViewStateListener listener : webViewStateListeners){
                listener.onError(errorCode, description, failingUrl);
            }
        }

        @Override
        public void onPageFinished(WebView view, String loadedUrl) {
            super.onPageFinished(view, loadedUrl);
            if (state == WebViewState.START) {
                for(WebViewStateListener listener : webViewStateListeners){
                    listener.onProgressChanged(view, 100);
                    listener.onFinishLoaded(loadedUrl);
                }
            }
            state = WebViewState.STOP;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String loadingUrl) {
            if (loadingUrl == null || loadingInterceptors == null) {
                return false;
            }
            return intercept(Uri.parse(loadingUrl));
        }

    }

    @Override
    public void loadUrl(String url) {
        if (intercept(Uri.parse(url))) {
            return;
        }
        super.loadUrl(url);
    }

    private boolean intercept(Uri uri) {
        for (LoadingInterceptor loadingInterceptor : loadingInterceptors) {
            if (loadingInterceptor.validate(uri)) {
                loadingInterceptor.exec(uri);
                return true;
            }
        }
        return false;
    }

}
