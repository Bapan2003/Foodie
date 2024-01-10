package com.example.foodie.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodie.Activity.RandomMealDetailsActivity
import com.example.foodie.Adapter.PopularItemAdapter
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.Retrofit.RetrofitInstance
import com.example.foodie.ViewModel.HomeViewModel
import com.example.foodie.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Response



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM:HomeViewModel
    private lateinit var meal:Meal
    private lateinit var  popularAdapter:PopularItemAdapter

    companion object{
        const val  MEAL_ID="MEAL_ID"
        const val MEAL_NAME="MEAL_NAME"
        const val MEAL_THUMB="MEAL_THUMB"
        const val MEAL_DETAILS="MEAL_DETAILS"
        const val MEAL_AREA="MEAL_AREA"
        const val MEAL_CATEGORY="MEAL_CATEGORY"
        const val MEAL_YT="MEAL_YT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM= ViewModelProvider(this).get(HomeViewModel::class.java)
        binding =FragmentHomeBinding.inflate(layoutInflater)
        popularAdapter=PopularItemAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularRecyclerView()
        homeMVVM.getPopularMeal()
        observerPopularMeal()
        homeMVVM.getRandomMeal()
        observerRandomMeal()
        onClick()

    }

    private fun preparePopularRecyclerView() {
        binding.popularRecyclerView.apply {
//            layoutManager=GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL,false)
            layoutManager  =LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularAdapter

        }
    }

    private fun observerPopularMeal() {
        homeMVVM.observePopularMealLiveData().observe(viewLifecycleOwner,object : Observer<List<PopularMeal>>{
            override fun onChanged(value: List<PopularMeal>) {

                popularAdapter.setPopularMeal(_mealList = value as ArrayList<PopularMeal>)
            }

        })
    }

    private fun onClick() {
        binding.randomMealImg.setOnClickListener {
            val intent= Intent(activity,RandomMealDetailsActivity::class.java)
            intent.putExtra("MEAL_ID",meal.idMeal)
            intent.putExtra("MEAL_NAME",meal.strMeal)
            intent.putExtra("MEAL_THUMB",meal.strMealThumb)
            intent.putExtra("MEAL_DETAILS",meal.strInstructions)
            intent.putExtra("MEAL_AREA",meal.strArea)
            intent.putExtra("MEAL_CATEGORY",meal.strCategory)
            intent.putExtra("MEAL_YT",meal.strYoutube)
            startActivity(intent)

        }
    }


    fun observerRandomMeal(){
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
//                Log.d("Test",value.idMeal)
                meal=value
                Glide.with(this@HomeFragment)
                    .load(if (value != null) value.strMealThumb else throw NullPointerException("Expression 'value' must not be null"))
                    .into(binding.randomMealImg)
            }
        })
    }


}