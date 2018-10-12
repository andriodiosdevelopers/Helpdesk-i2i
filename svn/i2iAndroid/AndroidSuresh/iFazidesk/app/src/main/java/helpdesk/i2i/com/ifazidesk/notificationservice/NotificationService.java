package helpdesk.i2i.com.ifazidesk.notificationservice;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.List;

import helpdesk.i2i.com.ifazidesk.R;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails;
import helpdesk.i2i.com.ifazidesk.activities.TicketDetails_User;
import helpdesk.i2i.com.ifazidesk.preferences.Preferences;

/**
 * Created by SSk_A on 28-03-2017.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Preferences prefs;
    long time = new Date().getTime();
    String tmpStr = String.valueOf(time);
    String last4Str = tmpStr.substring(tmpStr.length() - 5);
    int notificationId = Integer.valueOf(last4Str);

    public NotificationService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional

        try {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            //Calling method to generate notification
            String title = remoteMessage.getNotification().getTitle();
            String CompliantId = "" + remoteMessage.getData().get("CompliantId").toString();
            String Ticket_isClosed = "" + remoteMessage.getData().get("isClose").toString();
            String Ticket_StatusCount = "" + remoteMessage.getData().get("StatusCount").toString();
            String ClickAction = "" + remoteMessage.getNotification().getClickAction();
            Log.d("Notification", "ClickAction: " + ClickAction);
            sendNotification(remoteMessage.getNotification().getBody(), title, CompliantId, Ticket_isClosed , Ticket_StatusCount);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("updatetickets"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //This method is only generating push notification
    //It is same as we did in earlier posts
   private void sendNotification(String messageBody, String title, String complaintID, String Ticket_isClosed, String Ticket_StatusCount) {
       prefs = new Preferences(getApplicationContext());
       Bundle bundle = new Bundle();
       String string_isadmin = prefs.getString("isAdmin_SP");
       String string_isserviceengg = prefs.getString("isServiceEngineer_SP");
       Intent intent;
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
       if(string_isadmin.equals("true")||string_isserviceengg.equals("true"))
       {
           bundle.putString("TicketNo", "" + complaintID);
           bundle.putString("Ticket_isClosed", "" + Ticket_isClosed);
           bundle.putString("Ticket_StatusCount", "" + Ticket_StatusCount);
           intent =  new Intent(getApplicationContext(), TicketDetails.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent.putExtra("TicketNo", complaintID);
           intent.putExtra("Ticket_isClosed", Ticket_isClosed);
           intent.putExtra("Ticket_StatusCount", Ticket_StatusCount);
           prefs.setBoolean("OpenNotification",true);
           prefs.setString("TicketNo", "" + complaintID);
           prefs.setString("Ticket_isClosed", "" + Ticket_isClosed);
           prefs.setString("Ticket_StatusCount", "" + Ticket_StatusCount);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);
           //intent.setAction(Intent.ACTION_MAIN);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           //intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
           stackBuilder.addParentStack(TicketDetails.class);
           //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
           //intent.setAction(Intent.ACTION_MAIN);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);

           //intent.setAction(Intent.ACTION_MAIN);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);
           //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

           //intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent.putExtras(bundle);
           ////stackBuilder.addNextIntent(intent);
       }
       else
       {
           bundle.putString("TicketNo", "" + complaintID);
           bundle.putString("Ticket_isClosed", "" + Ticket_isClosed);
           bundle.putString("Ticket_StatusCount", "" + Ticket_StatusCount);
           intent = new Intent(getApplicationContext(), TicketDetails_User.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           ////intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent.putExtra("TicketNo", complaintID);
           intent.putExtra("Ticket_isClosed", Ticket_isClosed);
           intent.putExtra("Ticket_StatusCount", Ticket_StatusCount);
           prefs.setBoolean("OpenNotification",true);
           prefs.setString("TicketNo", "" + complaintID);
           prefs.setString("Ticket_isClosed", "" + Ticket_isClosed);
           prefs.setString("Ticket_StatusCount", "" + Ticket_StatusCount);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);
           //intent.setAction(Intent.ACTION_MAIN);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           //intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
           stackBuilder.addParentStack(TicketDetails_User.class);
           //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
           //intent.setAction(Intent.ACTION_MAIN);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);

           //intent.setAction(Intent.ACTION_MAIN);
           //intent.addCategory(Intent.CATEGORY_LAUNCHER);
           //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           //intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
           //intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent.putExtras(bundle);
           //stackBuilder.addNextIntent(intent);
       }
       stackBuilder.addNextIntent(intent);
       //stackBuilder.addParentStack(TicketDetailsData.class);
       //stackBuilder.addParentStack(TicketDetails_User.class);

        //*/intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

      /*  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                | PendingIntent.FLAG_ONE_SHOT);*/

       //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
       PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);


       /* PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_ONE_SHOT);*/

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
        startForeground(notificationId, notificationBuilder.build());
    }



    /*private Intent getPreviousIntent() {
        Intent newIntent = null;
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final List<ActivityManager.AppTask> recentTaskInfos = activityManager.getAppTasks();
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.AppTask appTaskTaskInfo: recentTaskInfos) {
                    if (appTaskTaskInfo.getTaskInfo().baseIntent.getComponent().getPackageName().equals("helpdesk.i2i.com.ifazidesk")) {
                        newIntent = appTaskTaskInfo.getTaskInfo().baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        } else {
            final List<ActivityManager.RecentTaskInfo> recentTaskInfos = activityManager.getRecentTasks(1024, 0);
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo: recentTaskInfos) {
                    if (recentTaskInfo.baseIntent.getComponent().getPackageName().equals("helpdesk.i2i.com.ifazidesk")) {
                        newIntent = recentTaskInfo.baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        }
        if (newIntent == null) newIntent = new Intent();
        return newIntent;
    }*/
}
