package dev.eury.core.ui_common.base

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<out T : ViewBinding>(_binding: ViewBinding) :
    ViewHolder(_binding.root) {

    @Suppress("UNCHECKED_CAST")
    protected val binding = _binding as T
}