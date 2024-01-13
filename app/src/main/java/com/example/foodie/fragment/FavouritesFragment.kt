package com.example.foodie.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodie.Activity.MainActivity
import com.example.foodie.Activity.MealDetailsActivity
import com.example.foodie.Activity.SearchActivity
import com.example.foodie.Adapter.FavouriteAdapter
import com.example.foodie.ModelClass.Meal
import com.example.foodie.R
import com.example.foodie.ViewModel.HomeViewModel
import com.example.foodie.databinding.FragmentFavouritesBinding
import com.google.android.material.snackbar.Snackbar
import es.dmoral.toasty.Toasty

class FavouritesFragment : Fragment() {
    private lateinit var favouriteBinding:FragmentFavouritesBinding
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var viewModel: HomeViewModel
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       favouriteBinding=FragmentFavouritesBinding.inflate(layoutInflater)
       favouriteAdapter= FavouriteAdapter()
       viewModel=(activity as MainActivity).viewmodel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return favouriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeLiveData()

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                val meal:Meal=favouriteAdapter.differ.currentList[position]
                if(meal !=null){
                    viewModel.deleteMeal(meal)
                    Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_SHORT).setAction(
                        "Undo",
                        {
                            viewModel.insertMeal(meal)
                        }
                    ).show()
                }
                else{
                    Toasty.error(requireContext(),"Meal Not Found",Toasty.LENGTH_SHORT).show()
                }

            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(favouriteBinding.recyclerView)

        onClickItemView()
        onSearchIconClick()
    }
    private fun onSearchIconClick() {
        favouriteBinding.linearLayout.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)

        }
    }

    private fun onClickItemView() {
        favouriteAdapter.onItemClick={
                meal ->
            val intent= Intent(activity, MealDetailsActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID,meal.idMeal)
                putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
                putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            }

            startActivity(intent)
        }
    }

    private fun observeLiveData() {
        viewModel.observeFavouriteMealLiveData().observe(viewLifecycleOwner,object :Observer<List<Meal>>{
            override fun onChanged(value: List<Meal>) {
                favouriteAdapter.differ.submitList(value)
            }
        })
    }

    private fun prepareRecyclerView() {
        favouriteBinding.recyclerView.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=favouriteAdapter
        }
    }
}