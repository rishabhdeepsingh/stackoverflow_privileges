package com.example.stackoverflowprivlages

import android.app.Application
import com.example.stackoverflowprivlages.data.db.PrivilegesDatabase
import com.example.stackoverflowprivlages.data.network.*
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepositoryImpl
import com.example.stackoverflowprivlages.ui.PrivilegesListViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

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
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}