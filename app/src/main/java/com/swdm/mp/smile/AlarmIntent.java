package com.swdm.mp.smile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by ChaeRin on 2017-02-03.
 * the Alarm intent in Setting fragment.
 * It can set whenever alarm rings.
 */
public class AlarmIntent extends Activity {

    protected static Switch switchAlarm;
    protected static Switch mSwitch00, mSwitch01, mSwitch02, mSwitch08, mSwitch09, mSwitch10;
    protected static Switch mSwitch11, mSwitch12, mSwitch14, mSwitch16, mSwitch18, mSwitch20;
    protected static Switch mSwitch21, mSwitch22, mSwitch23;
    protected static Switch mSwitch03, mSwitch04, mSwitch05, mSwitch06, mSwitch07;
    protected static Switch mSwitch13, mSwitch15, mSwitch17, mSwitch19;

    private Boolean alarmChoice;
    int initNum = -1;

    private static final String PREF_NAME = "My_prefs";

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    int notificationID = 1;
    int passHour = 99, passMin = 99;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_intent);

        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        switchAlarm = (Switch) findViewById(R.id.switch_alarm);
        //0,1,2,8,9,10,11,12,14,16,18,20,21,22,23
        mSwitch00 = (Switch) findViewById(R.id.switch_0000);
        mSwitch01 = (Switch) findViewById(R.id.switch_0100);
        mSwitch02 = (Switch) findViewById(R.id.switch_0200);
        mSwitch03 = (Switch) findViewById(R.id.switch_0300);
        mSwitch04 = (Switch) findViewById(R.id.switch_0400);
        mSwitch05 = (Switch) findViewById(R.id.switch_0500);
        mSwitch06 = (Switch) findViewById(R.id.switch_0600);
        mSwitch07 = (Switch) findViewById(R.id.switch_0700);
        mSwitch08 = (Switch) findViewById(R.id.switch_0800);
        mSwitch09 = (Switch) findViewById(R.id.switch_0900);
        mSwitch10 = (Switch) findViewById(R.id.switch_1000);
        mSwitch11 = (Switch) findViewById(R.id.switch_1100);
        mSwitch12 = (Switch) findViewById(R.id.switch_1200);
        mSwitch13 = (Switch) findViewById(R.id.switch_1300);
        mSwitch14 = (Switch) findViewById(R.id.switch_1400);
        mSwitch15 = (Switch) findViewById(R.id.switch_1500);
        mSwitch16 = (Switch) findViewById(R.id.switch_1600);
        mSwitch17 = (Switch) findViewById(R.id.switch_1700);
        mSwitch18 = (Switch) findViewById(R.id.switch_1800);
        mSwitch19 = (Switch) findViewById(R.id.switch_1900);
        mSwitch20 = (Switch) findViewById(R.id.switch_2000);
        mSwitch21 = (Switch) findViewById(R.id.switch_2100);
        mSwitch22 = (Switch) findViewById(R.id.switch_2200);
        mSwitch23 = (Switch) findViewById(R.id.switch_2300);

        Log.d("initNum", initNum + "");
        initNum = sharedPref.getInt("initNum", 0);
        Log.d("initNum_after", initNum + "");
        //alarm on-off
        loadPrefs(AlarmIntent.switchAlarm, "24");
        loads();
        editor.putInt("initNum_after2", -1);
        editor.commit();

        //service -- maybe,,,,, 삭제해도될듯 ㅠ젭알
//        Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(AlarmIntent.this,alarmService.class);
//        startService(intent);

        //시간 check~!
