package com.kazy.lx.sample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.kazy.lx.LoadingInterceptor;

public class UnsupportedProtocolInterceptor implements LoadingInterceptor {

    private Activity activity;

    public UnsupportedProtocolInterceptor(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean validate(Uri uri) {
        return uri.getScheme() != null && !uri.getScheme().equals("http") && !uri.getScheme().equals("https");
    }

    @Override
    public void exec(Uri uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
