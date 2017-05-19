package com.swdm.mp.smile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by ChaeRin on 2017-01-31.
 * the class is for html file.
 */
public class JavaScriptInterface extends Activity{
    Context mContext;

    /**
     * Instantiate the interface and set the context
     */
    JavaScriptInterface(Context c) {
        mContext = c;
    }
    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showSite(String site) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
        startActivity(intent);
    }
    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toastMsg) {
        Toast.makeText(mContext, toastMsg, Toast.LENGTH_SHORT).show();
    }
}

