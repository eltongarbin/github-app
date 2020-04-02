package com.android.github.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.android.github.domain.GitHubRepository

/**
 * The [DetailViewModel] associated with the [DetailFragment], containing information about the selected
 * [GitHubRepository].
 */
class DetailViewModel(gitHubRepository: GitHubRepository, app: Application) :
    AndroidViewModel(app) {

    private val _selectedRepository = MutableLiveData<GitHubRepository>()
    val selectedRepository: LiveData<GitHubRepository>
        get() = _selectedRepository

    init {
        _selectedRepository.value = gitHubRepository
    }

    val displayStarCounter = Transformations.map(_selectedRepository) {
        "%d stars".format(it.starCounter)
    }

    val displayForkCounter = Transformations.map(_selectedRepository) {
        "%d forks".format(it.forkCounter)
    }
}