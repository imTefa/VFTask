package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface ImageDao {

    @Query("SELECT * FROM localimage")
    fun getAll(): List<LocalImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg images: LocalImage)


}