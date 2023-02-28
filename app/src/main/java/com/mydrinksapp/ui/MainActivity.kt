package com.mydrinksapp.ui

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initNavigationView()

    }

    private fun initToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initNavigationView() {
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.allCocktailsFragment,
            R.id.ingredientsFragment,
            R.id.searchFragment,
            R.id.favoritesFragment,
            )
            .setOpenableLayout(drawer)
            .build()

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            navigationView.setBackgroundColor(getColor(R.color.white))
            navigationView.itemTextColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            navigationView.itemIconTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> navController.navigate(R.id.homeFragment)
            R.id.allCocktailsFragment -> navController.navigate(R.id.allCocktailsFragment)
            R.id.ingredientsFragment -> navController.navigate(R.id.ingredientsFragment)
            R.id.searchFragment -> navController.navigate(R.id.searchFragment)
            R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
            R.id.exit -> finish()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}