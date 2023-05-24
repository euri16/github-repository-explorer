package dev.eury.features.github_repos_list.adapter

import androidx.core.view.isVisible
import dev.eury.core.models.repositories.GithubRepo
import dev.eury.core.ui_common.base.BaseViewHolder
import dev.eury.core.ui_common.components.TopicsAdapter
import dev.eury.core.utils.compact
import dev.eury.features.githubReposList.databinding.ItemGithubRepoBinding

private const val MAX_TOPICS = 8

class GithubRepositoryViewHolder(
    binding: ItemGithubRepoBinding,
    private val onItemClick: (repoId: Long) -> Unit
) : BaseViewHolder<ItemGithubRepoBinding>(binding) {

    fun bind(repo: GithubRepo) {
        with(binding) {
            tvName.text = repo.name
            tvDescription.text = repo.description
            tvStarsCount.text = repo.stargazersCount.compact
            tvLanguage.text = repo.language
            ivLanguage.isVisible = repo.language != null

            rvTopics.adapter = TopicsAdapter().also {
                it.submitList(repo.topics.take(MAX_TOPICS))
            }

            root.setOnClickListener {
                onItemClick(repo.id)
            }
        }
    }
}