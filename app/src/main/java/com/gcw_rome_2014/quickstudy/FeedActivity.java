package com.gcw_rome_2014.quickstudy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


public class FeedActivity extends ActionBarActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextDetails;
    private CheckBox checkBoxResponse;
    private Spinner spinnerFeedType;
    private Button buttonSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        editTextName = (EditText) findViewById(R.id.feed_name);
        editTextEmail = (EditText) findViewById(R.id.feed_email);
        editTextDetails = (EditText) findViewById(R.id.feed_details);
        checkBoxResponse = (CheckBox) findViewById(R.id.checkbox_response);
        spinnerFeedType = (Spinner) findViewById(R.id.spinner_feedbacktype);
        buttonSendFeedback = (Button) findViewById(R.id.submit_feedback);

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String details = editTextDetails.getText().toString();
        String type = spinnerFeedType.getSelectedItem().toString();
        boolean response = checkBoxResponse.isChecked();

    }

    public void sendFeedback(View button) {
        // Do click handling here
    }

}
