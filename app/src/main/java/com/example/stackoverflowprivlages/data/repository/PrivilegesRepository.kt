package com.example.stackoverflowprivlages.data.repository

import androidx.lifecycle.LiveData
import com.example.stackoverflowprivlages.data.db.entity.UnitSpecificPrivilegesEntry

interface PrivilegesRepository {

    suspend fun getPrivilegesList(): LiveData<out List<UnitSpecificPrivilegesEntry>>

}