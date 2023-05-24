package dev.eury.core.testing.test_values

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates

object LoadStatesTestValues {

    fun getLoadStates(
        refresh: LoadState = LoadState.NotLoading(true),
        prepend: LoadState = LoadState.NotLoading(true),
        append: LoadState = LoadState.NotLoading(true)) : LoadStates {
        return LoadStates(
            refresh = refresh,
            prepend = prepend,
            append = append
        )
    }
    fun getCombinedLoadState(
        refresh: LoadState = LoadState.NotLoading(true),
        prepend: LoadState = LoadState.NotLoading(true),
        append: LoadState = LoadState.NotLoading(true),
        source: LoadStates = getLoadStates(),
        mediator: LoadStates? = null
    ): CombinedLoadStates {
        return CombinedLoadStates(
            refresh = refresh,
            prepend = prepend,
            append = append,
            source = source,
            mediator = mediator
        )
    }

}