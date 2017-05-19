package com.swdm.mp.smile;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class TherapyFragment extends Fragment {
    private ImageButton btnActivity;
    private ImageButton btnMessage;
    private ImageButton btnFood;
    private ImageButton btnSleep;


    public TherapyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.therapy_fragment, container, false);

        btnActivity = (ImageButton) view.findViewById(R.id.activityButton);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.ACTIVITYINTENT"));
            }
        });
        btnMessage = (ImageButton) view.findViewById(R.id.messageButton);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.MESSAGEINTENT"));
            }
        });
        btnSleep = (ImageButton) view.findViewById(R.id.sleepButton);
        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.SLEEPINTENT"));
            }
        });
        btnFood = (ImageButton) view.findViewById(R.id.foodButton);
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.swdm.mp.smile.FOODINTENT"));
            }
        });
        // 처음으로 0번째 Fragment를 보여줍니다. pager.setCurrentItem(0);

        // Inflate the layout for this fragment
        return view;
    }

}
