package com.example.iamcalmapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase?=null
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes"
                ).fallbackToDestructiveMigration().build()
                INSTANCE=instance
                instance
            }
        }
    }
}