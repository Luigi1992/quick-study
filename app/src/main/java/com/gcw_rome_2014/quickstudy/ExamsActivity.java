package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidInstanceManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.difficulties.Easy;
import com.gcw_rome_2014.quickstudy.model.difficulties.Hard;
import com.gcw_rome_2014.quickstudy.model.difficulties.Medium;
import com.gcw_rome_2014.quickstudy.ExamAdapter.OnItemClickListener;

import java.util.Calendar;

/**
 * Created by Luigi on 18/01/2015.
 */
public class ExamsActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private ExamAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton fab;
    private Exam[] exams;

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
                Intent i = new Intent(context, AddNewExamActivity.class);
                startActivity(i);
            }
        });

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        //Set default values for settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        exams = new Exam[] {new Exam("Ricerca Operativa", new Easy(), Calendar.getInstance()),
                            new Exam("Analisi Matematica", new Hard(), Calendar.getInstance()),
                            new Exam("Fondamenti Automatica", new Medium(), Calendar.getInstance())};

        // specify an adapter (see also next example)
        mAdapter = new ExamAdapter(exams);
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
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
        }
        return true;
    }

}
