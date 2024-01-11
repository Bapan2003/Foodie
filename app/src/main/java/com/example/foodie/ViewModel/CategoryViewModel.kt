package com.example.foodie.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodie.ModelClass.CategorieModelClass
import com.example.foodie.ModelClass.Category
import com.example.foodie.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class CategoryViewModel(): ViewModel() {
    private var categoryLiveData=MutableLiveData<List<Category>>()
    fun getCategory(){
        RetrofitInstance.api.getCategory().enqueue(object : retrofit2.Callback<CategorieModelClass>{
            override fun onResponse(
                call: Call<CategorieModelClass>?,
                response: Response<CategorieModelClass>?
            ) {
                if(response?.body() !=null){
                    categoryLiveData.value=response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategorieModelClass>?, t: Throwable?) {
                call?.cancel()
            }


        })
    }
    fun observeCategryLiveData():LiveData<List<Category>>{
        return categoryLiveData
    }
}