package com.example.tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Bootreceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {

        if(intent.action == "android.intent.action.BOOT_COMPLETED"){

            val sharedPrefs = context!!.getSharedPreferences("Alarms",Context.MODE_PRIVATE)

            val interval = sharedPrefs.getInt("time",0)

            val intent = Intent(context, Notificationreceiver::class.java)
            val alarmManager: AlarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingintent = PendingIntent.getBroadcast(context, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval.toLong(),pendingintent)

            Toast.makeText(context,"$interval",Toast.LENGTH_LONG).show()


        }

        else{

            Log.d("Error","Action is empty")

            }





        }

    }

