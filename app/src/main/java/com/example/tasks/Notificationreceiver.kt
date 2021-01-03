package com.example.tasks

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notificationreceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {

        if (intent != null){

            val intent = Intent(context,MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val startappIntent = PendingIntent.getActivity(context,1001,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            val mBuilder = NotificationCompat.Builder(context,MainActivity().CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_check_circle_black_icon)
                .setContentTitle("Come and check your tasklist")
                .setContentIntent(startappIntent)
                .setAutoCancel(true)
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(1001, mBuilder.build())


        }
    }


}