package pl.kapiz.minecraftmapy.ui.modules.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import com.mikepenz.iconics.utils.sizeDp
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.MainActivityBinding
import pl.kapiz.minecraftmapy.ui.base.OverridesOnBackPressed

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var b: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setSupportActionBar(b.navActionBar)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.mapListFragment,
                R.id.navigation_search,
                R.id.navigation_more
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        b.navView.setupWithNavController(navController)

        b.navView.menu.forEach {
            val icon: IIcon = when (it.itemId) {
                R.id.homeFragment -> CommunityMaterial.Icon2.cmd_home_outline
                R.id.mapListFragment -> CommunityMaterial.Icon.cmd_folder_search_outline
                else -> return@forEach
            }
            it.icon = IconicsDrawable(this, icon).apply {
                sizeDp = 24
            }
        }

        viewModel.toolbarTitle.observe(this) {
            b.navActionBar.title = it
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment is OverridesOnBackPressed) {
            currentFragment.onBackPressed {
                super.onBackPressed()
            }
        } else super.onBackPressed()
    }
}
