package com.android.github.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.github.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    /**
     * Lazily initialize our [SearchViewModel].
     */
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the SearchFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the SearchViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the repositoryList RecyclerView with clickHandler lambda that
        // tells the viewModel when our repository is clicked
        binding.repositoryList.adapter = SearchListAdapter(SearchListAdapter.OnClickListener {
            viewModel.displayRepositoryDetails(it)
        })

        // Observe the navigateToSelectedRepository LiveData and Navigate when it isn't null
        // After navigating, call displayRepositoryDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedRepository.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayRepositoryDetailsComplete()
            }
        })

        return binding.root
    }
}