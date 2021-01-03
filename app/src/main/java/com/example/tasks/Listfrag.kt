package com.example.tasks

import android.app.AlertDialog
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet.view.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_listfrag.*
import kotlinx.android.synthetic.main.fragment_listfrag.view.*


class Listfrag : Fragment() {

    lateinit var mviewmodel : Viewmodel
    lateinit var recycleradapter : Recycleradapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =inflater.inflate(R.layout.fragment_listfrag, container, false)


        v.text.visibility = View.GONE
        v.displayimgs.visibility = View.GONE




        v.fab.setOnClickListener {

            findNavController().navigate(R.id.action_listfrag_to_add2)
        }





        mviewmodel = ViewModelProvider(this).get(Viewmodel::class.java)

        recycleradapter = Recycleradapter(requireContext(),mviewmodel)




        mviewmodel.read.observe(viewLifecycleOwner, Observer { task ->

            recycleradapter.setdata(task)


        })




        v.recycle.layoutManager = LinearLayoutManager(requireContext())
        v.recycle.setHasFixedSize(true)

        v.recycle.adapter = recycleradapter
        v.recycle.setHasFixedSize(true)
        v.recycle.itemAnimator = DefaultItemAnimator()



       


        recycleradapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()


                if (recycleradapter.getItemCount() == 0) {

                    v.displayimgs.visibility = View.VISIBLE
                    v.text.visibility = View.VISIBLE

                } else {

                    v.recycle.visibility = View.VISIBLE
                }



            }
        })




        v.bottomappbar.setNavigationOnClickListener {

           val BottomSheetDialog = BottomSheetDialog(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.bottomsheet,null,false)
            BottomSheetDialog.setContentView(view)
            BottomSheetDialog.show()
            val showchecked = view.findViewById<LinearLayout>(R.id.complete)
            showchecked.setOnClickListener {


                findNavController().navigate(R.id.action_listfrag_to_frag_complete)
                BottomSheetDialog.cancel()

            }


            val shownotificationsettings = view.findViewById<LinearLayout>(R.id.enotificationsetting)
            shownotificationsettings.setOnClickListener {

                findNavController().navigate(R.id.action_listfrag_to_set_notificationt_ime)
                BottomSheetDialog.cancel()
            }

        }

        v.bottomappbar.setOnMenuItemClickListener {

            when(it.itemId){

                R.id.delete -> {


                    val a = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    a.setTitle("Confirm Delete All")
                    a.setMessage("All tasks in the list will be deleted")
                    a.setIcon(R.drawable.ic_warning_black_24dp)
                    a.setPositiveButton("Ok",{DialogInterface, i ->

                        mviewmodel.deleteall()

                    })

                    a.setNegativeButton("No",{DialogInterface, i ->
                        a.setCancelable(true)
                    })
                    a.show()
                    false
                }


                else->{
                    false
                }
            }
        }





        return  v
    }


    fun checked(id : Int,Taskname : String, date_time : String,ischecked : Boolean){


        mviewmodel = ViewModelProvider(this).get(Viewmodel::class.java)
        val taskmodel = Taskmodel(id,Taskname,date_time,ischecked)
        mviewmodel.Update(taskmodel)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }




}

