package com.example.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class LocalImage(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "author_name") val author: String,
    @ColumnInfo(name = "load_uri") val uri: String,
    @ColumnInfo(name = "open_link") val openUrl: String,
)