package com.android.github.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.github.domain.GitHubRepository

/**
 * Simple ViewModel factory that provides the GitHubRepository and context to the ViewModel.
 */
class DetailViewModelFactory(
    private val gitHubRepository: GitHubRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(gitHubRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
