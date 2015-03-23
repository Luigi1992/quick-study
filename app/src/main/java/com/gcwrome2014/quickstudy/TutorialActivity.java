package com.gcwrome2014.quickstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gcwrome2014.quickstudy.custom.CustomPagerAdapter;


public class TutorialActivity extends Activity {

    private ViewPager viewPager;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFirstExecution();
        setContentView(com.gcwrome2014.quickstudy.R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(com.gcwrome2014.quickstudy.R.id.viewPager);
        startButton = (Button) findViewById(com.gcwrome2014.quickstudy.R.id.start_button);
        PagerAdapter adapter = new CustomPagerAdapter(TutorialActivity.this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                if (position==3)
                    startButton.setVisibility(View.VISIBLE);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
