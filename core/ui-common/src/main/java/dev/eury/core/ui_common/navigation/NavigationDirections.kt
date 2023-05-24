package dev.eury.core.ui_common.navigation

sealed class NavigationDirections {
    data class RepoListToDetail(val repoId: Long) : NavigationDirections()
}
