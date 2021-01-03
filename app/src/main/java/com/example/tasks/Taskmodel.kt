package com.example.tasks

import android.os.Parcelable
import androidx.room.*
import androidx.room.Database
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "Tasks")

data class Taskmodel (


    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var Task : String,
    var date_time : String,
    var ischecked : Boolean

) : Parcelable