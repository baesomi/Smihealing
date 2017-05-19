package com.swdm.mp.smile;

/**
 * Created by ChaeRin on 2017-01-31.
 */

import android.graphics.drawable.Drawable;
import android.widget.Switch;

public class ListViewItem {
    private Drawable iconDrawable;
    private String titleStr;
//    private Switch mSwitch;
    private boolean mSwitch;

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setSwitch(boolean s) {
        mSwitch = s;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public boolean getSwitch() {
        return this.mSwitch;
    }
}