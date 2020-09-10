package com.example.stackoverflowprivlages.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository

class PrivilegesDetailsFactory(
    private val detailsId: Int,
    private val privilegesRepository: PrivilegesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrivilegesDetailsViewModel(detailsId, privilegesRepository) as T
    }
}