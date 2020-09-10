package com.example.stackoverflowprivlages.data.repository

import androidx.lifecycle.LiveData
import com.example.stackoverflowprivlages.data.db.entity.DbPrivilegesEntry

interface PrivilegesRepository {

    suspend fun getPrivilegesList(): LiveData<out List<DbPrivilegesEntry>>

    suspend fun getPrivilegesById(id: Int): LiveData<out DbPrivilegesEntry>

}