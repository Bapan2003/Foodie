package com.example.foodie.Activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodie.DB.MealDatabase
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ViewModel.MealViewModel
import com.example.foodie.ViewModel.MealViewModelFactory
import com.example.foodie.databinding.ActivityRandomMealDetailsBinding
import com.example.foodie.fragment.HomeFragment
import com.example.foodie.fragment.HomeFragment.Companion.MEAL_NAME
import es.dmoral.toasty.Toasty

class MealDetailsActivity : AppCompatActivity() {

    private lateinit var meal_id:String
    private lateinit var meal_name:String
    private lateinit var meal_thumb:String
    private  lateinit var  mealMVVM:MealViewModel
    private lateinit var binding:ActivityRandomMealDetailsBinding
    private var meal:Meal?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRandomMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase=MealDatabase.getInstance(this)
        val viewModelFactory= MealViewModelFactory(mealDatabase)
        mealMVVM= ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)
        getMealInfoByIntent()
        getMealById()
        setInformationInViews()
        onClickFavourites()
    }

    private fun onClickFavourites() {
        binding.floatingFavourite.setOnClickListener {
            meal?.let {
                mealMVVM.insertMeal(it)
                Toasty.success(this,"Meal Saved",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun getMealById() {
//        Log.d("bapansne","viewmodel")
        mealMVVM.getMealData(meal_id)
        mealMVVM.observeMealLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                meal=value!!
                binding.detailsMeal.text = value.strInstructions
                binding.areaTextView.text=value.strArea
                binding.categoryTextView.text=value.strCategory
//        Log.d("meal_name", binding.areaTextView.text as String)
                binding.btnYt.setOnClickListener{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(value.strYoutube))
                    startActivity(intent)
                }
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(this)
            .load(meal_thumb)
            .into(binding.randomImg)
        binding.collapasingToolBar.title=meal_name


        }



    private fun getMealInfoByIntent() {
        val intent=intent
        meal_name=intent.getStringExtra(MEAL_NAME)!!
        meal_id=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        meal_thumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
//        meal_instruction=intent.getStringExtra(HomeFragment.MEAL_DETAILS)!!
//        meal_category=intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!
//        meal_area=intent.getStringExtra(HomeFragment.MEAL_AREA)!!
//        meal_yt=intent.getStringExtra(HomeFragment.MEAL_YT)!!

    }


    override fun onDestroy() {
        super.onDestroy()

    }
}