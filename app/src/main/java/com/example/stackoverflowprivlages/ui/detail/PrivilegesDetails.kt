package com.example.stackoverflowprivlages.ui.detail

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stackoverflowprivlages.BuildConfig
import com.example.stackoverflowprivlages.R
import com.example.stackoverflowprivlages.ui.base.ScopedActivity
import kotlinx.android.synthetic.main.privileges_details_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.factory

class PrivilegesDetails : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var viewModel: PrivilegesDetailsViewModel

    private val viewModelFactoryInstanceFactory: ((Int) -> PrivilegesDetailsFactory) by factory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privileges_details_fragment)
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        var id: Int = -1
        if (extras != null) {
            id = extras.getInt("detailsId")
        }
        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(id))
            .get(PrivilegesDetailsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val privilegesEntries = viewModel.privilege.await()
        privilegesEntries.observe(this@PrivilegesDetails, Observer { entry ->
            if (entry == null) return@Observer
            details_reputation.text = entry.reputation.toString()
            details_short_description.text = entry.shortDescription
            details_description.text = entry.description
        })
    }
}