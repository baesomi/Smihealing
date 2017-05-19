package com.swdm.mp.smile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
/*
* the setting fragment is showed smile effects, can set the alarm time,
 * and send email to developers and give stars on Playstore (soon)*/

public class SettingFragment extends Fragment {
    private Button btnSmileEffect;
    private Button btnStar;
    private Button btnLetter;
    private Button btnAlarm;
    // private Switch switchAlarm;
    Boolean alarmChoice;
    private static final String PREF_NAME = "My_prefs";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPref = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();


    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d("onResume in Progress","progress,,");
    }
    public void loadPrefs(final Switch s, final String num) {
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//            sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//
////        final SharedPreferences.Editor editor = settings.edit();
//            editor = sharedPref.edit();

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Log.d("" + sharedPref.getBoolean("SwitchAlarm" + num, false), "SettingFragment+loadPref+SwitchAlarm" + num);
                if (s.isChecked()) {
                    s.setChecked(true);
                    editor.putBoolean("SwitchAlarm" + num, true);
                    editor.commit();
                } else {
                    s.setChecked(false);
                    editor.putBoolean("SwitchAlarm" + num, false);
                    editor.commit();
                }
            }
        });
        if(sharedPref.getBoolean("SwitchAlarm" + num, false)){
            alarmChoice = false;
        } else {
            alarmChoice = true;
        }
        s.setChecked(alarmChoice); //ì•ŒëžŒ ê³ ì •

        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        btnAlarm = (Button) view.findViewById(R.id.btn_alarm);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.ALARMINTENT"));
            }
        });
        btnSmileEffect = (Button) view.findViewById(R.id.btn_effect);
        btnSmileEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.SMILEEFFECTINTENT"));
            }
        });
        btnStar = (Button) view.findViewById(R.id.btn_star);
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: need to connect Play Store
                //startActivity(new Intent("com.swdm.mp.smile.ACTIVITYINTENT"));
            }
        });
        btnLetter = (Button) view.findViewById(R.id.btn_letter);
        btnLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: need to connect mail app
                Intent it = new Intent(Intent.ACTION_SEND);
                String[] tos = {"chaerin.du.ub@gmail.com"};
                it.putExtra(Intent.EXTRA_EMAIL, tos);
                it.putExtra(Intent.EXTRA_TEXT, "Please tell me your opinion. :)");
                it.putExtra(Intent.EXTRA_SUBJECT, "[SMIHEALING] subject");
                it.setType("text/plain");
                startActivity(Intent.createChooser(it, "Choose Emarkil Client"));
                //startActivity(new Intent("com.swdm.mp.smile.ACTIVITYINTENT"));
            }
        });

        //switchAlarm = (Switch) view.findViewById(R.id.switch_alarm);

        return view;
    }
    /*
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm() {
            AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity().getApplicationContext(), MyReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplication(), 0, intent, 0);
            //PendingIntent sender = PendingIntent.getActivity(getActivity(),0,new Intent(getActivity(),MainActivity.class),0);

//            Calendar calendar = Calendar.getInstance();
//           //ì•ŒëžŒì‹œê°„ calendarì— setí•´ì£¼ê¸°
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 11, 05);
//            Log.d("d","alarm000");
//
//            //ì•ŒëžŒ ì˜ˆì•½
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            long period = 1000 * 5;
            long after = 1000 * 5;
            long t = SystemClock.elapsedRealtime();
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, t + after, period, sender);
        }
    }
*/


}