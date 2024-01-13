package com.example.foodie.Retrofit

import com.example.foodie.ModelClass.CategorieModelClass
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.ModelClass.PopularItemModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("random.php")
    fun getRandomMealList(): Call<MealLists>

    @GET("lookup.php?")
    fun getMealById(@Query("i")idMeal:String): Call<MealLists>
    @GET("filter.php?")
    fun getPopularItemMeal(@Query("c")mealName:String):Call<PopularItemModelClass>

    @GET("categories.php")
    fun getCategory():Call<CategorieModelClass>

    @GET("search.php?")
    fun getSearchMeal(@Query("s")id:String):Call<MealLists>

}