package com.gcw_rome_2014.quickstudy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.gcw_rome_2014.quickstudy.model.Exam;
import com.gcw_rome_2014.quickstudy.model.QuickStudy;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Alessio on 16/02/2015.
 */
public class ViewExamActivity extends ActionBarActivity implements NumberPicker.OnValueChangeListener {
    private TextView name_details;
    private TextView date_details;
    private TextView difficulty_details;
    private TextView registered_details;
    private Exam exam;
    ShareActionProvider mShareActionProvider;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam);

        long examID = (long) getIntent().getSerializableExtra("examID");
        QuickStudy quickStudy = QuickStudy.getInstance();
        this.exam = quickStudy.getExam(examID);

        name_details = (TextView) findViewById(R.id.details_name);
        date_details = (TextView) findViewById(R.id.details_date);
        difficulty_details = (TextView) findViewById(R.id.details_difficulty);
        registered_details = (TextView) findViewById(R.id.details_registered);

        name_details.setText(exam.getName());
        date_details.setText(new SimpleDateFormat("EEE, d MMMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getDate().getTime()));

        switch (exam.getDifficulty().getName()) {
            case "Easy":
                difficulty_details.setText(getResources().getString(R.string.easy));
                break;
            case "Medium":
                difficulty_details.setText(getResources().getString(R.string.medium));
                break;
            case "Hard":
                difficulty_details.setText(getResources().getString(R.string.hard));
                break;
        }


        if (exam.isRegistered())
            registered_details.setText(getResources().getString(R.string.signed));
        else
            registered_details.setText(getResources().getString(R.string.not_signed));

        //Show icon in the Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_exam, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent i = new Intent(this, EditExamActivity.class);
                i.putExtra("examID", this.exam.getId());
                startActivity(i);
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.delete_exam))
                        .setMessage(getResources().getString(R.string.confirm_delete))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean done = QuickStudy.getInstance().deleteExam(ViewExamActivity.this.exam);
                                if(done) {
                                    showToastMessage(getResources().getString(R.string.success_delete));
                                    finish();
                                } else
                                    showToastMessage(getResources().getString(R.string.failed_delete));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                return true;
            case R.id.action_reschedule:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.reschedule_sessions))
                        .setMessage(getResources().getString(R.string.confirm_reschedule))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        QuickStudy.getInstance().updateExam(ViewExamActivity.this.exam);
                                    }
                                }).start();
                                showToastMessage(getResources().getString(R.string.success_reschedule));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                return true;
            case R.id.action_delete_sessions:
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.delete_sessions))
                        .setMessage(getResources().getString(R.string.confirm_del_sessions))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        QuickStudy.getInstance().deleteSessions(ViewExamActivity.this.exam);
                                    }
                                }).start();
                                showToastMessage(getResources().getString(R.string.success_del_sessions));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                return true;
            case R.id.action_share:
                mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
                if (mShareActionProvider != null)
                    setShareIntent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToastMessage(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void setShareIntent() {

        if (mShareActionProvider != null) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QuickStudy");

            if (exam.isOld()) {
                showDialog(shareIntent);
            }

            else {
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_1) + " " + name_details.getText() + " " + getResources().getString(R.string.share_2)
                        + " " + new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(exam.getDate().getTime())
                        + ", " + getResources().getString(R.string.share_3));
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_with)));
            }

        }
    }

    public void showDialog(final Intent shareIntent) {

        final Dialog d = new Dialog(ViewExamActivity.this);
        d.setTitle(getResources().getString(R.string.what_score));
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(31);
        np.setMinValue(18);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                score = np.getValue();
                d.dismiss();
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_a) + " " + name_details.getText() + " " + getResources().getString(R.string.share_b)
                        + " " + new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(exam.getDate().getTime())
                        + " " + getResources().getString(R.string.share_c) + " " + score + "!");
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_with)));
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }

}
