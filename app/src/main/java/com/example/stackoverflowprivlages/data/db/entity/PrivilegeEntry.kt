package com.example.stackoverflowprivlages.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "privileges", indices = [Index(value = ["id"], unique = true)])
data class PrivilegeEntry(
    val description: String,

    val reputation: Int,

    @SerializedName("short_description")
    val shortDescription: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)