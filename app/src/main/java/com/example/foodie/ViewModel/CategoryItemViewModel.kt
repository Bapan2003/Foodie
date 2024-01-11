package com.example.foodie.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.ModelClass.PopularItemModelClass
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class CategoryItemViewModel:ViewModel() {
    private var categoryItemLiveData=MutableLiveData<List<PopularMeal>>()
    fun getCategoryItem(id:String){
        RetrofitInstance.api.getPopularItemMeal(id).enqueue(object :retrofit2.Callback<PopularItemModelClass>{
            override fun onResponse(call: Call<PopularItemModelClass>?, response: Response<PopularItemModelClass>?) {
                if(response?.body() !=null){
//                    Log.d("bapansne","this")
                    val popularMealList:List<PopularMeal> =response.body()!!.meals
                    categoryItemLiveData.value=popularMealList

                }else
                    Log.d("bapansne","else")
            }

            override fun onFailure(call: Call<PopularItemModelClass>?, t: Throwable?) {
                Log.d("bapansne",t.toString())
            }

        })
    }

    fun obbserveCategoryLiveData():LiveData<List<PopularMeal>>{
//        Log.d("bapansne","this${categoryItemLiveData.value?.size}")
        return categoryItemLiveData
    }

}