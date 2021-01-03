package com.example.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator
import kotlinx.android.synthetic.main.fragment_frag_complete.view.*
import kotlinx.android.synthetic.main.fragment_listfrag.view.*
import kotlinx.android.synthetic.main.layout_taskcomplete.view.*


class frag_complete : Fragment() {

    lateinit var completelistadapter: Completelistadapter
    lateinit var mviewmodel : Viewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_frag_complete, container, false)


        v.recyclercomplete.visibility = View.INVISIBLE
        v.nocompletemessage.visibility = View.INVISIBLE
        v.imgnotask.visibility = View.INVISIBLE
        v.recyclercomplete.visibility = View.INVISIBLE


        mviewmodel = ViewModelProvider(this).get(Viewmodel::class.java)

        completelistadapter = Completelistadapter(mviewmodel,requireContext())

        mviewmodel.readchecked.observe(viewLifecycleOwner, Observer { complete ->


            completelistadapter.setdata(complete)
        })

        mviewmodel.readchecked.observe(viewLifecycleOwner,object : Observer<MutableList<Taskmodel>>{
            override fun onChanged(t: MutableList<Taskmodel>) {
                if (t == null){


                    Toast.makeText(requireContext(),"empty",Toast.LENGTH_SHORT).show()


                }
                else{

                }
            }


        })




        v.recyclercomplete.layoutManager = LinearLayoutManager(requireContext())

        v.recyclercomplete.setHasFixedSize(true)

        v.recyclercomplete.adapter = completelistadapter
        v.recyclercomplete.itemAnimator = DefaultItemAnimator()







        completelistadapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()


                if (completelistadapter.getItemCount() == 0) {

                    v.imgnotask.visibility = View.VISIBLE
                    v.nocompletemessage.visibility = View.VISIBLE

                } else {

                    v.recyclercomplete.visibility = View.VISIBLE

                    v.recyclercomplete.setItemAnimator(SlideInOutBottomItemAnimator(v.recyclercomplete));


                /*    mAdapter = MyAdapter(this)

                    val animatorAdapter = AlphaAnimatorAdapter(mAdapter, mRecyclerView)
                    mRecyclerView.setAdapter(animatorAdapter)*/
                }



            }
        })

        v.back.setOnClickListener {

            findNavController().navigate(R.id.action_frag_complete_to_listfrag)

        }


        return  v
    }


}
