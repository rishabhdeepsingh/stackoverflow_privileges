package com.example.stackoverflowprivlages

import android.app.Application
import com.example.stackoverflowprivlages.data.db.PrivilegesDatabase
import com.example.stackoverflowprivlages.data.network.*
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepositoryImpl
import com.example.stackoverflowprivlages.ui.list.PrivilegesListViewModelFactory
import com.example.stackoverflowprivlages.ui.detail.PrivilegesDetailsFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.*

class PrivilegesApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidModule(this@PrivilegesApplication))
        bind() from singleton { PrivilegesDatabase(instance()) }
        bind() from singleton { instance<PrivilegesDatabase>().privilegesDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { StackOverflowApiService(instance()) }
        bind<PrivilegesNetworkDataSource>() with singleton {
            PrivilegesNetworkDataSourceImpl(
                instance()
            )
        }
        bind<PrivilegesRepository>() with singleton {
            PrivilegesRepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider { PrivilegesListViewModelFactory(instance()) }
        bind() from factory { id: Int -> PrivilegesDetailsFactory(id, instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}