package com.example.stackoverflowprivlages.data.db.entity

import androidx.room.ColumnInfo

data class DbPrivilegesEntry(
    @ColumnInfo(name = "shortDescription")
    val shortDescription: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "reputation")
    val reputation: Int,

    @ColumnInfo(name = "id")
    val id: Int
)