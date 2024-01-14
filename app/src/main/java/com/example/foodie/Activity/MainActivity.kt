package com.example.foodie.Activity

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodie.DB.MealDatabase
import com.example.foodie.R
import com.example.foodie.R.layout.fragment_home
import com.example.foodie.ViewModel.HomeViewModel
import com.example.foodie.ViewModel.MainViewModelFactory
import com.example.foodie.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    var backPressedTime: Long = 0
    val viewmodel:HomeViewModel by lazy{
        val mealDatabase= MealDatabase.getInstance(this)
        val mainViewModelFactory=MainViewModelFactory(mealDatabase)
        ViewModelProvider(this,mainViewModelFactory).get(HomeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btmNavigation= findViewById<BottomNavigationView>(R.id.btmNav)
        val navController = Navigation.findNavController(this, R.id.hostFragment)

        NavigationUI.setupWithNavController(btmNavigation,navController)
        val bottomNavSelectorStates = arrayOf(
            intArrayOf(android.R.attr.state_selected),//selected
            intArrayOf(-android.R.attr.state_selected),//default/un-selected
        )


        val colors = intArrayOf(
            ContextCompat.getColor(this,R.color.black), ContextCompat.getColor(this, R.color.iconColor)
        )


        btmNavigation.itemIconTintList =
            ColorStateList(bottomNavSelectorStates, colors)
        btmNavigation.itemTextColor =
            ColorStateList(bottomNavSelectorStates, colors)

    }

    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}