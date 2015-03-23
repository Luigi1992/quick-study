package com.gcwrome2014.quickstudy;

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

import com.gcwrome2014.quickstudy.model.Exam;
import com.gcwrome2014.quickstudy.model.QuickStudy;

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

        this.exam = (Exam) getIntent().getSerializableExtra("exam");

        name_details = (TextView) findViewById(R.id.details_name);
        date_details = (TextView) findViewById(R.id.details_date);
        difficulty_details = (TextView) findViewById(R.id.details_difficulty);
        registered_details = (TextView) findViewById(R.id.details_registered);

        name_details.setText(exam.getName());
        date_details.setText(new SimpleDateFormat("EEE, d MMMM yyyy, HH:mm", Locale.ITALIAN).format(exam.getDate().getTime()));
        difficulty_details.setText(exam.getDifficulty().getName());
        if (exam.isRegistered())
            registered_details.setText("You've already signed up for this exam");
        else
            registered_details.setText("You haven't signed up for this exam yet");

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
                i.putExtra("exam", this.exam);
                startActivity(i);
                finish();
                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete exam")
                        .setMessage("Are you sure you want to delete this exam?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {

                                    public void run() {
                                        Boolean done = QuickStudy.getInstance().deleteExam(ViewExamActivity.this.exam);
                                        if(done) {
                                            startActivity(new Intent(getApplicationContext(), ExamsActivity.class));
                                        }
                                    }

                                }).start();
                                finish();
                                showToastMessage("The exam has been deleted");
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

            if (exam.isOld())
                showDialog(shareIntent);

            else {
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I have an exam in " + name_details.getText() + " on the "
                        + new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(exam.getDate().getTime())
                        + ", wish me good luck!");
                startActivity(Intent.createChooser(shareIntent, "Share exam with..."));
            }

        }
    }

    public void showDialog(final Intent shareIntent) {

        final Dialog d = new Dialog(ViewExamActivity.this);
        d.setTitle("What score did you get?");
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
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I passed the exam in " + name_details.getText() + " of the "
                        + new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).format(exam.getDate().getTime())
                        + " with a score of " + score + "!");
                startActivity(Intent.createChooser(shareIntent, "Share exam with..."));
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
