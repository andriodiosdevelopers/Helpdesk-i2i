package helpdesk.i2i.com.ifazidesk.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.io.InputStream;
import java.io.OutputStream;
import helpdesk.i2i.com.ifazidesk.R;
public class Utils {
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void notification(Context context, String title, String messageBody, int notificationId, PendingIntent pendingIntent){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)

                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_white);
            notificationBuilder.setColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
        }
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);


//        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);
        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, notificationBuilder.build());
//        startForeground(notificationId, notificationBuilder.build());
    }
}