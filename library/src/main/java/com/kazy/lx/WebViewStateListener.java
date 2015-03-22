package com.kazy.lx;

import android.graphics.Bitmap;

public interface WebViewStateListener {

    public void onStartLoading(String url, Bitmap favicon);

    public void onError(int errorCode, String description, String failingUrl);

    public void onFinishLoaded(String loadedUrl);

}
