package com.example.foodie.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.foodie.DB.MealDatabase

class MealViewModelFactory(val mealDatabase: MealDatabase):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}