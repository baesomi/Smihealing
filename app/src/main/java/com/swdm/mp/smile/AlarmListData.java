package com.swdm.mp.smile;

import android.widget.Switch;
import android.widget.TextView;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by ChaeRin on 2017-02-08.
 */
public class AlarmListData {
    //    public TextView mTime;
    public String mTime;
    public boolean isAlarm = true;
    public Switch isSwitch;
    //    private Switch mSwitch;
    public AlarmListData(){
    }
    public AlarmListData(String _time, boolean _alarm){
        this.mTime = _time;
        this.isAlarm = _alarm;
//        this.isAlarm = true;
        this.isSwitch.setChecked(isAlarm);
    }

    public void setTitle(String title) { mTime = title; }

    public void setAlarm(boolean s) {isAlarm = s;}

    public void setSwitch(Switch s) {
        isSwitch = s; //isSwitch.setChecked(this.isAlarm);
    }

    public String getTime() {
        return this.mTime;
    }

    public boolean getAlarm() {
        return this.isAlarm;
    }

    public Switch getSwitch() {return this.isSwitch;}

    /**
     * ì•ŒíŒŒë²³ ì´ë¦„ìœ¼ë¡œ ì •ë ¬
     */
    public static final Comparator<AlarmListData> ALPHA_COMPARATOR = new Comparator<AlarmListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AlarmListData mListDate_1, AlarmListData mListDate_2) {
            return sCollator.compare(mListDate_1.mTime, mListDate_2.mTime);
        }
    };
}