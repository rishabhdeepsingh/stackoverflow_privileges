package com.example.stackoverflowprivlages.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stackoverflowprivlages.data.db.entity.PrivilegeEntry
import com.example.stackoverflowprivlages.data.db.entity.DbPrivilegesEntry

@Dao
interface PrivilegesDao {

    @Query("DELETE FROM privileges")
    fun deleteAllPrivileges()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(privilegeEntry: List<PrivilegeEntry>)

    @Query("SELECT * FROM privileges")
    fun getPrivileges(): LiveData<List<DbPrivilegesEntry>>

    @Query("SELECT * FROM privileges WHERE id = :id")
    fun getDetailedPrivilegeById(id: Int): LiveData<DbPrivilegesEntry>
}