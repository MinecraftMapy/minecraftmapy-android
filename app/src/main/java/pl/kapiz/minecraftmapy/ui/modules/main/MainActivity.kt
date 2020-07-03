package pl.kapiz.minecraftmapy.ui.modules.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.ActivityMainBinding
import pl.kapiz.minecraftmapy.ui.base.OverridesOnBackPressed

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(b.navActionBar)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_maps,
                R.id.navigation_search,
                R.id.navigation_more
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        b.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        if (currentFragment is OverridesOnBackPressed) {
            currentFragment.onBackPressed {
                super.onBackPressed()
            }
        } else super.onBackPressed()

    }
}
