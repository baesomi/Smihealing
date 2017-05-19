package com.swdm.mp.smile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by ChaeRin on 2017-01-31.
 * the Activity intent in Therapy fragment
 * It shows that users' exercise type
 */
public class ActivityIntent extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        WebView browser = (WebView) findViewById(R.id.activity_webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        // if the html file is in the app's memory space use:
        browser.loadUrl("file:///android_asset/activity_introduce.html");

    }
    public void onClick(View view){
        finish();
    }
}
