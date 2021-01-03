package com.example.tasks

import android.content.Context
import android.graphics.Paint
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.layout_taskcomplete.view.*
import kotlinx.android.synthetic.main.tasklist_custom.view.*
import kotlinx.android.synthetic.main.tasklist_custom.view.chip
import kotlinx.android.synthetic.main.tasklist_custom.view.ischecked
import kotlinx.android.synthetic.main.tasklist_custom.view.task
import kotlinx.android.synthetic.main.tasklist_custom.view.txtid

class Completelistadapter(val viewmodel: Viewmodel,val c : Context) : RecyclerView.Adapter<Completelistadapter.Mviewholder>(){


    var data = mutableListOf<Taskmodel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mviewholder {

        val inf  = LayoutInflater.from(parent.context).inflate(R.layout.layout_taskcomplete,null)
        return Mviewholder(inf)
    }


    override fun getItemCount(): Int {

        return data.size
    }



    override fun onBindViewHolder(holder: Mviewholder, position: Int) {

        val currentitems = data[position]
        holder.itemView.task2.text= currentitems.Task
        holder.itemView.chip2.text = currentitems.date_time
        holder.itemView.txtid2.text = currentitems.id.toString()
        holder.itemView.ischeccked2.text = currentitems.ischecked.toString()
        holder.itemView.task2.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.itemView.likebtn2.isLiked = true



        //Visibllity
        holder.itemView.ischeccked2.visibility = View.INVISIBLE
        holder.itemView.txtid2.visibility = View.INVISIBLE

        holder.checkbox.likebtn2.setOnLikeListener(object : OnLikeListener{
            override fun liked(likeButton: LikeButton?) {

            }

            override fun unLiked(likeButton: LikeButton?) {

                Handler().postDelayed({
                    val taskmodel = Taskmodel(currentitems.id,currentitems.Task,currentitems.date_time,false)
                    viewmodel.Update(taskmodel)





                },1000)
            }


        })

        holder.itemView.deletetask.setOnClickListener {

            val id = holder.itemView.txtid2.text.toString()
            val a = AlertDialog.Builder(c)
            a.setTitle("Confirm Delete")
            a.setMessage("Are you sure to delete this item")
            a.setIcon(R.drawable.ic_warning_black_24dp)
            a.setPositiveButton("Ok",{DialogInterface, i ->

                viewmodel.deletecompleted(id.toInt())

            })

            a.setNegativeButton("No",{DialogInterface, i ->
                a.setCancelable(true)
            })
            a.show()

        }





    }


    inner class Mviewholder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val checkbox = itemview.findViewById<LikeButton>(R.id.likebtn2)


    }

    fun setdata(model : MutableList<Taskmodel>){

        data = model
        notifyDataSetChanged()
    }





}