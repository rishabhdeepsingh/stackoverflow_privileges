package com.example.stackoverflowprivlages.data.repository

import androidx.lifecycle.LiveData
import com.example.stackoverflowprivlages.data.db.PrivilegesDao
import com.example.stackoverflowprivlages.data.db.entity.UnitSpecificPrivilegesEntry
import com.example.stackoverflowprivlages.data.network.PrivilegesNetworkDataSource
import com.example.stackoverflowprivlages.data.network.STACK_OVERFLOW
import com.example.stackoverflowprivlages.data.network.response.PrivilegesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class PrivilegesRepositoryImpl(
    private val currentPrivilegesDao: PrivilegesDao,
    private val privilegesNetworkDataSource: PrivilegesNetworkDataSource
) : PrivilegesRepository {

    init {
        privilegesNetworkDataSource.downloadPrivileges.observeForever { newPrivileges ->
            persistFetchedCurrentPrivileges(newPrivileges)
        }
    }

    override suspend fun getPrivilegesList(): LiveData<out List<UnitSpecificPrivilegesEntry>> {
        return withContext(Dispatchers.IO) {
            initPrivilegesData()
            return@withContext currentPrivilegesDao.getPrivileges()
        }
    }

    override suspend fun getPrivilegesById(id: Int): LiveData<out UnitSpecificPrivilegesEntry> {
        return withContext(Dispatchers.IO){
            initPrivilegesData()
            return@withContext currentPrivilegesDao.getDetailedPrivilegeById(id)
        }
    }

    private fun persistFetchedCurrentPrivileges(fetchedPrivileges: PrivilegesResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentPrivilegesDao.upsert(fetchedPrivileges.privilegeEntries)
        }
    }

    private suspend fun initPrivilegesData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchPrivileges()
        }
    }

    private suspend fun fetchPrivileges() {
        privilegesNetworkDataSource.fetchPrivileges(STACK_OVERFLOW)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}