package com.example.stackoverflowprivlages.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackoverflowprivlages.R
import com.example.stackoverflowprivlages.data.db.entity.DbPrivilegesEntry
import com.example.stackoverflowprivlages.ui.base.ScopedActivity
import com.example.stackoverflowprivlages.ui.detail.PrivilegesDetails
import com.example.stackoverflowprivlages.ui.list.PrivilegeItem
import com.example.stackoverflowprivlages.ui.list.PrivilegesListViewModel
import com.example.stackoverflowprivlages.ui.list.PrivilegesListViewModelFactory
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

    private fun List<DbPrivilegesEntry>.toPrivilegeItems(): List<PrivilegeItem> {
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
            (item as? PrivilegeItem)?.let {
                val bundle = Bundle()
                bundle.putInt("detailsId", item.unitSpecificPrivilegesEntry.id)
                val intent = Intent(applicationContext, PrivilegesDetails::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}