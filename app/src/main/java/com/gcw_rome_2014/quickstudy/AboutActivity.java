package com.gcw_rome_2014.quickstudy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by alicja on 22/03/15.
 */
public class AboutActivity extends ActionBarActivity {
    private TextView aboutTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutTextView = (TextView) findViewById(R.id.about_quick_study);
        String about = getResources().getString(R.string.about_quick_study);
        aboutTextView.setText(about);

    }

}
