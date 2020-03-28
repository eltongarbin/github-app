package com.android.github.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.github.databinding.ListItemRepositoryBinding
import com.android.github.domain.GitHubRepository

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class SearchListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<GitHubRepository, SearchListAdapter.GitHubRepositoryViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepositoryViewHolder {
        return GitHubRepositoryViewHolder(
            ListItemRepositoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: GitHubRepositoryViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    /**
     * The GitHubRepositoryViewHolder constructor takes the binding variable from the associated
     * ListItemRepository, which nicely gives it access to the full [GitHubRepository] information.
     */
    class GitHubRepositoryViewHolder(private var binding: ListItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitHubRepository: GitHubRepository) {
            binding.repository = gitHubRepository
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [GitHubRepository]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<GitHubRepository>() {
        override fun areItemsTheSame(
            oldItem: GitHubRepository,
            newItem: GitHubRepository
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GitHubRepository,
            newItem: GitHubRepository
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [GitHubRepository]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [GitHubRepository]
     */
    class OnClickListener(val clickListener: (gitHubRepository: GitHubRepository) -> Unit) {
        fun onClick(gitHubRepository: GitHubRepository) = clickListener(gitHubRepository)
    }
}