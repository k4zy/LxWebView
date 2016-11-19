LxWebView
============
[![](https://jitpack.io/v/kazy1991/LxWebView.svg)](https://jitpack.io/#kazy1991/LxWebView)


This library provides you useful interfaces which tells loading-state and intercept url loading.  
You ,not any more, should not create Custom WebView Class.

Feature
--------

* Setting `WebSettings` with xml attributes.
* Provides correct loading-state Callback ( show errorView and loadingView  etc)
* Provides loading url interceptor ( bind action with custom_url_sheme  etc)

How to
--------

##### Step 1. Add the JitPack repository to your build file

```groovy
  repositories {
    maven {
      url "https://jitpack.io"
    }
  }
```

##### Step 2. Add the dependency in the form

```groovy
  dependencies {
    compile 'com.github.kazy1991:LxWebView:0.2.0'
  }
```

##### Step 3. Replace LxWebView with Webview in your layout xml

```xml
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:lx="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    <com.kazy.lx.LxWebView
            android:id="@+id/webview_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            lx:dom_storage_enabled="true"
            lx:app_cache_enabled="true"
            lx:java_script_enabled="true"
            lx:built_in_zoom_controls="true"
            lx:display_zoom_controls="false"
            lx:load_with_overview_mode="true"
            lx:use_wide_view_port="true"/>
</RelativeLayout>
```

License
-------

    Copyright 2015 Kazuki Yoshida

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
