package dev.eury.features.github_repos_list.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import dev.eury.core.ui_common.utils.inflateViewBinding
import dev.eury.features.githubReposList.databinding.ItemRepoLoadStateBinding

class GithubReposLoadStateAdapter : LoadStateAdapter<GithubReposLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GithubReposLoadStateViewHolder {
        return GithubReposLoadStateViewHolder(parent.inflateViewBinding(ItemRepoLoadStateBinding::inflate))
    }

    override fun onBindViewHolder(holder: GithubReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, itemCount)
    }
}