package com.example.foodie.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.Meal
import com.example.foodie.databinding.ItemViewCategoryBinding

class SearchAdapter:RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    lateinit var onItemClick:((Meal)->Unit)
    class SearchViewHolder(val binding:ItemViewCategoryBinding):RecyclerView.ViewHolder(binding.root)
    private val diffUtil=object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
          return SearchViewHolder(ItemViewCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val meal=differ.currentList[position]
      Glide.with(holder.itemView)
          .load(meal.strMealThumb)
          .into(holder.binding.categoryItemImageView)
        holder.binding.itemNameTextView.text=meal.strMeal
        holder.binding.categoryDes.text=meal.strInstructions
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }
}