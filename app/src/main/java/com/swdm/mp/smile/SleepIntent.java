package com.swdm.mp.smile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by ChaeRin on 2017-01-31.
 * the Sleep intent in Therapy fragment
 * it shows the method that can go sleep easily & video
 */
public class SleepIntent extends Activity {
    private Button btnVideo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_intent);

        WebView browser = (WebView) findViewById(R.id.sleep_webview);
        browser.getSettings().setJavaScriptEnabled(true);

        browser.addJavascriptInterface(new JavaScriptInterface(this), "Android");

        // if the html file is in the app's memory space use:
        browser.loadUrl("file:///android_asset/sleep_introduce.html");

        btnVideo = (Button) findViewById(R.id.btn_sleep_video);
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=YRPh_GaiL8s"));
                startActivity(intent);
            }
        });
    }
    public void onClick(View view){
        finish();
    }
}
