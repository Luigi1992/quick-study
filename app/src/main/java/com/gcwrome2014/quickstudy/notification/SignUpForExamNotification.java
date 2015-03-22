package com.gcwrome2014.quickstudy.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.gcwrome2014.quickstudy.ExamsActivity;
import com.gcwrome2014.quickstudy.R;

import java.util.Calendar;

/**
 * Created by alicja on 10/03/15.
 */
public class SignUpForExamNotification extends Activity {


    private ScheduleClient scheduleClient;
    int mNotificationId = 001;

    public SignUpForExamNotification() {
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
    }

    public void createNotification(String examName, String date) {

        String notificationTitle = "Sign up for " + examName;
        String notificationText = "The exam will be on " + date + ", don't forget to sign up!";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_my_calendar)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationText);

        // Clicking notification should just open application
        Intent resultIntent = new Intent(this, ExamsActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        mNotificationId++;

    }
//mNotificationId
//    public void sendNotification(){
//
//        int day = 10;
//        int month = 03;
//        int year = 2015;
//        int hour = 04;
//        int minute = 20;
//
//        // Create a new calendar set to the date chosen
//        // we set the time to midnight (i.e. the first minute of that day)
//        Calendar c = Calendar.getInstance();
//        c.set(year, month, day);
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
//        scheduleClient.setAlarmForNotification(c, new);
//        // Notify the user what they just did
//        Toast.makeText(this, "Notification set", Toast.LENGTH_SHORT).show();
//    }
}
