package com.example.tasks

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.TimePickerDialog
import android.os.Bundle
import android.renderscript.RenderScript
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_listfrag.*
import kotlinx.android.synthetic.main.fragment_listfrag.view.*
import java.nio.channels.Channel
import java.text.SimpleDateFormat
import java.util.*

public class Add : Fragment() {

    lateinit var mviewmodel : Viewmodel
    lateinit var listname : String
    lateinit var c : Calendar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_add, container, false)

        mviewmodel = ViewModelProvider(this).get(Viewmodel::class.java)

        c = Calendar.getInstance()


        v.save.setOnClickListener {

            if(txtask.text.toString().length == 0){

                Toast.makeText(requireContext(),"Task field is empty",Toast.LENGTH_LONG).show()
            }
            else {

                val dateandtime = CalAdd.text.toString() + "," + txttime.text.toString()
                val taskmodel = Taskmodel(0, txtask.text.toString(), dateandtime, false)
                mviewmodel.Add(taskmodel)
                Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_add2_to_listfrag)
            }

        }


        v.datepicker.setOnClickListener {

            getDate()

        }

        v.timeAdd.setOnClickListener {

            gettime()
        }

        v.backfromadd.setOnClickListener {

            findNavController().navigate(R.id.action_add2_to_listfrag)
        }

        return v
    }


    fun getDate(){



        val date = c.get(Calendar.DAY_OF_MONTH)
        val mmonth = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val dayofweek = c.get(Calendar.DAY_OF_WEEK)


        val dpd = DatePickerDialog(requireContext(),DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            c.set(Calendar.YEAR,year)
            c.set(Calendar.MONTH,month)
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth)




            val months : Int = month+1



            val simpleDateFormat = SimpleDateFormat("EEEE")
            val date = Date(year, month+1, dayOfMonth - 4)
            val dayString = simpleDateFormat.format(date)


            val monthshort = if (months == 1){"Jan"}else if(months ==2){"Feb"}
            else if(months ==3){"Mar"} else if(months ==4){"Apr"}
            else if(months== 5){"May"} else if(months == 6){"Jun"}
            else if(months == 7){"Jul"} else if(months == 8){"Aug"} else if(months==9)
            {"Sep"} else if(months == 10){"Oct"} else if(months==11){"Nov"}
            else if(months == 12){"Dec"} else{"N/A"}


            val day = if (dayString == "Monday"){"Mon"} else if(dayString == "Tuesday"){"Tue"}
            else if(dayString == "Wednesday"){"Wed"} else if(dayString == "Thursday"){"Thu"} else if(dayString =="Friday"){"Fri"}
            else if(dayString == "Saturday"){"Sat"} else if(dayString == "Sunday"){"Sun"} else{""}


            CalAdd.setText(" $day, $monthshort ${dayOfMonth}")


        },Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH)

        dpd.updateDate(year,mmonth,date)


        dpd.show()


    }


    fun gettime(){



        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val tp = TimePickerDialog(requireContext(),TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            val ampm =   if (hourOfDay >= 12) {

                "PM"
            }

            else {

                "AM"
            }

            //check hour


            //check minute
            val mintcheck = if(minute.toString().length == 1){ "0"}
            else{ "" }

            //12hour
            val hour12 = if (hourOfDay >12){

                hourOfDay - 12
            }
            else{

                hourOfDay
            }

            txttime.setText("$hour12 : $mintcheck$minute$ampm")
        },hour,minute,false)



        tp.show()
    }



    }






