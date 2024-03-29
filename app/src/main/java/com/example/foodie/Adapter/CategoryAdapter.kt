package com.example.foodie.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.Category
import com.example.foodie.databinding.CategoryViewBinding


class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoryList=ArrayList<Category>()
    lateinit var onItemClick:((Category)->Unit)
    fun setCategory(_categoryList:ArrayList<Category>){
        this.categoryList=_categoryList
        notifyDataSetChanged()
    }
    class CategoryViewHolder(val binding: CategoryViewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
              Glide.with(holder.itemView)
                  .load(categoryList[position].strCategoryThumb)
                  .into(holder.binding.categoryImageView)
        holder.binding.categoryNameTextView.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener{
              onItemClick.invoke(categoryList[position])
        }
    }
}