package com.example.stackoverflowprivlages.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackoverflowprivlages.data.db.entity.PrivilegeEntry
import com.example.stackoverflowprivlages.data.db.entity.UnitSpecificPrivilegesEntry

@Dao
interface PrivilegesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(privilegeEntry: List<PrivilegeEntry>)

    @Query("SELECT * FROM privileges")
    fun getPrivileges(): LiveData<List<UnitSpecificPrivilegesEntry>>

    @Query("SELECT * FROM privileges WHERE id = :id")
    fun getDetailedPrivilegeById(id: Int): LiveData<UnitSpecificPrivilegesEntry>
}