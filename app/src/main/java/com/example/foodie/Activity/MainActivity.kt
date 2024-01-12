package com.example.foodie.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }
}