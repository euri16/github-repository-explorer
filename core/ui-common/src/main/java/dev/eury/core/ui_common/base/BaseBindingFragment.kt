package dev.eury.core.ui_common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.eury.core.ui_common.navigation.NavigationDirections

abstract class BaseBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    private lateinit var listener: FragmentsListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateBinding(inflater, container, savedInstanceState).also { _binding = it }.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = context as FragmentsListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    fun navigate(direction: NavigationDirections) {
        listener.navigateToDirection(direction)
    }

    interface FragmentsListener {
        fun navigateToDirection(direction: NavigationDirections)
    }
}