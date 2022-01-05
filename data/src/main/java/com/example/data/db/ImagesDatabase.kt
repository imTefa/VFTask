package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalImage::class], version = 2)
internal abstract class ImagesDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}