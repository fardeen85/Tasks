package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Update


@Dao
interface Taskdao {

    @Insert()
    suspend fun Add(taskmodel: Taskmodel)

    @Query("select * from tasks where ischecked = :notchecked order by id Asc")
    fun read(notchecked : Boolean = false) : LiveData<MutableList<Taskmodel>>

    @Update()
    suspend fun Update(taskmodel: Taskmodel)

    @Delete()
    suspend fun Delete(taskmodel: Taskmodel)

    @Query("delete from tasks")
    suspend fun delete_all()


    @Query("select * from tasks where ischecked = :checked order by id Asc")
    fun readchecked(checked : Boolean = true) : LiveData<MutableList<Taskmodel>>

    @Query("delete from tasks where id = :id")
    fun deletecompleted(id : Int)







}