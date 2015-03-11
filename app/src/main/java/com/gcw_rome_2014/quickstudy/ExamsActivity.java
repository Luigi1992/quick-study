package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.gcw_rome_2014.quickstudy.ExamAdapter.OnItemClickListener;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidInstanceManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.QuickStudy;

/**
 * Created by Luigi on 18/01/2015.
 */
public class ExamsActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private ExamAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton fab;

    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        mRecyclerView = (RecyclerView) findViewById(R.id.exams_recycler_view);

        fab = (ImageButton) findViewById(R.id.fab);
        final Context context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddNewExamActivity.class));
            }
        });

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.menu);

        //Set default values for settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        QuickStudy quickStudy = QuickStudy.getInstance();
        quickStudy.init(getApplicationContext(), getContentResolver());

        // specify an adapter (see also next example)
        mAdapter = new ExamAdapter(quickStudy.getArrayOfExams());

        //Sort exams by date
        mAdapter.sort();


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(context, ViewExamActivity.class);
                Exam exam = mAdapter.getExams()[position];
                i.putExtra("exam", exam);
                startActivity(i);
            }
        });

        AndroidInstanceManager instanceManager = new AndroidInstanceManager(getContentResolver());
        instanceManager.queryInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                //Open calendarProvider calendar with an intent.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("content://com.android.calendar/time"));
                startActivity(intent);
                break;
            case R.id.action_feedback:
                startActivity(new Intent(this, FeedActivity.class));
                break;
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
                break;
        }
        return true;
    }

}
