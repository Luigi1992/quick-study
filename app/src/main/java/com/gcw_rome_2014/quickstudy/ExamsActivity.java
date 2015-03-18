package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gcw_rome_2014.quickstudy.ExamAdapter.OnItemClickListener;
import com.gcw_rome_2014.quickstudy.calendar.provider.AndroidInstanceManager;
import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.QuickStudy;
import com.gcw_rome_2014.quickstudy.settings.SettingsActivity;

/**
 * Created by Luigi on 18/01/2015.
 */
public class ExamsActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private ExamAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton fab;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLinearLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private String[] mMenuTitles;

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

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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

        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        mDrawerList = (ListView) findViewById(R.id.list_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  // host Activity
                mDrawerLayout,         // DrawerLayout object
                R.drawable.ic_menu,  // nav drawer image to replace 'Up' caret
                R.string.drawer_open,  //"open drawer" description for accessibility
                R.string.drawer_close  //"close drawer" description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("Your exams");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("QuickStudy");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

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
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_calendar:
                //Open calendarProvider calendar with an intent.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("content://com.android.calendar/time"));
                startActivity(intent);
                break;
        }
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
        menu.findItem(R.id.action_calendar).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
                break;
            case 4:
                break;
            case 5:
                startActivity(new Intent(this, FeedActivity.class));
                break;
        }

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}