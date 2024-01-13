package com.example.foodie.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class SearchViewModel:ViewModel() {
    private var searchLiveData= MutableLiveData<List<Meal>>()
    private  var  nullValue = MutableLiveData<Boolean>()

    fun getMealBySearch(id:String){
        RetrofitInstance.api.getSearchMeal(id).enqueue(object :retrofit2.Callback<MealLists>{
            override fun onResponse(call: Call<MealLists>?, response: Response<MealLists>?) {

                if (response?.body() != null && response.body().meals!=null) {
                    nullValue.value=false
                    searchLiveData.value = response.body().meals

                } else{
                    nullValue.value=true
                    Log.d("search crashes", "null")
                }


            }

            override fun onFailure(call: Call<MealLists>?, t: Throwable?) {
                Log.d("search crashes",t.toString())
            }

        })
    }
    fun observeNulValue(): LiveData<Boolean> {
        return  nullValue
    }
    fun observeSearchLiveData():LiveData<List<Meal>>{
        return searchLiveData
    }
}