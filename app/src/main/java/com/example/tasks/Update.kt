package com.example.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.layout_taskcomplete.view.*
import java.text.SimpleDateFormat
import java.util.*


class Update : Fragment() {

    private val args by navArgs<UpdateArgs>()
    lateinit var mviewmodel : Viewmodel
    lateinit var recycleradapter: Recycleradapter
    lateinit var viewholder: Recycleradapter.Viewholder
    lateinit var c : Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_update, container, false)

        mviewmodel = ViewModelProvider(this).get(Viewmodel::class.java)


        c = Calendar.getInstance()


        v.backupdate.setOnClickListener {

          /*  val extras = FragmentNavigatorExtras(v.taskupdate to "taskview",
                v.chip_datetime to "date_timeview")*/

            findNavController().navigate(R.id.action_update_to_listfrag)
        }

        v.txtupdate.setText(args.currentitems.Task.toString())
        v.taskupdate.setText(args.currentitems.Task)
        v.chip_datetime.setText(args.currentitems.date_time.toString())


        v.updatesave.setOnClickListener {

            if(v.taskupdate.text.toString().length == 0 ){

                Toast.makeText(requireContext(),"Task field is empty",Toast.LENGTH_LONG).show()
            }
            else {

                val dateandtime = v.updatecalender.text.toString() + "," + updatetime.text.toString()
                val taskupdate = v.txtupdate.text.toString()
                val taskmodel = Taskmodel(args.currentitems.id, taskupdate, dateandtime, false)
                mviewmodel.Update(taskmodel)
                /* val extras = FragmentNavigatorExtras(v.taskupdate to "taskview",
            v.chip_datetime to "date_timeview"
            )*/
                findNavController().navigate(R.id.action_update_to_listfrag)
            }

        }

        v.timeupdatepicker.setOnClickListener {

            gettime()

        }

        v.dateupdatepicker.setOnClickListener {

            getDate()
        }


        v.imagedelete.setOnClickListener {

            val a = AlertDialog.Builder(requireContext())
            a.setTitle("Confirm Delete")
            a.setMessage("Are you sure to delete this item")
            a.setIcon(R.drawable.ic_warning_black_24dp)
            a.setPositiveButton("Ok",{DialogInterface, i ->

                mviewmodel.delete(args.currentitems)
                findNavController().navigate(R.id.action_update_to_listfrag)
            })

            a.setNegativeButton("No",{DialogInterface, i ->
                a.setCancelable(true)
            })
            a.show()


        }


        return  v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }*/

    }


 /*   fun transitionpositions(){

        recycleradapter = Recycleradapter(requireContext(),mviewmodel)
        viewholder = Recycleradapter.Viewholder(requireView())
        taskupdate.transitionName = "taskview${viewholder.adapterPosition}}"
        chip_datetime.transitionName = "date_timeview${viewholder.adapterPosition}"
        Toast.makeText(requireContext(),"${viewholder.adapterPosition}",Toast.LENGTH_SHORT).show()

    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    fun getDate(){


        val date = c.get(Calendar.DAY_OF_MONTH)
        val mmonth = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        val dayofweek = c.get(Calendar.DAY_OF_WEEK)


        val dpd = DatePickerDialog(requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

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


                updatecalender.setText(" $day $monthshort ${dayOfMonth+1}")


            },
            Calendar.YEAR,
            Calendar.MONTH,
            Calendar.DAY_OF_MONTH)

        dpd.updateDate(year,mmonth,date)


        dpd.show()


    }


    fun gettime(){



        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val tp = TimePickerDialog(requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                val ampm =   if (hourOfDay >= 12) {

                    "PM"
                }

                else {

                    "AM"
                }

                //check hour

                val hourcheck = if (hourOfDay.toString().length == 1) { "0" }
                else{ "" }

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

                updatetime.setText("$hourcheck$hour12 : $mintcheck$minute$ampm")
            },hour,minute,false)



        tp.show()
    }


}