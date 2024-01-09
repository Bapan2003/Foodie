package com.example.foodie.Activity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.foodie.databinding.ActivityRandomMealDetailsBinding
import com.example.foodie.fragment.HomeFragment
import com.example.foodie.fragment.HomeFragment.Companion.MEAL_NAME

class RandomMealDetailsActivity : AppCompatActivity() {
    private lateinit var meal_id:String
    private lateinit var meal_name:String
    private lateinit var meal_thumb:String
    private lateinit var meal_instruction:String
    private lateinit var meal_category:String
    private lateinit var meal_area:String
    private lateinit var meal_yt:String

    private lateinit var binding:ActivityRandomMealDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRandomMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMealInfo()
        setInformationInViews()

    }

    private fun setInformationInViews() {
        Glide.with(this)
            .load(meal_thumb)
            .into(binding.randomImg)
        binding.collapasingToolBar.title=meal_name
        binding.detailsMeal.text = meal_instruction
        binding.areaTextView.text=meal_area
        binding.categoryTextView.text=meal_category
//        Log.d("meal_name", binding.areaTextView.text as String)
        binding.btnYt.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal_yt))
            startActivity(intent)

        }

    }

    private fun getMealInfo() {
        val intent=intent
        meal_name=intent.getStringExtra(MEAL_NAME)!!
        meal_id=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        meal_thumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
        meal_instruction=intent.getStringExtra(HomeFragment.MEAL_DETAILS)!!
        meal_category=intent.getStringExtra(HomeFragment.MEAL_CATEGORY)!!
        meal_area=intent.getStringExtra(HomeFragment.MEAL_AREA)!!
        meal_yt=intent.getStringExtra(HomeFragment.MEAL_YT)!!

    }
}