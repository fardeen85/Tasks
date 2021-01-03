package com.example.tasks

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.tasklist_custom.view.*

class Recycleradapter(var c : Context, var Viewmodel : Viewmodel)  : RecyclerView.Adapter<Recycleradapter.Viewholder>(),View.OnClickListener{
    override fun onClick(v: View?) {



    }

    var data = mutableListOf<Taskmodel>()
     var pos : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        val inf  = LayoutInflater.from(parent.context).inflate(R.layout.tasklist_custom,null)
        return Viewholder(inf)
    }

    override fun getItemCount(): Int {

        return data.size

    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val currentitems = data[position]
        holder.itemView.task.text= currentitems.Task
        holder.itemView.chip.text = currentitems.date_time
        holder.itemView.txtid.text = currentitems.id.toString()
        holder.itemView.ischecked.text = currentitems.ischecked.toString()
        holder.itemView.likebtn.isLiked = false

        //Visiblity
        holder.itemView.txtid.visibility = View.INVISIBLE
        holder.itemView.ischecked.visibility = View.INVISIBLE



/*
        holder.taskname.transitionName = "taskview${holder.adapterPosition}"
        holder.showdate_time.transitionName = "date_timeview${holder.adapterPosition}"*/

        /*ViewCompat.setTransitionName(holder.itemView.taskupdate,"taskview$position")*/
        holder.row.setOnClickListener {

          /*  pos = holder.adapterPosition

            val extras = FragmentNavigatorExtras(
                holder.taskname to "taskview${holder.adapterPosition}",
                holder.showdate_time to "date_timeview${holder.adapterPosition}")

           val a =ListfragDirections.actionListfragToUpdate(currentitems)
            holder.itemView.findNavController().navigate(a,extras)*/

            val a =ListfragDirections.actionListfragToUpdate(currentitems)
            holder.itemView.findNavController().navigate(a)
            holder.itemView.row.animation = AnimationUtils.loadAnimation(c,R.anim.popup)


        }




     /*  if(holder.itemView.ischecked.text == "true"){


           holder.itemView.task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
           holder.itemView.likebtn.isLiked = true

        }

        else{

           holder.itemView.task.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG.inv()
           holder.itemView.likebtn.isLiked = false
       }*/



        holder.checkbox.setOnLikeListener(object : OnLikeListener{



            override fun liked(likeButton: LikeButton?) {

                Handler().postDelayed({
                    val taskmodel = Taskmodel(currentitems.id,currentitems.Task,currentitems.date_time,true)
                    Viewmodel.Update(taskmodel)
                    Toast.makeText(c,"Wow..you just completed",Toast.LENGTH_SHORT).show()




                },800)


            }

            override fun unLiked(likeButton: LikeButton?) {



            }
        })







    }


     class Viewholder(itemview : View) : RecyclerView.ViewHolder(itemview),View.OnClickListener{

          var positions = 0
         override fun onClick(v: View?) {

             positions = adapterPosition
         }

         val checkbox = itemview.findViewById<LikeButton>(R.id.likebtn)
        val row = itemview.findViewById<ConstraintLayout>(R.id.row)
        val taskname = itemview.findViewById<TextView>(R.id.task)
        val showdate_time = itemview.findViewById<TextView>(R.id.chip)
         



    }

    fun setdata(model : MutableList<Taskmodel>){

        data = model
        notifyDataSetChanged()
    }





    fun checkifempty() : Int{

        if(data.size == 0){

            return 0
        }

        else{

            return 1
        }
    }



    fun getposition() : Int{

        val position = pos
        return position
    }









}