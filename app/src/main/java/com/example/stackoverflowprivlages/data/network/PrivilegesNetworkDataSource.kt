package com.example.stackoverflowprivlages.data.network

import androidx.lifecycle.LiveData
import com.example.stackoverflowprivlages.data.network.response.PrivilegesResponse

interface PrivilegesNetworkDataSource {
    val downloadPrivileges: LiveData<PrivilegesResponse>

    suspend fun fetchPrivileges(
        site: String
    )
}