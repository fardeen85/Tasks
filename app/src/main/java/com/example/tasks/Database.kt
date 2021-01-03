package com.example.tasks

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [Taskmodel::class],exportSchema = false,version = 1)
abstract class Database : RoomDatabase() {

    public abstract fun taskdao(): Taskdao


    companion object {

        @Volatile
        var INSTANCE: Database? = null

        fun getdatatbase(context: Context): Database {

            var teminstance = INSTANCE

            if (teminstance != null) {

                return teminstance
            }

            synchronized(this) {


                var instance : Database = Room.databaseBuilder(context, Database::class.java, "Taskdb")
                    .build()

                INSTANCE = instance
                return instance


            }


        }


    }
}