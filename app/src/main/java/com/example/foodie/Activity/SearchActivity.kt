package com.example.foodie.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodie.Adapter.SearchAdapter
import com.example.foodie.ModelClass.Meal
import com.example.foodie.ViewModel.SearchViewModel
import com.example.foodie.databinding.ActivitySearchBinding
import com.example.foodie.fragment.HomeFragment
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!
    private  lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchAdapter= SearchAdapter()
        viewModel=ViewModelProvider(this).get(SearchViewModel::class.java)
        prepareRecyclerView()
        binding.editText.requestFocus()
        observeLiveData()
        onClickItem()
        observeNullValue()
        var searchJob: Job?=null
        binding.editText.addTextChangedListener {it ->
            searchJob?.cancel()
            searchJob =lifecycleScope.launch {
                delay(300)
                viewModel.getMealBySearch(it.toString())
            }
        }

    }
    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter=searchAdapter
        }
    }

    private fun onClickItem() {
        searchAdapter.onItemClick ={
                it ->
            val intent= Intent(this,MealDetailsActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID,it.idMeal)
                putExtra(HomeFragment.MEAL_NAME,it.strMeal)
                putExtra(HomeFragment.MEAL_THUMB,it.strMealThumb)
            }
            startActivity(intent)
            finish()
        }
    }


    fun searchMeal(){
        val searchName= binding.editText.text.toString()
        if(searchName.isNotEmpty()){
            viewModel.getMealBySearch(searchName)

        }
    }

    fun observeNullValue(){
        viewModel.observeNulValue().observe(this,object : Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                if (value==true){
                    Toasty.error(applicationContext,"No Meal Found", Toasty.LENGTH_SHORT).show()
                }
            }
        })
    }
    fun observeLiveData(){
        viewModel.observeSearchLiveData().observe(this,object : Observer<List<Meal>> {
            override fun onChanged(value: List<Meal>) {
                if(value !=null){
                    searchAdapter.differ.submitList(value)
                }

            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}