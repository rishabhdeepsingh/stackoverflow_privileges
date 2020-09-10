package com.example.stackoverflowprivlages

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflowprivlages.data.db.entity.UnitSpecificPrivilegesEntry
import com.example.stackoverflowprivlages.ui.PrivilegeItem
import com.example.stackoverflowprivlages.ui.PrivilegesListViewModel
import com.example.stackoverflowprivlages.ui.PrivilegesListViewModelFactory
import com.example.stackoverflowprivlages.ui.base.ScopedActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.privileges_list_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class PrivilegesListActivity : ScopedActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: PrivilegesListViewModelFactory by instance()

    private lateinit var viewModel: PrivilegesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privileges_list_activity)
        println("onCreate is called here")
        Log.d("OnCreate", "PrivilegesListActivity:onCreate")

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PrivilegesListViewModel::class.java)
        bindUI()

    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val privilegesEntries = viewModel.privilegesEntries.await()
        privilegesEntries.observe(this@PrivilegesListActivity, Observer { entries ->
            if (entries == null) return@Observer
            group_loading.visibility = View.GONE
            initRecyclerView(entries.toPrivilegeItems())
        })
    }

    private fun List<UnitSpecificPrivilegesEntry>.toPrivilegeItems(): List<PrivilegeItem> {
        return this.map {
            PrivilegeItem(it)
        }
    }

    private fun initRecyclerView(items: List<PrivilegeItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PrivilegesListActivity)
            adapter = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT)
                .show()
        }
    }
}