package com.example.stackoverflowprivlages.data.network.response


import com.example.stackoverflowprivlages.data.db.entity.PrivilegeEntry
import com.google.gson.annotations.SerializedName

data class PrivilegesResponse(
    @SerializedName("has_more")
    val hasMore: Boolean,

    @SerializedName("items")
    val privilegeEntries: List<PrivilegeEntry>,

    @SerializedName("quota_max")
    val quotaMax: Int,

    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)