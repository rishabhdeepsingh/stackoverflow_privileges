package com.example.stackoverflowprivlages.ui.detail

import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository
import com.example.stackoverflowprivlages.internal.lazyDeferred
import com.example.stackoverflowprivlages.ui.base.PrivilegesViewModel

class PrivilegesDetailsViewModel(
    private val id: Int,
    private val privilegesRepository: PrivilegesRepository
) : PrivilegesViewModel(privilegesRepository) {

    val privilege by lazyDeferred {
        privilegesRepository.getPrivilegesById(id)
    }

}