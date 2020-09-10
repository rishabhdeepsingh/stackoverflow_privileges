package com.example.stackoverflowprivlages.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackoverflowprivlages.data.repository.PrivilegesRepository

class PrivilegesListViewModelFactory(
    private val privilegesRepository: PrivilegesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrivilegesListViewModel(privilegesRepository) as T
    }
}