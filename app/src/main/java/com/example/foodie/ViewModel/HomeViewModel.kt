package com.example.foodie.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.DB.MealDatabase
import com.example.foodie.ModelClass.CategorieModelClass
import com.example.foodie.ModelClass.Category
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.ModelClass.PopularItemModelClass
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.Retrofit.RetrofitInstance
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(val mealDatabase: MealDatabase):ViewModel() {
      private var randomMealLiveData= MutableLiveData<Meal>()
      private  var categoryLiveData=MutableLiveData<List<Category>>()
      private var popularItemLiveData= MutableLiveData<List<PopularMeal>>()
    private var favouriteLiveData=mealDatabase.mealDao().getAllMeal()
    private var searchLiveData=MutableLiveData<List<Meal>>()
    private  var  nullValue =MutableLiveData<Boolean>()

     fun getPopularMeal(){
        RetrofitInstance.api.getPopularItemMeal("Seafood").enqueue(object :retrofit2.Callback<PopularItemModelClass>{
            override fun onResponse(call: Call<PopularItemModelClass>?, response: Response<PopularItemModelClass>?) {
                if(response?.body() !=null){
//                    Log.d("bapansne","lay")
                    val popularMealList:List<PopularMeal> =response.body()!!.meals
                    popularItemLiveData.value=popularMealList

                }else
                    Log.d("bapansne","else")
            }

            override fun onFailure(call: Call<PopularItemModelClass>?, t: Throwable?) {
                Log.d("bapansne",t.toString())
            }

        })
    }
      fun getRandomMeal(){
          RetrofitInstance.api.getRandomMealList().enqueue(object :
              retrofit2.Callback<MealLists> {
              override fun onResponse(call: Call<MealLists>?, response: Response<MealLists>?) {

                  if(response?.body() !=null){
                      val randomMeal: Meal = response.body().meals[0]
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

        fun getCategory(){
           RetrofitInstance.api.getCategory().enqueue(object :retrofit2.Callback<CategorieModelClass>{
               override fun onResponse(call: Call<CategorieModelClass>?, response: Response<CategorieModelClass>?) {
                  if(response?.body() !=null){
                      categoryLiveData.value= response.body()!!.categories
                  }

               }

               override fun onFailure(call: Call<CategorieModelClass>?, t: Throwable?) {
                   TODO("Not yet implemented")
               }

           })
    }

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
    fun observeNulValue():LiveData<Boolean>{
        return  nullValue
    }
    fun observeCategoryLiveData():LiveData<List<Category>>{
        return categoryLiveData
    }

    fun observePopularMealLiveData():LiveData<List<PopularMeal>>{
        return popularItemLiveData
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return  randomMealLiveData
    }

    fun observeFavouriteMealLiveData():LiveData<List<Meal>>{
        return favouriteLiveData
    }

    fun observeSearchLiveData():LiveData<List<Meal>>{
        return searchLiveData
    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }


}