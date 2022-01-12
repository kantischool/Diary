package com.exmple.diary.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.exmple.diary.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.user, R.id.profile, R.id.task_add))
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHost.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_bar.setupWithNavController(navController)
        bottom_bar.menu.getItem(1).isEnabled = false

        add_task.setOnClickListener {
            navController.navigate(R.id.task_add)
        }
        bottom_bar.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }
    }
}