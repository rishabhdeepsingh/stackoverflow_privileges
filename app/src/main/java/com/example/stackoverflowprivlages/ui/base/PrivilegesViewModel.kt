package com.example.stackoverflowprivlages.ui.base

import androidx.lifecycle.ViewModel
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository
import com.example.stackoverflowprivlages.internal.lazyDeferred

abstract class PrivilegesViewModel(
    private val privilegesRepository: PrivilegesRepository
): ViewModel(){

    val privileges by lazyDeferred {
        privilegesRepository.getPrivilegesList()
    }
}