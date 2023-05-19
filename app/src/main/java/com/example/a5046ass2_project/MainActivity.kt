package com.example.a5046ass2_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.a5046ass2_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain?.toolbar)
        mainViewModel= ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        initNavigationDrawer()
        mainViewModel.getProfileFromRoom()
    }

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra("propertyFlag").equals("WishListFlag")) {
            intent.putExtra("propertyFlag", "")
            val fragmentManager = supportFragmentManager
            val navHostFragment =
                fragmentManager.findFragmentById(R.id.nav_host_fragment) as
                        NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.wishlistFragment)
        }
        initNavigationDrawer()
    }
    private fun initNavigationDrawer() {
        //Create a new Builder with a specific set of top level destinations
        val mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment, R.id.barChartFragment,
            R.id.profileFragment, R.id.profileFragmentEdit,
            R.id.logoutFragment, R.id.wishlistFragment
        ).setOpenableLayout(binding.drawerLayout).build()
        val fragmentManager = supportFragmentManager
        val navHostFragment =
            fragmentManager.findFragmentById(R.id.nav_host_fragment) as
                    NavHostFragment
        val navController = navHostFragment.navController
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController)
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(
            binding.appBarMain.toolbar, navController,
            mAppBarConfiguration
        )
    }

}