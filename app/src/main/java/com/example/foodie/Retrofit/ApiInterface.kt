package com.example.foodie.Retrofit

import com.example.foodie.ModelClass.MealLists
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("random.php")
    fun getRandomMealList(): Call<MealLists>

}