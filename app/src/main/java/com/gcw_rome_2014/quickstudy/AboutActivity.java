package com.gcw_rome_2014.quickstudy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.gcwrome2014.quickstudy.R;

/**
 * Created by alicja on 22/03/15.
 */
public class AboutActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = (TextView) findViewById(R.id.about_quick_study);
        String about = "QuickStudy:\n" +
                "\n" +
                "- Organises for you the study plan\n" +
                "- Shows when you should study\n" +
                "- Keeps an eye on you if you signed up your exams in time\n" +
                "- Uses Google Calendar to show suggested planning sessions and exams";
        aboutTextView.setText(about);

    }

}
