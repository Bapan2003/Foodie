package com.example.foodie.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodie.Activity.CategoryAllMeal
import com.example.foodie.Adapter.CategoryAdapter
import com.example.foodie.ModelClass.Category
import com.example.foodie.R
import com.example.foodie.ViewModel.CategoryItemViewModel
import com.example.foodie.ViewModel.CategoryViewModel
import com.example.foodie.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private  lateinit var categoryBinding:FragmentCategoryBinding
    private  lateinit var categoryMVVM:CategoryViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryBinding=FragmentCategoryBinding.inflate(layoutInflater)
        categoryMVVM=ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryAdapter= CategoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return categoryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareCategoryRecyclerView()
        observeCategory()
        onClickCategroy()
    }

    private fun observeCategory() {
        categoryMVVM.getCategory()
        categoryMVVM.observeCategryLiveData().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            override fun onChanged(value: List<Category>) {
                categoryAdapter.setCategory(value as ArrayList<Category>)
            }
        })
    }

    private fun onClickCategroy() {
        categoryAdapter.onItemClick={
                category ->
            val intent= Intent(context, CategoryAllMeal::class.java)
            intent.putExtra(HomeFragment.MEAL_CATEGORY,category.strCategory)
            intent.putExtra(HomeFragment.MEAL_ID,category.idCategory)
            startActivity(intent)
        }

    }
    private fun prepareCategoryRecyclerView() {
        categoryBinding.categoryRecyclerView.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }
}