//        Date date = new Date(System.currentTimeMillis());
//        Log.d("time111",date+"");
//        Log.d("time111222", date.getTime() + "");
//        Calendar calendar = Calendar.getInstance();
//        DatePicker datePicker = new DatePicker(getApplicationContext());
//        TimePicker timePicker =new TimePicker(getApplicationContext());
//        timePicker.setHour(22);
//        timePicker.setMinute(25);
//        calendar.set(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth(),
//                timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
//        Log.d("time111calendar", datePicker.getMonth()+1+"월"
//                + datePicker.getDayOfMonth()+"일?"
//                +timePicker.getCurrentHour() + "시"
//                +timePicker.getCurrentMinute() + "분");
//        long startTime = calendar.getTimeInMillis();
//        Log.d("time111LONG", startTime + "");

        if (AlarmIntent.switchAlarm.isChecked()) {
            AlarmIntent.switchAlarm.setChecked(true);
            //alarm on-off
            loadPrefs(AlarmIntent.switchAlarm, "24");
            editor.putInt("SwitchAlarm24", 1);
            editor.commit();
            settingAlarm(true);
        } else {
            AlarmIntent.switchAlarm.setChecked(false);
            //alarm on-off
            loadPrefs(AlarmIntent.switchAlarm, "24");
            editor.putInt("SwitchAlarm24", 0);
            editor.commit();
            settingAlarm(false);

        }
        AlarmIntent.switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (AlarmIntent.switchAlarm.isChecked()) {
                    AlarmIntent.switchAlarm.setChecked(true);
                    //alarm on-off
                    loadPrefs(AlarmIntent.switchAlarm, "24");
                    editor.putInt("SwitchAlarm24", 1);
                    editor.commit();
                    settingAlarm(true); //the alarm is enable
                } else {

                    AlarmIntent.switchAlarm.setChecked(false);
                    //alarm on-off
                    loadPrefs(AlarmIntent.switchAlarm, "24");
                    editor.putInt("SwitchAlarm24", 0);
                    editor.commit();
                    settingAlarm(false); //the alarm is not enable
                }
            }
        });
    }

    // alarm enrollment
    private void setAlarm(Context context, int passH, int passM){
        Log.d("settingSwitchAlarm",passH+":"+passM);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = new DatePicker(getApplicationContext());
        TimePicker timePicker =new TimePicker(getApplicationContext());
        timePicker.setHour(passH);
        timePicker.setMinute(passM);
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                passH, passM, 0);
        long startTime = calendar.getTimeInMillis();
        Log.d("settingSwitch", datePicker.getMonth()+1+"월"
                + datePicker.getDayOfMonth()+"일"
                +passH + "시"
                +passM + "분");
        Log.d("settingSwitch", new Date(startTime) + "startTime");
        Intent i = new Intent(AlarmIntent.this,alarmService.class);
        i.putExtra("notificationID", notificationID);
        PendingIntent pIntent = PendingIntent.getService(context, 0, i, 0);

        alarmManager.setRepeating(AlarmManager.RTC, startTime, 86400000, pIntent);
    }

    // alarm release
    private void releaseAlarm(Context context, int passH, int passM){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = new DatePicker(getApplicationContext());
        TimePicker timePicker =new TimePicker(getApplicationContext());
        timePicker.setHour(passH);
        timePicker.setMinute(passM);
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                passH, passM, 0);
        long startTime = calendar.getTimeInMillis();

        Intent i = new Intent(AlarmIntent.this,alarmService.class);

        i.putExtra("notificationID", notificationID);
        PendingIntent pIntent = PendingIntent.getService(context, 0, i, 0);
        alarmManager.cancel(pIntent);

        // 주석을 풀면 먼저 실행되는 알람이 있을 경우, 제거하고
        // 새로 알람을 실행하게 된다. 상황에 따라 유용하게 사용 할 수 있다.
