package com.example.foodie.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.Retrofit.RetrofitInstance
import com.example.foodie.ViewModel.HomeViewModel
import com.example.foodie.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Response



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM= ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMVVM.getRandomMeal()
        observerRandomMeal()

    }



    fun observerRandomMeal(){
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
//                Log.d("Test",value.idMeal)
                Glide.with(this@HomeFragment)
                    .load(value!!.strMealThumb)
                    .into(binding.randomMealImg)
            }
        })
    }


}