package com.vfgodoy.android_system_bar.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vfgodoy.android_system_bar.R
import com.vfgodoy.android_system_bar.databinding.ActivityMainBinding
import com.vfgodoy.android_system_bar.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(), FloatingActionButtonController {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserViewModel: UserViewModel

    var fab : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fab = binding.appBarMain.fab
        setSupportActionBar(binding.appBarMain.toolbar)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top leve l destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_products, R.id.nav_orders
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        observer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_logout -> { mUserViewModel.doLogout() }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observer(){
        mUserViewModel.logout.observe(this, {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
    }

    override fun setFabImageResource(resourceId: Int) {
        fab?.setImageResource(resourceId)
    }

    override fun setFabAction(listener: (View) -> Unit) {
        fab?.setOnClickListener(listener)
    }

    override fun setFabVisibility(visibility: Int) {
        fab?.visibility = visibility
    }

}