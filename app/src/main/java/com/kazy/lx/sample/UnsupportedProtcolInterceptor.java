package com.kazy.lx.sample;

import com.kazy.lx.LoadingInterceptor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class UnsupportedProtcolInterceptor implements LoadingInterceptor {

    private Activity activity;

    public UnsupportedProtcolInterceptor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean validate(Uri uri) {
        return !uri.getScheme().equals("http") && !uri.getScheme().equals("https");
    }

    @Override
    public void exec(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }
}
