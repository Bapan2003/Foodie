package com.example.foodie.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.databinding.CategoryItemsBinding
import com.example.foodie.ModelClass.Category


class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList=ArrayList<Category>()
    fun setCategory(_categoryList:ArrayList<Category>){
        this.categoryList=_categoryList
        notifyDataSetChanged()
    }
    class CategoryViewHolder(val binding:CategoryItemsBinding ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
              Glide.with(holder.itemView)
                  .load(categoryList[position].strCategoryThumb)
                  .into(holder.binding.categoryImageView)
        holder.binding.categoryNameTextView.text=categoryList[position].strCategory

    }
}