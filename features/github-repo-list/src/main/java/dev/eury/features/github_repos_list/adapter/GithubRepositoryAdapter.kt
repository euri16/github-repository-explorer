package dev.eury.features.github_repos_list.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.ui_common.utils.inflateViewBinding
import dev.eury.features.githubReposList.databinding.ItemGithubRepoBinding

class GithubRepositoryAdapter(
    private val onItemClick: (repoId: Long) -> Unit
) : PagingDataAdapter<GithubRepo, GithubRepositoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryViewHolder {
        return GithubRepositoryViewHolder(
            parent.inflateViewBinding(ItemGithubRepoBinding::inflate),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: GithubRepositoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubRepo>() {
            override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
                return oldItem == newItem
            }

        }
    }
}