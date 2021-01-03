package com.example.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Completetask")
class CompletedTaskmodel(



    var Task : String,
    var date_time : String,
    var tablename : String


)