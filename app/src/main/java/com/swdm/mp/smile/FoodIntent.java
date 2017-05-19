package com.swdm.mp.smile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by ChaeRin on 2017-01-31.
 * the Food intent in Therapy fragment.
 * it shows some foods that are good for depression patients.
 */
public class FoodIntent extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_intent);

        WebView browser = (WebView) findViewById(R.id.food_webview);
        browser.getSettings().setJavaScriptEnabled(true);

        // if the html file is in the app's memory space use:
        browser.loadUrl("file:///android_asset/food_introduce.html");
    }
    public void onClick(View view){
        finish();
    }
}
