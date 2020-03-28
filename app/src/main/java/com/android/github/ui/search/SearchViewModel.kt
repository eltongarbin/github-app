package com.android.github.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.github.domain.GitHubRepository
import com.android.github.network.GitHubNetwork
import com.android.github.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class GitHubApiStatus { LOADING, ERROR, DONE }

class SearchViewModel : ViewModel() {
    private val _status = MutableLiveData<GitHubApiStatus>()
    val status: LiveData<GitHubApiStatus>
        get() = _status

    private val _repositories = MutableLiveData<List<GitHubRepository>>()
    val repositories: LiveData<List<GitHubRepository>>
        get() = _repositories

    // LiveData to handle navigation to the selected property
    private val _navigateToSelectedRepository = MutableLiveData<GitHubRepository>()
    val navigateToSelectedRepository: LiveData<GitHubRepository>
        get() = _navigateToSelectedRepository

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        refreshDataFromApi()
    }

    /**
     * Refresh data from the api. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromApi() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getRepositoriesDeferred = GitHubNetwork.github.getRepositories()
            try {
                _status.value = GitHubApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getRepositoriesDeferred.await()
                _status.value = GitHubApiStatus.DONE
                _repositories.value = listResult.asDomainModel()
            } catch (e: Exception) {
                _status.value = GitHubApiStatus.ERROR
                _repositories.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param gitHubRepository The [GitHubRepository] that was clicked on.
     */
    fun displayRepositoryDetails(gitHubRepository: GitHubRepository) {
        _navigateToSelectedRepository.value = gitHubRepository
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedRepository is set to null
     */
    fun displayRepositoryDetailsComplete() {
        _navigateToSelectedRepository.value = null
    }
}