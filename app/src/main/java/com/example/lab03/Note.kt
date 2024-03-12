package com.example.lab03

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    var name: String,
    var description: String,
    var isChecked: Boolean,
    var resourceId: Int,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)