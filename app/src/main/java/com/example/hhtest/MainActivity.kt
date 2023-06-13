package com.example.hhtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.core.datasource.ShoppingCartPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var shoppingCartPreferences: ShoppingCartPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shoppingCartPreferences = ShoppingCartPreferences(applicationContext)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.e("TAG ACTIVITY", "Delte")
//        shoppingCartPreferences.deleteAllShoppingCartItems()
//    }
}