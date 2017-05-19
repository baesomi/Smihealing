package com.swdm.mp.smile;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HowtoFragment extends Fragment{

    private AnimationDrawable mframeAnimation;

    public HowtoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.howto_fragment, container, false);
        startAnimation(view);

        return view;
    }
    public void startAnimation(View v) {
        ImageView img = (ImageView) v.findViewById(R.id.howto_last_imageview);
        BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(R.drawable.smihealing_0);
        BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(R.drawable.smihealing_1);
        BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(R.drawable.smihealing_2);
        BitmapDrawable frame4 = (BitmapDrawable) getResources().getDrawable(R.drawable.smihealing);

        // Get the background, which has been compiled to an Animation Drawable object.
        int reasonableDuration = 600;
        mframeAnimation = new AnimationDrawable();
        mframeAnimation.setOneShot(false); // loop continuously
        mframeAnimation.addFrame(frame1, reasonableDuration);
        mframeAnimation.addFrame(frame2, reasonableDuration);
        mframeAnimation.addFrame(frame3, reasonableDuration);
        mframeAnimation.addFrame(frame4, reasonableDuration+300);
        img.setBackgroundDrawable(mframeAnimation);
        mframeAnimation.setVisible(true, true);
        mframeAnimation.start();
    }

    public void stopAnimation(View v) {
        mframeAnimation.stop();
        mframeAnimation.setVisible(false, false);
    }
}
