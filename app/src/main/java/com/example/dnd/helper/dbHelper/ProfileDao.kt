package com.example.dnd.helper.dbHelper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dnd.model.dbmodel.ProfileModel

@Dao
interface ProfileDao {

    @Insert(entity = ProfileModel::class)
    suspend fun insertProfile(taskItem: ProfileModel)

    @Query("SELECT * FROM profiles ORDER BY id DESC")
    suspend fun getAllProfiles(): List<ProfileModel>

    @Delete
    suspend fun deleteProfile(id: ProfileModel)

    @Update
    suspend fun updateProfile(taskItem: ProfileModel)
}