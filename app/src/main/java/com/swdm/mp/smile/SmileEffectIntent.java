package com.swdm.mp.smile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by ChaeRin on 2017-02-07.
 * the smile effect intent in setting fragment
 * it shows some smile effects
 */
public class SmileEffectIntent extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smile_effect_intent);

        WebView browser = (WebView) findViewById(R.id.smile_effect_webview);
        browser.getSettings().setJavaScriptEnabled(true);

        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        // if the html file is in the app's memory space use:
        browser.loadUrl("file:///android_asset/smile_effect_introduce.html");

        browser.setHorizontalScrollBarEnabled(false); //hide the horizontal scroll
        browser.setVerticalScrollBarEnabled(false);
    }

    public void onClick(View view) {
        finish();
    }
}
