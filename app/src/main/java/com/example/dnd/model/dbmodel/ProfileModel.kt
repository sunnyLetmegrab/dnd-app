package com.example.dnd.model.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Profiles")
class ProfileModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "profileName") var profileName: String,
    @ColumnInfo(name = "startTime") var startTime: String,
    @ColumnInfo(name = "endTime") var endTime: String,
    @ColumnInfo(name = "createdAt", defaultValue = "CURRENT_TIMESTAMP") var createdAt: Int = 0,
    @ColumnInfo(name = "updateAt", defaultValue = "CURRENT_TIMESTAMP") var updateAt: Int = 0,
)
@Entity(tableName = "ActiveProfiles")
class ActiveProfileModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "profileName") var profileName: String,
    @ColumnInfo(name = "startTime") var startTime: String,
    @ColumnInfo(name = "endTime") var endTime: String,
    @ColumnInfo(name = "createdAt", defaultValue = "CURRENT_TIMESTAMP") var createdAt: Int = 0,
    @ColumnInfo(name = "updateAt", defaultValue = "CURRENT_TIMESTAMP") var updateAt: Int = 0,
)
