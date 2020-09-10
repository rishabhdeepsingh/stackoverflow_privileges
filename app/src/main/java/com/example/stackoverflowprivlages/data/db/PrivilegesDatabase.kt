package com.example.stackoverflowprivlages.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stackoverflowprivlages.data.db.entity.PrivilegeEntry

@Database(
    entities = [PrivilegeEntry::class],
    version = 1
)
abstract class PrivilegesDatabase : RoomDatabase() {
    abstract fun privilegesDao(): PrivilegesDao

    companion object {
        @Volatile
        private var instance: PrivilegesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PrivilegesDatabase::class.java, "privileges.db"
            ).build()

    }
}