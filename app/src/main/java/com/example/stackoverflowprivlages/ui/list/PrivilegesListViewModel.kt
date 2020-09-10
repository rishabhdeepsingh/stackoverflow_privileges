package com.example.stackoverflowprivlages.ui.list

import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository
import com.example.stackoverflowprivlages.internal.lazyDeferred
import com.example.stackoverflowprivlages.ui.base.PrivilegesViewModel

class PrivilegesListViewModel(
    private val privilegesRepository: PrivilegesRepository
) : PrivilegesViewModel(privilegesRepository) {

    val privilegesEntries by lazyDeferred {
        privilegesRepository.getPrivilegesList()
    }

}