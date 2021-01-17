package ru.magzyumov.talan.ui.activity


import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import ru.magzyumov.talan.R
import ru.magzyumov.talan.databinding.ActivityMainBinding
import ru.magzyumov.talan.ui.base.BaseActivity

class MainActivity: BaseActivity() {
    override val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = binding.includeAppBar.toolbar

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> { false }
        }
    }
}