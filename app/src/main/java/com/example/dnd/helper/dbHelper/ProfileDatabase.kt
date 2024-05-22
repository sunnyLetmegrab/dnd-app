package com.example.dnd.helper.dbHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dnd.model.dbmodel.ProfileModel

@Database(entities = [ProfileModel::class], version = 1)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun ProfileDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: ProfileDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): ProfileDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: Builddatabase(context).also {
                    instance = it
                }
            }
        }

        private fun Builddatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ProfileDatabase::class.java,
            "soundPorfiledb"
        ).build()
    }
}