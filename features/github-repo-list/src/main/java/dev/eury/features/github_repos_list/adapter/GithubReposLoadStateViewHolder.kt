package dev.eury.features.github_repos_list.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dev.eury.core.ui_common.base.BaseViewHolder
import dev.eury.features.githubReposList.databinding.ItemRepoLoadStateBinding

class GithubReposLoadStateViewHolder(
    binding: ItemRepoLoadStateBinding
) : BaseViewHolder<ItemRepoLoadStateBinding>(binding) {

    fun bind(loadState: LoadState, itemCount: Int) {
        with(binding) {
            val isLoading =  loadState is LoadState.Loading
            progress.isVisible = isLoading && itemCount > 1
            container.isVisible = isLoading && itemCount > 1
        }
    }
}