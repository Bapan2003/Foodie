package com.example.foodie.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.Adapter.CategoryWiseItemAdapter
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.ViewModel.CategoryItemViewModel
import com.example.foodie.databinding.ActivityCategoryAllMealBinding
import com.example.foodie.fragment.HomeFragment

class CategoryAllMeal : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryAllMealBinding
    private lateinit var categoryMVVM:CategoryItemViewModel
    private lateinit var categoryName:String
    private  lateinit var categoryWiseItemAdapter: CategoryWiseItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryMVVM=ViewModelProvider(this).get(CategoryItemViewModel::class.java)
        binding = ActivityCategoryAllMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryWiseItemAdapter= CategoryWiseItemAdapter()
        getCategoryInfoByItem()
        binding.toolbar.text=categoryName+" Item"
        prepareCategoryItemRecyclerView()
        getCategoryItem()
        onClickCategroy()
    }

    private fun onClickCategroy() {
        categoryWiseItemAdapter.onItemClick = { item ->
            val intent = Intent(this,MealDetailsActivity::class.java)
           intent.putExtra(HomeFragment.MEAL_ID,item.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,item.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,item.strMealThumb)
            startActivity(intent)
        }
    }
    private fun prepareCategoryItemRecyclerView() {
        binding.categoryItemRecyclerView.apply {
            layoutManager=LinearLayoutManager(this@CategoryAllMeal,LinearLayoutManager.VERTICAL,false)
//            Log.d("bapansne","hii")
            adapter=categoryWiseItemAdapter
        }
    }

    private fun getCategoryInfoByItem() {
        val intent=intent
        categoryName= intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!
    }

    private fun getCategoryItem() {
        categoryMVVM.getCategoryItem(categoryName)
        categoryMVVM.obbserveCategoryLiveData().observe(this,object :Observer<List<PopularMeal>>{
            override fun onChanged(value: List<PopularMeal>) {
                Log.d("bapansne","hii")
                categoryWiseItemAdapter.setCategoryWiseItem(value as ArrayList<PopularMeal>,categoryName)
            }

        })
    }
}