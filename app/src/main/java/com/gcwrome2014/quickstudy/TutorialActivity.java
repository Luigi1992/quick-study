package com.gcwrome2014.quickstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gcwrome2014.quickstudy.custom.CustomPagerAdapter;


public class TutorialActivity extends Activity {

    private ViewPager viewPager;
    private Button startButton;
    private RelativeLayout bgElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFirstExecution();
        setContentView(com.gcwrome2014.quickstudy.R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(com.gcwrome2014.quickstudy.R.id.viewPager);
        startButton = (Button) findViewById(com.gcwrome2014.quickstudy.R.id.start_button);
        bgElement = (RelativeLayout) findViewById(R.id.tutorial_container);
        PagerAdapter adapter = new CustomPagerAdapter(TutorialActivity.this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bgElement.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        bgElement.setBackgroundColor(Color.parseColor("#2EFE9A"));
                        break;
                    case 2:
                        bgElement.setBackgroundColor(Color.parseColor("#FFFF99"));
                        break;
                    case 3:
                        bgElement.setBackgroundColor(Color.parseColor("#F5A9BC"));
                    startButton.setVisibility(View.VISIBLE);
                        break;
                    }
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                bgElement.setBackgroundColor(Color.WHITE);
                start();
            }
        });


    }

    public void checkFirstExecution() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            start();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }

    }

    public void start() {
        Intent intent = new Intent(this, ExamsActivity.class);
        startActivity(intent);
        finish();
    }

}
