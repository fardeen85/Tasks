package com.example.tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.fragment_set_notificationt_ime.*
import kotlinx.android.synthetic.main.fragment_set_notificationt_ime.view.*
import kotlinx.android.synthetic.main.tasklist_custom.*
import java.util.*
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.R.id.toggle
import android.content.ComponentName
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_listfrag.view.*
import kotlinx.android.synthetic.main.layout_taskcomplete.view.*


class set_notificationt_ime : Fragment() {

    val checked = false
    lateinit var calendar : Calendar
    lateinit var calenderminutes : Calendar
    lateinit var calendar5hour: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_set_notificationt_ime, container, false)

        val sharedPrefs = activity!!.getSharedPreferences("checkingswitch", MODE_PRIVATE)
        val switch_check = sharedPrefs.getBoolean("checked", true)
        if(switch_check == true){

            v.notify_switch.isChecked = true
        }

        else{

            v.notify_switch.isChecked = false
        }

        val time = v.timespinner.selectedItem



            val mainActivity = MainActivity()

        v.notify_switch.setOnCheckedChangeListener(object  : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {



                val timenotify = v.timespinner.selectedItem

                val  IntervalTimeforbroadcast =

                    if(timenotify == "30 min"){ 30*60000 }
                    else if(timenotify == "1 hour"){ 60*60000}
                    else if(timenotify == "2 hour"){ 120*60000 }
                    else if(timenotify == "5 hour "){ 250*60000 }
                    else{ 0 }


                val time = timespinner.selectedItem.toString()
                val  IntervalTime =

                    if(time == "30 min"){ 30*60000 }
                    else if(time == "1 hour"){ 60*60000}
                    else if(time == "2 hour"){ 120*60000 }
                    else if(time == "5 hour "){ 250*60000 }
                    else{ 0 }




                if (isChecked == true){


                    val editor = activity!!.getSharedPreferences("checkingswitch", MODE_PRIVATE).edit()
                    editor.putBoolean("checked", true)
                    editor.commit()

                    //for resseting alarm

                    val editor1 = activity!!.getSharedPreferences("Alarms", MODE_PRIVATE).edit()
                    editor1.putInt("time",IntervalTimeforbroadcast)
                    editor1.commit()
                    Notification(IntervalTime)

                    val receiver = ComponentName(requireContext(), Bootreceiver::class.java)

                    requireContext()!!.packageManager.setComponentEnabledSetting(
                        receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )

                   val cancelnotificationreceiver = ComponentName(requireContext(),Notificationreceiver::class.java)
                    requireContext().packageManager.setComponentEnabledSetting(

                        cancelnotificationreceiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )




                }

                if(isChecked == false){

                    //cancel notification
                    cancelNotification()

                    val editor = activity!!.getSharedPreferences("checkingswitch", MODE_PRIVATE).edit()
                    editor.putBoolean("checked", false)
                    editor.apply()
                    editor.commit()
                    cancelNotification()

                    //update value of time in shaaredpreference
                    val editor1 = activity!!.getSharedPreferences("Alarms", MODE_PRIVATE).edit()
                    editor1.putInt("time",0)
                    editor1.commit()

                    //disable Bootcomplete receiver
                    val receiver = ComponentName(requireContext(), Bootreceiver::class.java)

                    requireContext()!!.packageManager.setComponentEnabledSetting(
                        receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )

                    //disable alarmanager pending intent for boot receiver
                    val intent = Intent(context, Notificationreceiver::class.java)
                    val alarmManager: AlarmManager = requireContext()!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val pendingintentbootreceiver = PendingIntent.getBroadcast(context, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    alarmManager.cancel(pendingintentbootreceiver)

                  /* val cancelnotificationreceiver = ComponentName(requireContext(),Notificationreceiver::class.java)
                    requireContext().packageManager.setComponentEnabledSetting(

                        cancelnotificationreceiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )*/
                }
            }


        })





        return  v

    }



    fun cancelNotification() {

        val intent = Intent(context, Notificationreceiver::class.java)
        val pendingintent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager: AlarmManager = activity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingintent)

    }

    fun Notification(interval : Int) {

        val intent = Intent(context, Notificationreceiver::class.java)
        val alarmManager: AlarmManager = activity!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingintent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),interval.toLong(),pendingintent)

    }






}
