package com.example.nowyprojekt;

import android.app.NotificationChannel;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.app.NotificationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


public static class MainActivity extends AppCompatActivity {
private static final String CHANNEL_ID = "Noti";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChanel();

        Button btnBigText = findViewById(R.id.btnBigText);
        Button btnBigPicture = findViewById(R.id.btnBigPicture);
        Button btnInbox = findViewById(R.id.btnInbox);

        btnBigText.setOnClickListener(v -> showBigTextNotification(1));
        btnBigPicture.setOnClickListener(v -> showBigPictureNotification());
        btnInbox.setOnClickListener(v -> showInboxNotification());
    }
    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            CharSequence name = "Notification Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void showBigTextNotification(int NOTIFICATION_ID) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("BigText Notification")
                .setContentText("Jest to BigText Notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Powiadomienie z długim tekstem"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void showBigPictureNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("BigPicture Notification")
                .setContentText("Jest to Bigpicture Notification")
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID + 1,builder.build());
    }

    private void showInboxNotification() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .addLine("Line 1: Powiadomienie 1")
                .addLine("Line 2: Powiadomienie 2")
                .addLine("Line 3: Powiadomienie 3")
                .setBigContentTitle("InboxStyle Notification")
                .setSummaryText("+3 more messages");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("InboxStyle Notification")
                .setContentText("Jest to Inbox Notification")
                .setStyle(inboxStyle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID + 2, builder.build());
    }
}
