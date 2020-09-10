package com.example.stackoverflowprivlages.ui

import com.example.stackoverflowprivlages.R
import com.example.stackoverflowprivlages.data.db.entity.UnitSpecificPrivilegesEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.privileges_item.*

class PrivilegeItem(
    private val unitSpecificPrivilegesEntry: UnitSpecificPrivilegesEntry
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_description.text = unitSpecificPrivilegesEntry.description
            textView_short_description.text = unitSpecificPrivilegesEntry.shortDescription
            textView_reputation.text =
                unitSpecificPrivilegesEntry.reputation.toString()
        }
    }

    override fun getLayout(): Int  = R.layout.privileges_item

}