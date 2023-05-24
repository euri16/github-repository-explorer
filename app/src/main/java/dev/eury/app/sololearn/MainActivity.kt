package dev.eury.app.sololearn

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.eury.core.ui_common.base.BaseBindingFragment
import dev.eury.core.ui_common.navigation.NavigationDirections
import dev.eury.features.repo_detail.RepositoryDetailFragment

@AndroidEntryPoint
class MainActivity : FragmentActivity(),
    BaseBindingFragment.FragmentsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun navigateToDirection(direction: NavigationDirections) {
        var bundle: Bundle? = null
        val graphDirection = when (direction) {
            is NavigationDirections.RepoListToDetail -> {
                bundle = bundleOf(RepositoryDetailFragment.KEY_REPO_ID to direction.repoId)
                R.id.to_repositoryDetailFragment
            }
        }

        findNavController(R.id.navHost).navigate(graphDirection, bundle)
    }
}