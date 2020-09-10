package com.example.stackoverflowprivlages.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackoverflowprivlages.data.network.response.PrivilegesResponse
import com.example.stackoverflowprivlages.internal.NoConnectivityException

class PrivilegesNetworkDataSourceImpl(
    private val stackOverflowApiService: StackOverflowApiService
) : PrivilegesNetworkDataSource {

    private val _downloadedPrivileges = MutableLiveData<PrivilegesResponse>()

    override val downloadPrivileges: LiveData<PrivilegesResponse>
        get() = _downloadedPrivileges

    override suspend fun fetchPrivileges(site: String) {
        try {
            val fetchCurrentPrivileges = stackOverflowApiService
                .getPrivilegesAsync(site)
                .await()
            _downloadedPrivileges.postValue(fetchCurrentPrivileges)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", e)
        }
    }
}