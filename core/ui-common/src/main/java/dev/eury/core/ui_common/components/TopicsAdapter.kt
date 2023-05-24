package dev.eury.core.ui_common.components

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.eury.core.ui_common.databinding.ItemGithubRepoTopicBinding
import dev.eury.core.ui_common.utils.inflateViewBinding

class TopicsAdapter : ListAdapter<String, TopicViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {

        /*val binding = ItemGithubRepoTopicBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )*/

        return TopicViewHolder(parent.inflateViewBinding(ItemGithubRepoTopicBinding::inflate))
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}
