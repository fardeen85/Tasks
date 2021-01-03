package com.example.tasks

import androidx.lifecycle.LiveData

class Repository(val taskdao : Taskdao){

    suspend fun Add(taskmodel: Taskmodel){

        taskdao.Add(taskmodel)
    }

    var read = taskdao.read()

    suspend fun Update( taskmodel: Taskmodel){

        taskdao.Update(taskmodel)
    }

    var readcomplete = taskdao.readchecked()

    suspend fun deleteall(){

        taskdao.delete_all()
    }

    suspend fun delete(taskmodel: Taskmodel){

        taskdao.Delete(taskmodel)
    }


  suspend fun deletecompleted(id : Int){

      taskdao.deletecompleted(id)
  }


}