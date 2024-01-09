package com.example.foodie.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class HomeViewModel:ViewModel() {
      private var randomMealLiveData= MutableLiveData<Meal>()
      fun getRandomMeal(){
          RetrofitInstance.api.getRandomMealList().enqueue(object :
              retrofit2.Callback<MealLists> {
              override fun onResponse(call: Call<MealLists>?, response: Response<MealLists>?) {

                  if(response?.body() !=null){
                      val randomMeal: Meal = response.body()!!.meals[0]
                      randomMealLiveData.value=randomMeal
//                      Glide.with(this@HomeFragment)
//                          .load(randomMeal.strMealThumb)
//                          .into(binding.randomMealImg)
//                   Log.d("test","Meal id ${randomMeal.idMeal} meal Name ${randomMeal.strMeal}")
                  }
                  else{
                      return
                  }
              }

              override fun onFailure(call: Call<MealLists>?, t: Throwable?) {


              }


          })
      }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return  randomMealLiveData
    }
}