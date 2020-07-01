package pl.kapiz.minecraftmapy.ui.modules.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.ActivityMainBinding

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.navActionBar)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_newest,
                R.id.navigation_discover,
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
}
