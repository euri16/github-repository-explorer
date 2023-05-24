package dev.eury.core.ui_common.components

import dev.eury.core.ui_common.base.BaseViewHolder
import dev.eury.core.ui_common.databinding.ItemGithubRepoTopicBinding

class TopicViewHolder(binding: ItemGithubRepoTopicBinding) :
    BaseViewHolder<ItemGithubRepoTopicBinding>(binding) {

    fun bind(value: String) {
        binding.tvName.text = value
    }
}