package com.example.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Viewmodel(application: Application) : AndroidViewModel(application){


     var repository : Repository
     var read : LiveData<MutableList<Taskmodel>>
     var readchecked : LiveData<MutableList<Taskmodel>>


    init {

        var taskdao = Database.getdatatbase(application)!!.taskdao()
        repository = Repository(taskdao)
        read = repository.read
        readchecked = repository.readcomplete

    }

    fun Add(taskmodel: Taskmodel){

       viewModelScope.launch (Dispatchers.IO){

           repository.Add(taskmodel)
       }


        }

    fun Update(taskmodel: Taskmodel){

        viewModelScope.launch(Dispatchers.IO){

            repository.Update(taskmodel)
        }


    }

    fun deletecompleted(id : Int){

        viewModelScope.launch(Dispatchers.IO){

            repository.deletecompleted(id)
        }

    }
    fun delete(taskmodel: Taskmodel){

        viewModelScope.launch(Dispatchers.IO){

            repository.delete(taskmodel)

        }    }

    fun deleteall(){

        viewModelScope.launch(Dispatchers.IO){

            repository.deleteall()
        }
    }




}