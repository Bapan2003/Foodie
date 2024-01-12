package com.example.foodie.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.DB.MealDatabase
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.Retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase):ViewModel() {
      private  var MealLiveData=MutableLiveData<Meal>()
      fun getMealData(idMeal:String){
          RetrofitInstance.api.getMealById(idMeal).enqueue(object: retrofit2.Callback<MealLists>{
              override fun onResponse(call: Call<MealLists>?, response: Response<MealLists>?) {
                  if(response?.body() !=null){
                      val meal:Meal=response.body()!!.meals[0]
                      MealLiveData.value=meal
                  }else
                      Log.d("bapansne","else")
              }

              override fun onFailure(call: Call<MealLists>?, t: Throwable?) {
                  Log.d("bapansne",t.toString())
              }

          })
      }

    fun observeMealLiveData():LiveData<Meal>{
        return MealLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }


}