package com.example.stackoverflowprivlages.data.repository

import androidx.lifecycle.LiveData
import com.example.stackoverflowprivlages.data.db.PrivilegesDao
import com.example.stackoverflowprivlages.data.db.entity.DbPrivilegesEntry
import com.example.stackoverflowprivlages.data.network.PrivilegesNetworkDataSource
import com.example.stackoverflowprivlages.data.network.STACK_OVERFLOW
import com.example.stackoverflowprivlages.data.network.response.PrivilegesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrivilegesRepositoryImpl(
    private val currentPrivilegesDao: PrivilegesDao,
    private val privilegesNetworkDataSource: PrivilegesNetworkDataSource
) : PrivilegesRepository {

    init {
        privilegesNetworkDataSource.downloadPrivileges.observeForever { newPrivileges ->
            persistFetchedCurrentPrivileges(newPrivileges)
        }
    }

    override suspend fun getPrivilegesList(): LiveData<out List<DbPrivilegesEntry>> {
        return withContext(Dispatchers.IO) {
            currentPrivilegesDao.deleteAllPrivileges()
            privilegesNetworkDataSource.fetchPrivileges(STACK_OVERFLOW)
            return@withContext currentPrivilegesDao.getPrivileges()
        }
    }

    override suspend fun getPrivilegesById(id: Int): LiveData<out DbPrivilegesEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext currentPrivilegesDao.getDetailedPrivilegeById(id)
        }
    }

    private fun persistFetchedCurrentPrivileges(fetchedPrivileges: PrivilegesResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentPrivilegesDao.upsert(fetchedPrivileges.privilegeEntries)
        }
    }
}