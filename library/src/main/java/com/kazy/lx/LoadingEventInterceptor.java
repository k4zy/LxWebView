package com.kazy.lx;

import android.net.Uri;

public interface LoadingEventInterceptor {
    public boolean validate(Uri uri);
    public void exec(Uri uri);
}
