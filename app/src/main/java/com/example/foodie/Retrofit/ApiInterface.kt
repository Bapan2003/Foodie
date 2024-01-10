package com.example.foodie.Retrofit

import com.example.foodie.ModelClass.MealLists
import com.example.foodie.ModelClass.PopularItemModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("random.php")
    fun getRandomMealList(): Call<MealLists>

    @GET("filter.php?c=Seafood")
    fun getPopularItemMeal(@Query("c")mealName:String):Call<PopularItemModelClass>

}