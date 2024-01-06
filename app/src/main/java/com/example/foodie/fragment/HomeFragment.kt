package com.example.foodie.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ModelClass.MealLists
import com.example.foodie.Retrofit.RetrofitInstance
import com.example.foodie.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Response



class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        RetrofitInstance.api.getRandomMealList().enqueue(object :
            retrofit2.Callback<MealLists> {
            override fun onResponse(call: Call<MealLists>?, response: Response<MealLists>?) {

               if(response?.body() !=null){
                  val randomMeal:Meal = response.body()!!.meals[0]
                   Glide.with(this@HomeFragment)
                       .load(randomMeal.strMealThumb)
                       .into(binding.randomMealImg)
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


}