//        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 86400000 ,pIntent);
    }

    //whether the alarm is enable or not
    public void settingAlarm(boolean b) {
        AlarmIntent.mSwitch00.setEnabled(b);
        AlarmIntent.mSwitch01.setEnabled(b);
        AlarmIntent.mSwitch02.setEnabled(b);
        AlarmIntent.mSwitch03.setEnabled(b);
        AlarmIntent.mSwitch04.setEnabled(b);
        AlarmIntent.mSwitch05.setEnabled(b);
        AlarmIntent.mSwitch06.setEnabled(b);
        AlarmIntent.mSwitch07.setEnabled(b);
        AlarmIntent.mSwitch08.setEnabled(b);
        AlarmIntent.mSwitch09.setEnabled(b);
        AlarmIntent.mSwitch10.setEnabled(b);
        AlarmIntent.mSwitch11.setEnabled(b);
        AlarmIntent.mSwitch12.setEnabled(b);
        AlarmIntent.mSwitch13.setEnabled(b);
        AlarmIntent.mSwitch14.setEnabled(b);
        AlarmIntent.mSwitch15.setEnabled(b);
        AlarmIntent.mSwitch16.setEnabled(b);
        AlarmIntent.mSwitch17.setEnabled(b);
        AlarmIntent.mSwitch18.setEnabled(b);
        AlarmIntent.mSwitch19.setEnabled(b);
        AlarmIntent.mSwitch20.setEnabled(b);
        AlarmIntent.mSwitch21.setEnabled(b);
        AlarmIntent.mSwitch22.setEnabled(b);
        AlarmIntent.mSwitch23.setEnabled(b);
        editor.commit();
    }

    // check the alarm's sharedpreferece
    public void loads() {
        //basic alarm
        loadPrefs(AlarmIntent.mSwitch00, "0");
        Log.d("" + sharedPref.getInt("SwitchAlarm0", 0), "AlarmIntent+onResume+SwitchAlarm");
        loadPrefs(AlarmIntent.mSwitch01, "1");
        loadPrefs(AlarmIntent.mSwitch02, "2");
        loadPrefs(AlarmIntent.mSwitch03, "3");
        loadPrefs(AlarmIntent.mSwitch04, "4");
        loadPrefs(AlarmIntent.mSwitch05, "5");
        loadPrefs(AlarmIntent.mSwitch06, "6");
        loadPrefs(AlarmIntent.mSwitch07, "7");
        loadPrefs(AlarmIntent.mSwitch08, "8");
        loadPrefs(AlarmIntent.mSwitch09, "9");
        loadPrefs(AlarmIntent.mSwitch10, "10");
        loadPrefs(AlarmIntent.mSwitch11, "11");
        loadPrefs(AlarmIntent.mSwitch12, "12");
        loadPrefs(AlarmIntent.mSwitch13, "13");
        loadPrefs(AlarmIntent.mSwitch14, "14");
        loadPrefs(AlarmIntent.mSwitch15, "15");
        loadPrefs(AlarmIntent.mSwitch16, "16");
        loadPrefs(AlarmIntent.mSwitch17, "17");
        loadPrefs(AlarmIntent.mSwitch18, "18");
        loadPrefs(AlarmIntent.mSwitch19, "19");
        loadPrefs(AlarmIntent.mSwitch20, "20");
        loadPrefs(AlarmIntent.mSwitch21, "21");
        loadPrefs(AlarmIntent.mSwitch22, "22");
        loadPrefs(AlarmIntent.mSwitch23, "23");
        Log.d("setting", alarmChoice + "");
        editor.commit();
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        //alarm on-off
        loadPrefs(AlarmIntent.switchAlarm, "24");
        Log.d("" + sharedPref.getInt("SwitchAlarm24", 0), "AlarmIntent+onResume+SwitchAlarm");
        loads();

        AlarmIntent.switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (AlarmIntent.switchAlarm.isChecked()) {
                    editor.putInt("SwitchAlarm24", 1);
                    editor.commit();
                    settingAlarm(true);
                } else {
                    editor.putInt("SwitchAlarm24", 0);
                    editor.commit();
                    settingAlarm(false);
                }
            }
        });
    }

    // set the alarm value
    private void loadPrefs(final Switch s, final String num) {
        //ALARM switch ID: 2131492989
        //12:00AM: 2131492990 1:00AM: 1, ... ,
        //10:00AM: 2131493000 11:00AM: 1, ...,
        //08:00PM: 2131493010
        //11:00PM: 2131493013
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                Log.d("" + sharedPref.getInt("SwitchAlarm" + num, 0), "AlarmIntent+loadPref+SwitchAlarm" + num);
                if (initNum == -1) {
                    //0,1,234567,8,9,10,11,12,131415,1617,18,1920,21,22,23
                    if (num.equals("8") || num.equals("9") || num.equals("11") || num.equals("12")
                            || num.equals("14") || num.equals("15") || num.equals("16")) {
                        s.setChecked(true);
                        s.getId();
                        Log.d("getId_switch", s.getId() + "");
                        if (num.equals("8")) {
                            setAlarm(getApplicationContext(),8,0);
                        } else if(num.equals("9")) {
                            setAlarm(getApplicationContext(),9,0);
                        } else if(num.equals("11")) {
                            setAlarm(getApplicationContext(),11,0);
                        } else if(num.equals("12")) {
                            setAlarm(getApplicationContext(),12,0);
                        } else if(num.equals("14")) {
                            setAlarm(getApplicationContext(),14,0);
                        } else if(num.equals("15")) {
                            setAlarm(getApplicationContext(),15,0);
                        } else if(num.equals("16")) {
                            setAlarm(getApplicationContext(),16,0);
                        }
                        editor.putInt("SwitchAlarm" + num, 1);
                        editor.commit();
                    } else {
                        s.setChecked(false);
                        Log.d("getId_switch2", s.getId() + "");
                        if (num.equals("0")) {
                            releaseAlarm(getApplicationContext(), 0, 0);
                        } else if (num.equals("1")) {
                            releaseAlarm(getApplicationContext(), 1, 0);
                        } else if (num.equals("2")) {
                            releaseAlarm(getApplicationContext(), 2, 0);
                        } else if (num.equals("3")) {
                            releaseAlarm(getApplicationContext(), 3, 0);
                        } else if (num.equals("4")) {
                            releaseAlarm(getApplicationContext(), 4, 0);
                        } else if (num.equals("5")) {
                            releaseAlarm(getApplicationContext(), 5, 0);
                        } else if (num.equals("6")) {
                            releaseAlarm(getApplicationContext(), 6, 0);
                        } else if (num.equals("7")) {
                            releaseAlarm(getApplicationContext(), 7, 0);
                        } else if (num.equals("8")) {
                            releaseAlarm(getApplicationContext(), 8, 0);
                        } else if (num.equals("9")) {
                            releaseAlarm(getApplicationContext(), 9, 0);
                        } else if (num.equals("10")) {
                            releaseAlarm(getApplicationContext(), 10, 0);
                        } else if (num.equals("11")) {
                            releaseAlarm(getApplicationContext(), 11, 0);
                        } else if (num.equals("12")) {
                            releaseAlarm(getApplicationContext(), 12, 0);
                        } else if (num.equals("13")) {
                            releaseAlarm(getApplicationContext(), 13, 0);
                        } else if (num.equals("14")) {
                            releaseAlarm(getApplicationContext(), 14, 0);
                        } else if (num.equals("15")) {
                            releaseAlarm(getApplicationContext(), 15, 0);
                        } else if (num.equals("16")) {
                            releaseAlarm(getApplicationContext(), 16, 0);
                        } else if (num.equals("17")) {
                            releaseAlarm(getApplicationContext(), 17, 0);
                        } else if (num.equals("18")) {
                            releaseAlarm(getApplicationContext(), 18, 0);
                        } else if (num.equals("19")) {
                            releaseAlarm(getApplicationContext(), 19, 0);
                        } else if (num.equals("20")) {
                            releaseAlarm(getApplicationContext(), 20, 0);
                        } else if (num.equals("21")) {
                            releaseAlarm(getApplicationContext(), 21, 0);
                        } else if (num.equals("22")) {
                            releaseAlarm(getApplicationContext(), 22, 0);
                        } else if (num.equals("23")) {
                            releaseAlarm(getApplicationContext(), 23, 0);
                        }
                        editor.putInt("SwitchAlarm" + num, 0);
                        editor.commit();
                    }
                } else {
                    if (s.isChecked()) {
                        s.setChecked(true);
                        Log.d("getId_switch3", s.getId() + "");
                        if (num.equals("0")) {
                            setAlarm(getApplicationContext(), 0, 0);
                        } else if (num.equals("1")) {
                            setAlarm(getApplicationContext(), 1, 0);
                        } else if (num.equals("2")) {
                            setAlarm(getApplicationContext(), 2, 0);
                        } else if (num.equals("3")) {
                            setAlarm(getApplicationContext(), 3, 0);
                        } else if (num.equals("4")) {
                            setAlarm(getApplicationContext(), 4, 0);
                        } else if (num.equals("5")) {
                            setAlarm(getApplicationContext(), 5, 0);
                        } else if (num.equals("6")) {
                            setAlarm(getApplicationContext(), 6, 0);
                        } else if (num.equals("7")) {
                            setAlarm(getApplicationContext(), 7, 0);
                        } else if (num.equals("8")) {
                            setAlarm(getApplicationContext(), 8, 0);
                        } else if (num.equals("9")) {
                            setAlarm(getApplicationContext(), 9, 0);
                        } else if (num.equals("10")) {
                            setAlarm(getApplicationContext(), 10, 0);
                        } else if (num.equals("11")) {
                            setAlarm(getApplicationContext(), 11, 0);
                        } else if (num.equals("12")) {
                            setAlarm(getApplicationContext(), 12, 0);
                        } else if (num.equals("13")) {
                            setAlarm(getApplicationContext(), 13, 0);
                        } else if (num.equals("14")) {
                            setAlarm(getApplicationContext(), 14, 0);
                        } else if (num.equals("15")) {
                            setAlarm(getApplicationContext(), 15, 0);
                        } else if (num.equals("16")) {
                            setAlarm(getApplicationContext(), 16, 0);
                        } else if (num.equals("17")) {
                            setAlarm(getApplicationContext(), 17, 0);
                        } else if (num.equals("18")) {
                            setAlarm(getApplicationContext(), 18, 0);
                        } else if (num.equals("19")) {
                            setAlarm(getApplicationContext(), 19, 0);
                        } else if (num.equals("20")) {
                            setAlarm(getApplicationContext(), 20, 0);
                        } else if (num.equals("21")) {
                            setAlarm(getApplicationContext(), 21, 0);
                        } else if (num.equals("22")) {
                            setAlarm(getApplicationContext(), 22, 0);
                        } else if (num.equals("23")) {
                            setAlarm(getApplicationContext(), 10, 04);
                            Log.d("settingSwitchAlarm",""+"setAlarm");
                        }
                        editor.putInt("SwitchAlarm" + num, 1);
                        editor.commit();
                    } else {
                        s.setChecked(false);
                        Log.d("getId_switch4", s.getId() + "");
                        if (num.equals("0")) {
                            releaseAlarm(getApplicationContext(), 0, 0);
                        } else if (num.equals("1")) {
                            releaseAlarm(getApplicationContext(), 1, 0);
                        } else if (num.equals("2")) {
                            releaseAlarm(getApplicationContext(), 2, 0);
                        } else if (num.equals("3")) {
                            releaseAlarm(getApplicationContext(), 3, 0);
                        } else if (num.equals("4")) {
                            releaseAlarm(getApplicationContext(), 4, 0);
                        } else if (num.equals("5")) {
                            releaseAlarm(getApplicationContext(), 5, 0);
                        } else if (num.equals("6")) {
                            releaseAlarm(getApplicationContext(), 6, 0);
                        } else if (num.equals("7")) {
                            releaseAlarm(getApplicationContext(), 7, 0);
                        } else if (num.equals("8")) {
                            releaseAlarm(getApplicationContext(), 8, 0);
                        } else if (num.equals("9")) {
                            releaseAlarm(getApplicationContext(), 9, 0);
                        } else if (num.equals("10")) {
                            releaseAlarm(getApplicationContext(), 10, 0);
                        } else if (num.equals("11")) {
                            releaseAlarm(getApplicationContext(), 11, 0);
                        } else if (num.equals("12")) {
                            releaseAlarm(getApplicationContext(), 12, 0);
                        } else if (num.equals("13")) {
                            releaseAlarm(getApplicationContext(), 13, 0);
                        } else if (num.equals("14")) {
                            releaseAlarm(getApplicationContext(), 14, 0);
                        } else if (num.equals("15")) {
                            releaseAlarm(getApplicationContext(), 15, 0);
                        } else if (num.equals("16")) {
                            releaseAlarm(getApplicationContext(), 16, 0);
                        } else if (num.equals("17")) {
                            releaseAlarm(getApplicationContext(), 17, 0);
                        } else if (num.equals("18")) {
                            releaseAlarm(getApplicationContext(), 18, 0);
                        } else if (num.equals("19")) {
                            releaseAlarm(getApplicationContext(), 19, 0);
                        } else if (num.equals("20")) {
                            releaseAlarm(getApplicationContext(), 20, 0);
                        } else if (num.equals("21")) {
                            releaseAlarm(getApplicationContext(), 21, 0);
                        } else if (num.equals("22")) {
                            releaseAlarm(getApplicationContext(), 22, 0);
                        } else if (num.equals("23")) {
                            releaseAlarm(getApplicationContext(), 23, 0);
                        }
                        editor.putInt("SwitchAlarm" + num, 0);
                        editor.commit();
                    }
                }
            }
        });
        if (sharedPref.getInt("SwitchAlarm" + num, 0) == 0) {
            alarmChoice = false;
        } else {
            alarmChoice = true;
        }
        s.setChecked(alarmChoice); //알람 고정

        editor.commit();
    }


}

