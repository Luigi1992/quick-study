package com.gcw_rome_2014.quickstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gcw_rome_2014.quickstudy.mail.GMailSender;
import com.gcwrome2014.quickstudy.R;


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

        buttonSendFeedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (fieldsFilled()) {
                    String feedType = spinnerFeedType.getSelectedItem().toString();
                    String user = editTextName.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String message = editTextDetails.getText().toString();
                    String response = "";
                    if (checkBoxResponse.isChecked())
                        response = "The user has requested an email response";

                    sendFeedback("QuickStudy User Feedback: "+feedType,
                            "User: "+user+"\n" + "Email: "+email+"\n"+ message+"\n"+response);
                    finish();
                    showToast("Thanks for your help!");
                }
                else
                    showToast("All fields are required");
            }
        });



    }

    public void sendFeedback(final String subject, final String body) {
        final GMailSender sender = new GMailSender();
        new AsyncTask<Void, Void, Void>() {
            @Override public Void doInBackground(Void... arg) {
                try {
                    sender.sendMail(subject,
                            body,
                            "gcw.quickstudy@gmail.com",
                            "gcw.quickstudy@gmail.com");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            return null;}
        }.execute();
    }

    public boolean fieldsFilled() {
        if (editTextName.getText().toString().isEmpty() ||
                editTextEmail.getText().toString().isEmpty() ||
                editTextDetails.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
